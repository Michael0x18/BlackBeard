package server.mirrors.projectiles;

import server.net.ClientHandler;
import server.util.MVector;

/**
 * Represents a MusketBall fired by the Player.
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since 2.3
 */
public class MusketShot extends Projectile {

	/**
	 * see super constructor.
	 * 
	 * @param position
	 * @param velocity
	 * @param source
	 */
	public MusketShot(MVector position, MVector velocity, ClientHandler source) {
		super(position, velocity, source);
	}

	@Override
	/**
	 * Returns a String containing the minimal information necessary to replicate
	 * this object.
	 */
	public String getSerialInfo() {
		return "MusketShot`" + x() + "`" + y() + "`" + z();
	}

}
