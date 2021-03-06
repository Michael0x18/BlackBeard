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
import java.util.StringTokenizer;

import server.mirrors.projectiles.MusketShot;
import server.util.MVector;

/**
 * The workhorse of the Server backend, the ClientHandler is the class that
 * almost exclusively talks to the client. It implements an atomic style of
 * threading. See the thicck paragraph on the Server doc for more information.
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since Before Version 1.
 *
 */
public class ClientHandler extends Thread {
	/**
	 * The socket
	 */
	private Socket socket;
	/**
	 * Connected to byteStream
	 */
	private PrintWriter out;
	/**
	 * Connected to byteStream
	 */
	private BufferedReader in;
	/**
	 * Messages to be sent
	 */
	private Queue<String> messageQueue = new LinkedList<String>();
	/**
	 * Everything received.
	 */
	private Queue<String> inQueue = new LinkedList<String>();
	/**
	 * last ping
	 */
	private int ping = 0;
	/**
	 * Used internally
	 */
	private int pingCounter = 0;
	/**
	 * Used internally
	 */
	private StopWatch pingTimer;
	/**
	 * Used internally
	 */
	private boolean stopped = false;
	/**
	 * Used internally
	 */
	private boolean pinging = false;
	/**
	 * Used internally
	 */
	private int missedPings = 0;
	/**
	 * Used internally
	 */
	private int runTime = 0;
	/**
	 * Used internally
	 */
	private double myX = 0;
	/**
	 * Used internally
	 */
	private double myY = 0;
	/**
	 * Used internally
	 */
	private double myZ = 0;
	// public static synchronized nextOtherPlayer
	// private boolean printCoords;
	/**
	 * Used internally
	 */
	private int playerHP;
	/**
	 * Used internally
	 */
	private double myTilt;
	/**
	 * Used internally
	 */
	private double myPan;
	/**
	 * Used internally
	 */
	public int points = 0;

	/**
	 * Called by ServerConnector. Sets up the ClientHandler, which sticks itself
	 * into the Server infastructure.
	 * 
	 * @param s
	 * @throws IOException
	 */
	public ClientHandler(Socket s) throws IOException {
		socket = s;
		s.setKeepAlive(true);
		Server.clientList.put(s.getInetAddress().getHostAddress(), this);
		Server.clientDisplayModel.addElement(s.getInetAddress().getHostAddress());
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		playerHP = 5;
//		new Thread(new Runnable() {
//			public void run() {
//				while(true) {
//					System.out.println(runTime);
//				}
//			}
//		}).start();
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
				/// Server.println(""+in.ready());
				// System.out.println("trying read");
				while (in.ready()) { // Read all messages from stream to Queue
					inQueue.add(in.readLine());
				}
				// System.out.println("Read finished");
				if (inQueue.size() == 0) {
					// System.out.println("There is something going on with the client.");
				}

//				if(runTime % 1000 == 0) {
//					System.out.println("Process running");
//				}

				pingCounter++; // Increment Ping counter
				if (pingCounter == 1000) {
					// System.out.println("Pinging");
					pingCounter = 0;
					if (pinging) {
						missedPings++;
						Server.println("ping missed: " + this.socket.getInetAddress().getHostAddress());
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
					// System.out.println("Got Ping");
					ping = pingTimer.halt();
					inQueue.remove("-ping");
					pinging = false;
				}

				// System.out.println("processing");
				this.processEvents();
				// System.out.println("done");

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
					if (runTime % 15 == 0) {
						messageQueue.add(":getcoords");
						messageQueue.add(":points " + points);
					}
					// System.out.println("sending coordinates");
					if (runTime % 15 == 0) {
						String otherCoords = "";
						for (String s : Server.playerCoords.keySet()) {
							if (!s.equals(this.socket.getInetAddress().getHostAddress())
									&& Server.playerCoords.get(s) != null
									&& !Server.playerCoords.get(s).contains("null")) {
								otherCoords += "`" + s + " " + Server.playerCoords.get(s);
							}
						}
						if (otherCoords.length() > 0) {
							// System.out.println(otherCoords);
							messageQueue.add(":coords" + otherCoords);
						}
					}

					// System.out.println("Crabbing coordinates.");
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
					if (Server.verbose)
						e.printStackTrace();
				}
				// System.out.println(messageQueue.size());
				synchronized (messageQueue) {
					// System.out.println("Inside sync");
					String msg;
					while ((msg = messageQueue.poll()) != null) { // Send all messages
						// String msg = messageQueue.poll();

						// System.out.println("sending message: "+msg);
						if (msg != null) {
							// System.out.println("before IO");
							out.println(msg);
							// System.out.println("After IO");
							if (msg.equals(":disc") || msg.equals(":kill")) {
								this.stopped = true;
							}
							if (msg.startsWith(":echo")) {
								// printCoords = true;
								System.out.println(msg.substring(5));
							}
						}
					}
					if (messageQueue.size() > 0) {
						System.out.println("std::err<<\"!\"");
						System.out.println(messageQueue.poll());
					}
					// System.out.println("done");
				}
				// System.out.println("FINISHED CYCLE");
				inQueue.clear();

				messageQueue.clear();

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
			System.out.println("Killing");
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
	public synchronized void addMessage(String e, String source) {
		// System.out.println("Adding message:" + e+" from "+source);
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
		this.addMessage(e.getMessage(), "");

	}

	/**
	 * CliencHandlers hold the Hp of the player. This causes the hp to decrease. It
	 * kills the player if the HP is less than or equal to 0;
	 */
	public void hit() {
		playerHP--;
		if (playerHP <= 0) {
			this.addMessage(":die", "internal hit");
		}
	}

	/**
	 * Should be overridden in subclasses. Provides infrastructure for handling
	 * events drawn from the queues that do not need to be immediately dealt with.
	 * @since Version 2.5
	 */
	public void processEvents() {
		// add code for handling front-end interactions here and here only.
		// use the Runtime millisecond counter eg: %15.
		if (runTime % 15 == 0) {
			messageQueue.add(":coords");
			// messageQueue.add("")
		}
		for (String msg : inQueue) {
			if (msg.startsWith("/coords")) {
				// System.out.println(msg);
				// System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg);
				st.nextToken();
				myX = Double.parseDouble(st.nextToken());
				myY = Double.parseDouble(st.nextToken());
				myZ = Double.parseDouble(st.nextToken());
				myPan = Double.parseDouble(st.nextToken());
				myTilt = Double.parseDouble(st.nextToken());
				Server.playerCoords.put(this.socket.getInetAddress().getHostAddress(), msg.substring(7));
			}
			if (msg.startsWith(":port")) {
				if (Server.shipQuick.get(msg.split(" ")[1]) == null)
					return;
				Server.shipQuick.get(msg.split(" ")[1]).aceleft(0.1);
			}
			if (msg.startsWith(":star")) {
				if (Server.shipQuick.get(msg.split(" ")[1]) == null)
					return;
				Server.shipQuick.get(msg.split(" ")[1]).aceright(0.1);
			}
			if (msg.startsWith(":acel")) {
				if (Server.shipQuick.get(msg.split(" ")[1]) == null)
					return;
				Server.shipQuick.get(msg.split(" ")[1]).accelerate(0.01);
			}
			if (msg.startsWith(":deac")) {
				// \u000d if (Server.shipQuick.get(msg.split(" ")[1]) == null)
					return; // An executable comment?!
				Server.shipQuick.get(msg.split(" ")[1]).accelerate(-0.01);
			}
			if (msg.startsWith(":shootEvent")) {
				System.out.println("Registered On Server");
				Server.shots.add(new MusketShot(new MVector(myX, myY, myZ), MVector.fromAngles(myPan, myTilt), this));
			}
		}

	}

}
