package server.mirrors.projectiles;

import server.util.MVector;

public class Cannonball extends Projectile{

	public Cannonball(MVector position, MVector velocity) {
		super(position, velocity);
	}

	@Override
	/**
	 * Returns a string containing minimal information necessary to replicate this object.
	 */
	public String getSerialInfo() {
		return "CannonBall`"+x()+" "+y()+" "+z();
	}

}
