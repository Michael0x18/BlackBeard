package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import world.constructs.Ship;
import world.constructs.StructPlayer;
import world.constructs.projectiles.MeesenMeister;
import world.constructs.projectiles.Projectile;
import world.viewport.Grid;
import world.viewport.JoglPane;
import world.viewport.Player;

/**
 * 
 * @author mferolito676
 * @version 2
 * 
 *          Client-sided equivalent of ClientHandler on Server end. Handles most
 *          of the communications between the Server and Client using a Queue
 *          and two streams. Does most of the heavy lifting during TCP
 *          communications and any lag can be blamed on this class.
 * @since version 1
 * 
 */
public class ServerListener extends Thread {
	private Socket socket;
	private BufferedReader s;
	private PrintWriter p;
	@SuppressWarnings("deprecation")
	private Queue<ExecutionEvent> outE = new LinkedList<ExecutionEvent>();
//	private boolean printCoords = false;
	private Player matchedPlayer;

	/**
	 * Creates new ServerListener with an established socket connection.
	 * 
	 * @param socket - the TCP socket used
	 * @throws IOException - if socket is closed.
	 */
	public ServerListener(Socket socket) throws IOException {
		this.socket = socket;
		this.s = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.p = new PrintWriter(socket.getOutputStream(), true);
		this.start();
		JoglPane._driver_start(null);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Overrides Thread.run Main cyclic process
	 */
	@SuppressWarnings("deprecation")
	public void run() {
		while (true) {
			//p.println(":echo hi");
			try {
				if (this.s.ready()) {
					//System.out.println("CHECK!");
					//System.out.println(socket.isClosed());
					
					String msg = this.s.readLine();
					// System.out.println(msg);
					if (msg.equals(":ping")) {
						p.println("-ping");
						System.out.println("Ping response");
					} else if (msg.equals(":bce")) {
						throw (new BCException());
					} else if (msg.startsWith(":echo ")) {
						System.out.println(msg.substring(5));
						// PRINT TO SHELL
					} else if (msg.equals(":disc")) {
						System.out.println("CLOSING SOCKET!!!");
						this.socket.close();
					} else if (msg.equals(":kill")) {
						// System.exit(1);
						Runtime.getRuntime().halt(1);
					} else if (msg.equals(":die")) {
						// DIE, PUNY HUMAN
						JOptionPane.showMessageDialog(null, "You Died");
					}
					if (JoglPane.currentLoader != null) {
						matchedPlayer = JoglPane.currentLoader.getC();
						this.processEvents(msg);
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
	 * Same functionality as Server.
	 */ 
	public void processEvents(String msg) {
		if ((matchedPlayer != null) && msg.equals(":getcoords")) {
			// System.out.println("ok");
			p.println("/coords " + matchedPlayer.getPosition().x + " " + matchedPlayer.getPosition().y + " "
					+ matchedPlayer.getPosition().z+ " "+matchedPlayer.getAngle() + " "+matchedPlayer.getAngle2());
		}
		else if (msg.startsWith(":ship")) {
			msg = msg.substring(5);
			StringTokenizer st = new StringTokenizer(msg);
			String str = st.nextToken();
			
			Ship s = new Ship(str);
			s.moveTo(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()),
					Double.parseDouble(st.nextToken()));
			s.turn(Double.parseDouble(st.nextToken()));
			s.setVelocity(st.nextToken());
			s.lastrot = Double.parseDouble(st.nextToken());
			for (Ship sh : Grid.ships) {
				if (sh.name.equals(str)) {
					Grid.ships.remove(sh);
					break;
				}
			}
			Grid.ships.add(s);
			
			
			//Grid.ships.add(s);

		}
		else if(msg.startsWith(":coords")) {
			String[] playerData = msg.split("`");
			new Thread() {
				public void run() {
					CopyOnWriteArrayList<StructPlayer> sp = new CopyOnWriteArrayList<StructPlayer>();
					for(int i = 1; i < playerData.length; i++) {
						try {
						StringTokenizer st = new StringTokenizer(playerData[i]);
						//System.out.println(playerData[i]);
						String ip = st.nextToken();
						double px = Double.parseDouble(st.nextToken());
						double py = Double.parseDouble(st.nextToken());
						double pz = Double.parseDouble(st.nextToken());
						double pp = Double.parseDouble(st.nextToken());
						double pt = Double.parseDouble(st.nextToken());
						if(Math.abs(px-(int)(px)) < 0.001) {
							px = (int) px;
						}
						StructPlayer p = new StructPlayer(px,py,pz,pp,pt,ip);
						sp.add(p);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						
					}
					Grid.players = sp;
				}
			}.start();
		}else if(msg.startsWith(":shots")) {
			CopyOnWriteArrayList<Projectile> sh2 = new CopyOnWriteArrayList<Projectile>();
			String[] shots = msg.split(";");
			for(int i = 1; i < shots.length; i++) {
				sh2.add(MeesenMeister.yeet(shots[i]));
			}
			Grid.shots = sh2;
		}
//		if(msg.startsWith(":delta ")) {
//			String s = msg.substring(7);
//			StringTokenizer st = new StringTokenizer(s);
//			double dx = Double.parseDouble(st.nextToken());
//			double dy = Double.parseDouble(st.nextToken());
//			JoglPane.currentLoader.getC().getVelocity().x+=dx;
//			JoglPane.currentLoader.getC().getVelocity().z+=dy;
//		}

	}

	/**
	 * Rarely used
	 * @deprecated as of Version 2
	 * @param executionEvent event to send
	 */
	public void addEvent(ExecutionEvent executionEvent) {
		outE.add(executionEvent);

	}
	
	public void sendMessafe(String s) {
		p.println(s);
	}

}
