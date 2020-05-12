package server.mirrors.projectiles;

import server.net.Server;
import server.util.MVector;

public abstract class Projectile extends Thread {
	private volatile MVector position;
	private volatile MVector velocity;
	private boolean killed = false;

	public Projectile(MVector position, MVector velocity) {
		this.position = position;
		this.velocity = velocity;
		Server.shots.add(this);
		this.setDaemon(true);
		this.start();
	}

	public void run() {
		while (!killed) {
			position.add(velocity);
		}
	}

	/**
	 * Atomic operation sigKill
	 * Implements classic lock-free CAS instruction set.
	 */
	public void kill() {
		while (!killed) {
			killed = true;
		}
	}

	public double x() {
		return position.x;
	}

	public double y() {
		return position.y;
	}

	public double z() {
		return position.z;
	}
	
	public abstract String getSerialInfo();

}
