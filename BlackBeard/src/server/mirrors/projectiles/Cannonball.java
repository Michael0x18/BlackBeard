package server.mirrors.projectiles;

import server.net.ClientHandler;
import server.util.MVector;

public class Cannonball extends Projectile{

	public Cannonball(MVector position, MVector velocity,ClientHandler source) {
		super(position, velocity,source);
	}

	@Override
	/**
	 * Returns a string containing minimal information necessary to replicate this object.
	 */
	public String getSerialInfo() {
		return "CannonBall`"+x()+"`"+y()+"`"+z();
	}

}
