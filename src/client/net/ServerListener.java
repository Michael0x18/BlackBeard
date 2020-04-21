package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



/**
 * 
 * @author mferolito676
 * @version 1
 * 
 * Client-sided equivalent of ClientHandler on Server end.
 * Handles most of the communications between the Server and Client using a Queue and two streams.
 * Does most of the heavy lifting during TCP communications and any lag can be blamed on this class.
 */
public class ServerListener extends Thread {
	private Socket socket;
	private BufferedReader s;
	private PrintWriter p;
	private Queue<ExecutionEvent> outE = new LinkedList<ExecutionEvent>();
	private boolean printCoords = false;

	/**
	 * Creates new ServerListener with an established socket connection.
	 * @param socket - the TCP socket used
	 * @throws IOException - if socket is closed.
	 */
	public ServerListener(Socket socket) throws IOException {
		this.socket = socket;
		this.s = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.p = new PrintWriter(socket.getOutputStream(), true);
		this.start();
	}

	/**
	 * Overrides Thread.run
	 * Main cyclic process
	 */
	public void run() {
		while (true) {
			try {
				if (this.s.ready()) {
					String msg = this.s.readLine();
					// System.out.println(msg);
					if (msg.equals(":ping")) {
						p.println("-ping");
						// System.out.println("Ping response");
					}else if(msg.equals(":bce")) {
						throw(new BigChodeException());
					} else if (msg.startsWith(":exec<WorldSetup>")) {
						//WORLD SETUP CODE
					} else if (msg.equals(":exec<grep>")) {
						//PLS GIVE PLAYER COORDS
					}else if(msg.startsWith(":exec<!clearShots>")) {
						//RM ALL BULLETS
					}else if(msg.startsWith(":exec<?state>")) {
						//TO SHOOT OR NOT TO SHOOT
					}else if(msg.startsWith(":exec<!shots>")) {
						//PLS LOAD THESE BULLETS
					} else if (msg.startsWith(":echo ")) {
						System.out.println(msg.substring(5));
						// PRINT TO SHELL
					} else if (msg.equals(":disc")) {
						this.socket.close();
					} else if (msg.equals(":kill")) {
						//System.exit(1);
						Runtime.getRuntime().halt(1);
					} else if (msg.startsWith(":exec")) {
						synchronized (this) {
							// System.out.println(msg);
							if (msg.indexOf("null") == -1)
								Client.sendExecutionEvent(msg);
						}
					}else if (msg.equals(":die")) {
						//DIE, PUNY HUMAN
						JOptionPane.showMessageDialog(null, "You Died");
					}


					while (outE.size() > 0) {
						p.println(":exec " + outE.poll().getMessage());
					}

				}
			} catch (Exception e) {
				// System.out.println("e");
				if (Client.VerboseMode)
					e.printStackTrace();
			}
		}
	}

	/**
	 * Rarely used
	 * @param executionEvent event to send
	 */
	public void addEvent(ExecutionEvent executionEvent) {
		outE.add(executionEvent);

	}

}
