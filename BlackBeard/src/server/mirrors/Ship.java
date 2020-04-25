package server.mirrors;

import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import server.net.Daemon;
import server.net.Server;

public class Ship extends Daemon {
	private boolean sunk = false;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double bearing = 0;
	private double velocity = 0;
	private String name;
	private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();
	private double turned;

	public Ship(String name) {
		super();
		this.name = name;
		this.start();
	}

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
					if ((a=Server.playerCoords.get(s)) != null) {
						StringTokenizer st = new StringTokenizer(a);
						double d = Double.parseDouble(st.nextToken());
						double b = Double.parseDouble(st.nextToken());
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
								.addMessage(":ship " + name + " " + x + " " + y + " " + z + " " + bearing + " ");
						// System.out.println(bearing);
						// Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
					}
				}
				for (String player : this.players) {
					if (Server.clientList.get(player) != null)
						Server.clientList.get(player)
								.addMessage(":delta " + velocity * Math.cos(rad) + " " + velocity * Math.sin(rad));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sink() {
		sunk = true;
	}

	public void turnLeft(double degrees) {
		bearing -= degrees;
		turned -= degrees;
	}

	public void turnRight(double degrees) {
		turnLeft(-degrees);
		turned += degrees;
	}

}
