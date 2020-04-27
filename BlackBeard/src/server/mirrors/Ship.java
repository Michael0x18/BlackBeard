package server.mirrors;

import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import server.net.Daemon;
import server.net.Server;

/**
 * 
 * @author michaelonblue
 * @since version 2
 * @version 2 The ship is the Server equivalent of a UNIX daemon. It is created
 *          as a daemon to prevent the JVM from continuing execution once the
 *          event, graphics, main, and network Threads have shut down.
 * 
 *
 */
public class Ship extends Daemon {
	private boolean sunk = false;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double bearing = 0;
	private double velocity = 0;
	private String name;
	private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();
	double turned;

	/**
	 * Standard Ship constructor. Names are the valid identifier that facilitates
	 * the TCP transfer of details. Valid names MUST BE ONE TOKEN.
	 * 
	 * @param name
	 */
	public Ship(String name) {
		super();
		this.name = name;
		this.start();
	}

	/**
	 * Standard constructor for a ship. See the default constructor for the name
	 * explanation. dx is the x coordinate, dy is the y coordinate, dz is the z
	 * coordinate. The velocity is the initial velocity of the ship. The bearing is
	 * the initial angle of the ship. The bearing is measured in degree starting
	 * from 'Grid North', or the positive x direction with respect to the origin. In
	 * vector notation, it is described as using {1,0,0} as the directional
	 * component. Ships immediately begin execution upon construction.
	 * 
	 * @param name
	 * @param dx
	 * @param dy
	 * @param dz
	 * @param bearing
	 * @param velocity
	 */
	public Ship(String name, double dx, double dy, double dz, double bearing, double velocity) {
		this.name = name;
		this.x = dx;
		this.y = dy;
		this.z = dz;
		this.bearing = bearing;
		this.velocity = velocity;
	}

	/**
	 * Overrides the run() method in Thread.
	 * 
	 */
	public void run() {
		// velocity = 0.001;

		// x = 10;
		// z = 10;
		while (!sunk) {
			turnLeft(0.1);
			velocity = 0.01;
			try {
				Thread.sleep(10);
				players.clear();
				for (String s : Server.playerCoords.keySet()) {
					String a;
					if ((a = Server.playerCoords.get(s)) != null) {
						StringTokenizer st = new StringTokenizer(a);
						double d = Double.parseDouble(st.nextToken());
						//double b = Double.parseDouble(st.nextToken());
						double c = Double.parseDouble(st.nextToken());
						if ((x - d) * (x - d) + (z - c) * (z - c) < 144) {
							players.add(s);
						}
					}
				}
				while (bearing > 360) {
					bearing -= 360;
				}
				while (bearing < -360) {
					bearing += 360;
				}
				double rad = Math.toRadians(bearing);
				x += velocity * Math.cos(rad);
				z += velocity * Math.sin(rad);
				// System.out.println(x);
				synchronized (this) {
					for (String player : Server.clientList.keySet()) {
						Server.clientList.get(player)
								.addMessage(":ship " + name + " " + x + " " + y + " " + z + " " + bearing + " "+velocity);
						// System.out.println(bearing);
						// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
					}
				}
				for (String player : this.players) {
					if (Server.clientList.get(player) != null) {
						
					}
//						Server.clientList.get(player)
//								.addMessage(":delta " + velocity * Math.cos(rad) + " " + velocity * Math.sin(rad));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the sunk flag to true and causes the ship to stop execution on next loop
	 * intercept.
	 */
	public void sink() {
		sunk = true;
	}

	/**
	 * Turns the ship left by the desired amount.
	 * 
	 * @param degrees
	 */
	public void turnLeft(double degrees) {
		bearing -= degrees;
		turned -= degrees;
	}

	/**
	 * Turns the ship right by the desired amount.
	 * 
	 * @param degrees
	 */
	public void turnRight(double degrees) {
		turnLeft(-degrees);
		turned += degrees;
	}

}
