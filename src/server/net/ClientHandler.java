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

				if (runTime % 15 == 0) {
					messageQueue.add(":exec<grep>");
					messageQueue.add(":exec<?state>");
				}

				if (runTime % 15 == 0) {

					String str = "";
					String thisIp = socket.getInetAddress().getHostAddress();
					// System.out.println(thisIp);
					synchronized (this) {
						for (String s : Server.playerCoords.keySet()) {
							if (!s.equals(thisIp)) {
								str += Server.playerCoords.get(s);
								str += "_";

							}
						}

					}
					if (str.length() > 0) {
						// System.out.println(str.substring(0, str.length() - 1));\
						if (str.substring(0, str.length() - 1).equals("null")) {
							System.err.println("NULL!!!!!!");
						}
						messageQueue.add(":exec<PlayerData>" + str.substring(0, str.length() - 1));
						
					}
//					if(Server.shotsList.size() > 0) {TODO-Server shots.
//						String msg = ":exec<!shots>";
//						for(SnowBall sb : Server.shotsList) {
//							msg += "{"+sb.toString()+"}`";
//						}
//						messageQueue.add(msg.substring(0, msg.length()-1));
//						//System.out.println(msg);
//					}
//					else {
//						messageQueue.add(":exec<!clearShots>");
//					}
				}

				String finalStr = "";
				for (String s : inQueue) {
					if (s.startsWith("/exec<?state>")) {
						// System.out.println(s);
//						ShotThingie stth = new ShotThingie();
//						if (Server.playerCoords.get(socket.getInetAddress().getHostAddress()) != null)
//							stth.CreateShot(Server.playerCoords.get(socket.getInetAddress().getHostAddress()));
//						s = null;
						//Found a bullet.
					} else if (s.startsWith(":exec")) {
						Server.sendExecutionEvent(new ExecutionEvent(s, null));
						s = null;
					}

					else if (s.startsWith("/exec<grep>")) {
						String str = s.substring(11);
						synchronized (this) {

							finalStr = str;
							// inQueue.remove(str);
							s = null;
						}

						// Server.println(s);

					}

				}

				if (!finalStr.equals("")) {
					if (printCoords)
						System.out.println(finalStr);
					Server.playerCoords.remove(this.socket.getInetAddress().getHostAddress());
					Server.playerCoords.put(this.socket.getInetAddress().getHostAddress(), finalStr.substring(0, finalStr.length()-1)+","+this.socket.getInetAddress().getHostAddress()+"}");
				}
				while (inQueue.contains(null))
					inQueue.remove(null);

				
				try {
				Iterator<String> it = inQueue.iterator();
				while (it.hasNext()) {
					String str = (String) it.next();
					if (str.startsWith("/grep") || str.startsWith("/exec<?state>")) {
						inQueue.remove(str);
					}
				}
				}catch(Exception e) {
					
				}

				while (messageQueue.size() > 0) { // Send all messages
					String msg = messageQueue.poll();
					out.println(msg);
					if (msg.equals(":disc") || msg.equals(":kill")) {
						this.stopped = true;
					}
					if (msg.startsWith(":echo")) {
						printCoords = true;
					}
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
		if(playerHP <= 0) {
			this.addMessage(":die");
		}
	}

}
