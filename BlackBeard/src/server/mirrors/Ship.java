package server.mirrors;

import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import server.net.Server;
import server.net.daemons.Daemon;

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
	public double x = 0;
	public double y = 0;
	public double z = 0;
	private double bearing = 0;
	private double velocity = 0;
	public volatile double hp = 5;
	private String name;
	private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();
	double turned;
	// double lastrot;
	private volatile int counter = 0;
	private volatile double v = 0;

	public static final double µk = .9;

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
		Server.ships.add(this);
		Server.shipQuick.put(name, this);
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
		this.start();
		Server.ships.add(this);
		Server.shipQuick.put(name, this);
	}

	/**
	 * Overrides the run() method in Thread.
	 * 
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void run() {
		// velocity = 0.001;

		// x = 10;
		// z = 10;
		while (!sunk) {
			if (hp <= 0) {
				this.sink();
			}
			turnLeft(v);
			// v=0;
//			++counter;
//			if(counter == 100) {
//				velocity = 0;
//			}
			// turnLeft(0.1);
//			counter ++;
//			if(counter > 1000) {
//				turnLeft(180);
//				counter = 0;
//			}
//			velocity = 0.01;
			try {
				Thread.sleep(10);
				players.clear();
				for (String s : Server.playerCoords.keySet()) {
					String a;
					if ((a = Server.playerCoords.get(s)) != null) {
						StringTokenizer st = new StringTokenizer(a);
						double d = Double.parseDouble(st.nextToken());
						// double b = Double.parseDouble(st.nextToken());
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
				// synchronized (this) {
				for (String player : Server.clientList.keySet()) {
					Server.clientList.get(player).addMessage(":ship " + name + " " + x + " " + y + " " + z + " "
							+ bearing + " " + velocity + " " + turned + " " + hp, "ship.run");
					// System.out.println(bearing);
					// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
				}
				// }
				turned = 0;
//				for (String player : this.players) {
//					if (Server.clientList.get(player) != null) {
//						
//					}
////						Server.clientList.get(player)
////								.addMessage(":delta " + velocity * Math.cos(rad) + " " + velocity * Math.sin(rad));
//				}
//				System.out.println(Server.clientList.keySet().size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			// velocity *= µk;
		}
		Server.ships.remove(this);
		Server.shipQuick.remove(this.name);
		Server.println("Sunk");
		for (String player : Server.clientList.keySet()) {
//			Server.clientList.get(player).addMessage(":ship " + name + " " + x + " " + y + " " + z + " "
//					+ bearing + " " + velocity + " " + turned + " " + hp, "ship.run");
			Server.clientList.get(player).addMessage(":mayday " + name, "sinking");
			// System.out.println(bearing);
			// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String player : Server.clientList.keySet()) {
//			Server.clientList.get(player).addMessage(":ship " + name + " " + x + " " + y + " " + z + " "
//					+ bearing + " " + velocity + " " + turned + " " + hp, "ship.run");
			Server.clientList.get(player).addMessage(":mayday " + name, "sinking");
			// System.out.println(bearing);
			// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String player : Server.clientList.keySet()) {
//			Server.clientList.get(player).addMessage(":ship " + name + " " + x + " " + y + " " + z + " "
//					+ bearing + " " + velocity + " " + turned + " " + hp, "ship.run");
			Server.clientList.get(player).addMessage(":mayday " + name, "sinking");
			// System.out.println(bearing);
			// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
		}
	}

	/**
	 * Sets the sunk flag to true and causes the ship to stop execution on next loop
	 * intercept.
	 */
	public void sink() {
		System.out.println("sinking");
		new Thread() {
			public void run() {
				for (int i = 0; i < 1000; i++) {
					y -= 0.01;
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				y = -1000;
				sunk = true;
				
			}

		}.start();

	}

	/**
	 * Turns the ship left by the desired amount.
	 * 
	 * @param degrees
	 */
	public synchronized void turnLeft(double degrees) {
		bearing -= degrees;
		turned -= degrees;
	}

	/**
	 * Turns the ship right by the desired amount.
	 * 
	 * @param degrees
	 */
	public synchronized void turnRight(double degrees) {
		turnLeft(-degrees);
		turned += degrees;
	}

	public synchronized void accelerate(double a) {
		// velocity += a;
		velocity += a;
		if (velocity > 0.01) {
			velocity = 0.01;
		}
		if (velocity < -0.01) {
			velocity = -0.01;
		}
		// counter = 0;
	}

	public synchronized void aceleft(double a) {
		v += a;
		if (v > 0.2) {
			v = 0.2;
		}
	}

	public synchronized void aceright(double a) {
		v -= a;
		if (v < -0.2) {
			v = -0.2;
		}
	}
}
