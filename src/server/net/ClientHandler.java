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
	private boolean printCoords;
	private int playerHP;

	public ClientHandler(Socket s) throws IOException {
		socket = s;
		Server.clientList.put(s.getInetAddress().getHostAddress(), this);
		Server.clientDisplayModel.addElement(s.getInetAddress().getHostAddress());
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		playerHP = 5;
		this.start();

	}

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
						System.out.println("ping missed");
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
				
				Iterator<String> it = inQueue.iterator();
				while(it.hasNext()) {
					String str = (String) it.next();
					if(str.startsWith("/coords ")) {
						String s = str.substring(8);
						it.remove();
						Server.playerCoords.put(this.socket.getInetAddress().getHostAddress(),s);
					}
				}
				
				} catch (Exception e) {

				}

				while (messageQueue.size() > 0) { // Send all messages
					String msg = messageQueue.poll();
					if (msg != null) {
						out.println(msg);
						if (msg.equals(":disc") || msg.equals(":kill")) {
							this.stopped = true;
						}
						if (msg.startsWith(":echo")) {
							printCoords = true;
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
				this.quit();
				return;
			}
		}
	}

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

	public void addMessage(String e) {
		messageQueue.add(e);
	}

	public String getPing() {
		return "" + this.ping;
	}

	public void halt() {
		this.stopped = true;

	}

	public void check() {
		return;

	}

	public void sendEvent(ExecutionEvent e) {
		this.addMessage(e.getMessage());

	}

	public void hit() {
		playerHP--;
		if (playerHP <= 0) {
			this.addMessage(":die");
		}
	}

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
		}
	}

}
