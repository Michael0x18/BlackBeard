package world.viewport;

/**
 * Created because processing's PVector is bad and doesn't work with jre 1.6.
 * This is not bloated like processing's which has a 2D mode. For this one, if
 * you want a 2D mode, LEAVE THE Z VALUE BLANK!
 * 
 * @author Michael Ferolito
 * 
 *
 */
public class MVector {
	/**
	 * x position
	 */
	public double x = 0;
	/**
	 * y position
	 */
	public double y = 0;
	/**
	 * z position
	 */
	public double z = 0;

	/**
	 * new default stuff
	 */
	public MVector() {
		x = 0;
		y = 0;
		z = 0;
	}

	/**
	 * Creates a new MVector with the specified coordinates.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public MVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * adds the other MVector to this one. return this.
	 * 
	 * @param other
	 * @return
	 */
	public MVector add(MVector other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}

	/**
	 * get the magnitude of the Vector.
	 * 
	 * @return
	 */
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * 
	 * @param v
	 * @param d
	 * @return
	 */
	public static MVector mult(MVector v, double d) {
		return new MVector(v.x * d, v.y * d, v.z * d);
	}

	/**
	 * makes da magnitude 1
	 * @return
	 */
	public MVector normalize() {
		x = x / getMagnitude();
		y = y / getMagnitude();
		z = z / getMagnitude();
		return this;
	}

	/**
	 * multiplies and returns this by d.
	 * @param d
	 * @return
	 */
	public MVector mult(double d) {
		x *= d;
		y *= d;
		z *= d;
		return this;

	}

	/**
	 * copy factory
	 * @return
	 */
	public MVector copy() {
		return new MVector(x, y, z);
	}
	
	public double[] getPosition() {
		return new double[] {x,y,z};
	}
	
	/**
	 * adds the two and returns da result.
	 * @param u
	 * @param v
	 * @return
	 */
	public static MVector add(MVector u, MVector v) {
		MVector r = u.copy();
		r.add(v);
		return r;
	}

	/**
	 * Like PApplet.map, but with DOUBLES!
	 * @param value
	 * @param istart
	 * @param istop
	 * @param ostart
	 * @param ostop
	 * @return
	 */
	static public final double map(double value, double istart, double istop, double ostart, double ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

}
