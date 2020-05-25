package server.mirrors.projectiles;

import server.net.ClientHandler;
import server.util.MVector;

/**
 * Yet to be implemented
 * 
 * @author Michael Ferolito
 * @version 0.0
 * @since Version 2.5
 */
public class Cannonball extends Projectile {

	/**
	 * See projectile constructor.
	 * 
	 * @param position
	 * @param velocity
	 * @param source
	 */
	public Cannonball(MVector position, MVector velocity, ClientHandler source) {
		super(position, velocity, source);
	}

	@Override
	/**
	 * Returns a string containing minimal information necessary to replicate this
	 * object.
	 */
	public String getSerialInfo() {
		return "CannonBall`" + x() + "`" + y() + "`" + z();
	}

}
