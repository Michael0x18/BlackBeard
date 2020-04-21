package server.mirrors;

import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import server.net.Daemon;
import server.net.Server;

public class Ship extends Daemon{
	private boolean sunk = false;
	private double x = 0;
	private double y = 0;
	private double z = 0;
	private double bearing = 0;
	private double velocity = 0;
	private String name;
	private CopyOnWriteArrayList<String> players = new CopyOnWriteArrayList<>();

	public Ship(String name) {
		super();
		this.name = name;
		this.start();
	}
	
	public void run() {
		while(!sunk) {
			turnLeft(0.1);
			velocity = 0.01;
		try {
			Thread.sleep(10);
			players.clear();
			for(String s : Server.playerCoords.keySet()) {
				StringTokenizer st = new StringTokenizer(Server.playerCoords.get(s));
				double a = Double.parseDouble(st.nextToken());
				double b = Double.parseDouble(st.nextToken());
				double c = Double.parseDouble(st.nextToken());
				if((x-a)*(x-a)+(y-b)*(y-b)+(z-c)*(z-c)<144) {
					players.add(s);
				}
			}
			while(bearing > 360) {
				bearing -= 360;
			}
			while(bearing < -360) {
				bearing += 360;
			}
			double rad = Math.toRadians(bearing);
			x += velocity * Math.cos(rad);
			z += velocity * Math.sin(rad);
			//System.out.println(x);
			synchronized(this) {
				for(String player : Server.clientList.keySet()) {
					Server.clientList.get(player).addMessage(":ship "+name+" "+x+" "+y+" "+z+" "+bearing+" ");
					//Server.clientList.get(player).addMessage(":ship YEET 0.0 0.0 0.0 0.0");
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
	}
	
	public void sink() {
		sunk = true;
	}
	
	public void turnLeft(double degrees) {
		bearing -= degrees;
	}
	
	public void turnRight(double degrees) {
		turnLeft(-degrees);
	}

}
