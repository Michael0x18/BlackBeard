package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
//import java.util.Scanner;

/**
 * The workhorse of the Server backend, the ClientHandler is the class that
 * almost exclusively talks to the client. It implements an atomic style of
 * threading. See the thicck paragraph on the Server doc for more informaion.
 * 
 * @author Michael Ferolito
 *
 */
public class ClientHandler extends Thread {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private Queue<String> messageQueue = new LinkedList<String>();
	private Queue<String> inQueue = new LinkedList<String>();
	private int ping = 0;
	private int pingCounter = 0;
	private StopWatch pingTimer;
	private boolean stopped = false;
	private boolean pinging = false;
	private int missedPings = 0;
	private int runTime = 0;
	// public static synchronized nextOtherPlayer
	// private boolean printCoords;
	private int playerHP;

	/**
	 * Called by ServerConnector. Sets up the ClientHandler, which sticks itself
	 * into the Server infastructure.
	 * 
	 * @param s
	 * @throws IOException
	 */
	public ClientHandler(Socket s) throws IOException {
		socket = s;
		Server.clientList.put(s.getInetAddress().getHostAddress(), this);
		Server.clientDisplayModel.addElement(s.getInetAddress().getHostAddress());
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		playerHP = 5;
		this.start();

	}

	/**
	 * Main cyclic process.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			runTime++;
			if (runTime > 100000) {
				runTime = 0;
			}

			try {

				while (in.ready()) { // Read all messages from stream to Queue
					inQueue.add(in.readLine());
				}

				pingCounter++; // Increment Ping counter
				if (pingCounter == 1000) {
					// System.out.println("Pinging");
					pingCounter = 0;
					if (pinging) {
						missedPings++;
						Server.println("ping missed: "+this.socket.getInetAddress().getHostAddress());
					}
					if (missedPings >= 3) {
						Server.println("Client disconnected:");
						this.quit();
						return;
					}
					messageQueue.add(":ping");
					pinging = true;
					pingTimer = new StopWatch();
					pingTimer.start();
				}

				if (inQueue.contains("-ping")) { // Check Client Response for ping
					ping = pingTimer.halt();
					inQueue.remove("-ping");
					pinging = false;
				}

				this.processEvents();

				while (inQueue.contains(null))
					inQueue.remove(null);

				try {
//				Iterator<String> it = inQueue.iterator();
//				while (it.hasNext()) {
//					String str = (String) it.next();
//					if (str.startsWith("/grep") || str.startsWith("/exec<?state>")) {
//						inQueue.remove(str);
//					}
//				}
					if(runTime % 15 == 0) {
						messageQueue.add(":getcoords");
					}
					String otherCoords = "";
					for(String s : Server.playerCoords.keySet()) {
						if(!s.equals(this.socket.getInetAddress().getHostAddress()) && Server.playerCoords.get(s) != null) {
							otherCoords += "|" + s+" "+Server.playerCoords.get(s);
						}
					}
					messageQueue.add(":coords"+otherCoords);

					Iterator<String> it = inQueue.iterator();
					while (it.hasNext()) {
						String str = (String) it.next();
						if (str.startsWith("/coords ")) {
							String s = str.substring(8);
							it.remove();
							Server.playerCoords.put(this.socket.getInetAddress().getHostAddress(), s);
						}
					}

				} catch (Exception e) {
					if(Server.verbose)
						e.printStackTrace();
				}

				while (messageQueue.size() > 0) { // Send all messages
					String msg = messageQueue.poll();
					if (msg != null) {
						out.println(msg);
						if (msg.equals(":disc") || msg.equals(":kill")) {
							this.stopped = true;
						}
						if (msg.startsWith(":echo")) {
							// printCoords = true;
							System.out.println(msg.substring(5));
						}
					}
				}
				if (inQueue.size() > 0) {
					inQueue.clear();
				}
			} catch (Exception e) {
				if (Server.verbose)
					e.printStackTrace();
			}
			if (this.stopped) {
				Server.println("Exiting on signal 15");
				this.quit();
				return;
			}
		}
	}

	/**
	 * Kills the running process and terminates the socket.
	 */
	private void quit() {
		Server.clientList.remove(this.socket.getInetAddress().getHostAddress());
		Server.clientDisplayModel.removeElement(this.socket.getInetAddress().getHostAddress());
		String msg = this.socket.getInetAddress().getHostAddress();
		Server.playerCoords.remove(this.socket.getInetAddress().getHostAddress());
		try {
			this.socket.close();
			Server.println("Killed: " + msg);
		} catch (IOException e) {
			if (Server.verbose)
				e.printStackTrace();
		}
	}

	/**
	 * Adds a message to output Queue, which is dumped into the output stream on
	 * next cycle.
	 * 
	 * @param e
	 */
	public void addMessage(String e) {
		messageQueue.add(e);
	}

	/**
	 * returns the last measured ping value.
	 * 
	 * @return
	 */
	public String getPing() {
		return "" + this.ping;
	}

	/**
	 * stops the ClientHandler.
	 */
	public void halt() {
		this.stopped = true;

	}

	/**
	 * Ensures the ClientHandler is not null. A similar process may be achieved by
	 * using Unsafe to check for initialized instances.
	 */
	public void check() {
		return;

	}

	/**
	 * @deprecated since version 2
	 * @param e
	 */
	public void sendEvent(ExecutionEvent e) {
		this.addMessage(e.getMessage());

	}

	/**
	 * CliencHandlers hold the Hp of the player. This causes the hp to decrease. It
	 * kills the player if the HP is less than or equal to 0;
	 */
	public void hit() {
		playerHP--;
		if (playerHP <= 0) {
			this.addMessage(":die");
		}
	}

	/**
	 * Should be overridden in subclasses. Provides infastructure for handling
	 * events drawn from the queues that do not need to be immediately dealt with.
	 */
	public void processEvents() {
		// add code for handling front-end interactions here and here only.
		// use the Runtime millisecond counter eg: %15.
		if (runTime % 15 == 0) {
			messageQueue.add(":coords");
		}
		for (String msg : inQueue) {
			if (msg.startsWith("/coords")) {
				// System.out.println(msg);
				Server.playerCoords.put(this.socket.getInetAddress().getHostAddress(), msg.substring(7));
				
			}
			if(msg.startsWith(":port")) {
				Server.shipQuick.get(msg.split(" ")[1]).aceleft(0.1);
			}
			if(msg.startsWith(":star")) {
				Server.shipQuick.get(msg.split(" ")[1]).aceright(0.1);
			}
			if(msg.startsWith(":acel")) {
				Server.shipQuick.get(msg.split(" ")[1]).accelerate(0.01);
			}
			if(msg.startsWith(":deac")) {
				Server.shipQuick.get(msg.split(" ")[1]).accelerate(-0.01);
			}
		}
		
	}

}
