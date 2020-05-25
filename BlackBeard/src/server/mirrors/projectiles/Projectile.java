package server.mirrors.projectiles;

import server.mirrors.Ship;
import server.net.ClientHandler;
import server.net.Server;
import server.util.MVector;

/**
 * Generic non-Daemon thread for handling one bullet.
 * 
 * @author Michael Ferolito
 *
 */
public abstract class Projectile extends Thread {
	private volatile MVector position;
	private volatile MVector velocity;
	private boolean killed = false;
	private int counter = 0;
	private ClientHandler source;
	/**
	 * Set to true upon caught instance of ThreadDeathException.
	 */
	public boolean dead = false;

	/**
	 * Constructs a Projectile based on two MVectors. The source is provided for
	 * tagging purposes.
	 * 
	 * @param position
	 * @param velocity
	 * @param source
	 */
	public Projectile(MVector position, MVector velocity, ClientHandler source) {
		this.position = position;
		this.velocity = velocity;
		this.velocity.mult(0.5);
		Server.shots.add(this);
		this.setDaemon(true);
		this.source = source;
		this.start();
	}

	/**
	 * Don't call directly, doing so results in IllegalThreadStateException, as
	 * Thread is already started and cycling. Start is called during construction.
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
				if (counter > 100)
					kill();
				for (Ship s : Server.ships) {
					if (isHit(s.x, s.y, s.z)) {
						s.hp--;
						System.out.println("Hit Ship");
						source.points++;
						kill();
						return;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Segmentation fault in Bullet thread: exiting on signal 15.");
		} finally {
			System.out.println("Waiting (max 60 seconds) for Thread buffSpaceDaemon to stop.");
		}

	}

	/**
	 * Checks the hitbox of the ship for it.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public boolean isHit(double a, double b, double c) {
		double x = position.x;
		double y = position.y;
		double z = position.z;
		return (x - a) * (x - a) + (y - b) * (y - b) + (z - c) * (z - c) < 5;
	}

	/**
	 * No use.
	 * 
	 * @return
	 */
	protected boolean equals() {
		return false;
	}

	/**
	 * Atomic operation sigKill Implements classic lock-free CAS instruction set.
	 */
	public void kill() {
		while (!killed) {
			killed = true;
		}
		this.dead = true;
		Server.shots.remove(this); //
	}

	/**
	 * returns the x coordinate of the position vector.
	 * 
	 * @return
	 */
	public double x() {
		return position.x;
	}

	/**
	 * Returns the y coordinate fo the position vector
	 * 
	 * @return
	 */
	public double y() {
		return position.y;
	}

	/**
	 * Returns the z coordinate of the position vector.
	 * 
	 * @return
	 */
	public double z() {
		return position.z;
	}

	/**
	 * Intended to return a string containing the minimal information necessary to
	 * replicate this object. It is also required to have an identifier.
	 * 
	 * @return
	 */
	public abstract String getSerialInfo();

}
