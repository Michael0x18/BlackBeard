package world.constructs;

/**
 * Holds the minimal amount of information needed to represent a Player
 * 
 * @author Michael Ferolito
 *
 */
public class StructPlayer extends Structure {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -1966683972301498894L;
	/**
	 * x coordinate
	 */
	public double x;
	/**
	 * y coordinate
	 */
	public double y;
	/**
	 * z coordinate
	 */
	public double z;
	/**
	 * pan
	 */
	public double pan;
	/**
	 * tilt
	 */
	public double tilt;
	/**
	 * Player's IP address.
	 */
	public String ip;

	/**
	 * Allocates RAM much like a C structure.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param s
	 */
	public StructPlayer(double a, double b, double c, double d, double e, String s) {
		x = a;
		y = b;
		z = c;
		pan = d;
		tilt = e;
		ip = s;
	}

}
