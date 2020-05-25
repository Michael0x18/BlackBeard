package server.mirrors;

/**
 * Reverse substitute mirror for Players on Server. Player-Player hits are not
 * allowed.
 * 
 * @author Michael Ferolito
 *
 */
public class Player {

	/**
	 * Determines if the Player contains the specified point.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean containsPoint(double x, double y, double z) {
		return false;
	}

}
