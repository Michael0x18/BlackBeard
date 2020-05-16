package server.mirrors.projectiles;

import server.net.Server;
import server.util.MVector;

public abstract class Projectile extends Thread {
	private volatile MVector position;
	private volatile MVector velocity;
	private boolean killed = false;
	private int counter = 0;

	public Projectile(MVector position, MVector velocity) {
		this.position = position;
		this.velocity = velocity;
		this.velocity.mult(0.5);
		Server.shots.add(this);
		this.setDaemon(true);
		this.start();
	}

	/**
	 * Don't call directly, doing so results in IllegalThreadStateException, as Thread is already started and cycling. 
	 */
	public void run() {
		try {
		while (!killed) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			++counter;
			position.add(velocity);
			if(counter>100)
				kill();
		}
		}catch(Exception e) {
			System.err.println("Segmentation fault in Bullet thread: exiting on signal 15.");
		}finally {
			System.out.println("Waiting (max 60 seconds) for Thread buffSpaceDaemon to stop.");
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

	/**
	 * returns the x coordinate of the position vector.
	 * @return
	 */
	public double x() {
		return position.x;
	}

	/**
	 * Returns the y coordinate fo the position vector
	 * @return
	 */
	public double y() {
		return position.y;
	}

	/**
	 * Returns the z coordinate of the position vector.
	 * @return
	 */
	public double z() {
		return position.z;
	}
	
	/**
	 * Intended to return a string containing the minimal information necessary to replicate this object.
	 * It is also required to have an identifier.
	 * @return
	 */
	public abstract String getSerialInfo();

}
