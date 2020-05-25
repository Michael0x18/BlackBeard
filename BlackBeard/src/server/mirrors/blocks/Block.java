package server.mirrors.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import server.util.MVector;

/**
 * Common superclass for all Blocks. When created, forms a "GRASS BLOCK"
 * 
 * @author Michael Ferolito, William Meng
 *
 */
public class Block {

	/**
	 * Factory constructor. Takes in convenient coordinates that are two times the
	 * actual size.
	 * 
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static Block fromDoubleCoords(double x2, double y2, double z2) {
		return new Block(x2 / 2, y2 / 2, z2 / 2);

	}

	/**
	 * Moves the Block to the coordinates. For use with a dynamic classloader.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public void moveTo(double a, double b, double c) {
		x = a;
		y = b;
		z = c;
		mv = new MVector(x, y, z);
	}

	/**
	 * If true, suggests that this Block add extra verticies to smooth the GL
	 * lighting.
	 */
	public static boolean TESSELATION_ON = false;

	/**
	 * The coordinates of the Block.
	 */
	double x, y, z;
	// private double red = Math.random()*4;
	// private double green = Math.random()*4;
	// private double blue = Math.random()*4;
	/**
	 * The size of the Block. This may be changed by subclasses.
	 */
	protected final double size = 0.5;

	/**
	 * The position vector of the Block. For use in transformations.
	 */
	protected MVector mv;

	/**
	 * Blocks may change this as internally modified. Eg. A lighthouse block will
	 * deselect every time the face changes.
	 */
	protected boolean isSelected;

	/**
	 * Default constructor. Initializes position to zero and selected to false.
	 */
	public Block() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	/**
	 * Creates a block with the specified coordinates.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Block(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	/**
	 * Checks to see if the Block is out of the Player's render range. It is removed
	 * because if caused problems with the Player's camera.
	 * 
	 * @return
	 */
	protected boolean outOfRange() {
		// if(MVector.add(mv.copy().mult(-1),
		// JoglPane.currentLoader.getC().getPosition()).getMagnitude()>ViewConstants.ABSOLUTE_RENDER_RANGE)
		// {
		// return true;
		// }
		return false;
	}

	/**
	 * Check bounds Greater or less than no equal to.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public boolean containsPoint(double x, double y, double z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		double left = this.x - size / 2;
		double right = this.x + size / 2;
		double top = this.y - size / 2;
		double bottom = this.y + size / 2;
		double front = this.z - size / 2;
		double back = this.z + size / 2;
		if (x >= left && x <= right && y >= top && y <= bottom && z >= front && z <= back) {
			return true;
		}

		return false;
	}

	/**
	 * Returns a MVector with the coordinates. The returned MVector is not linked to
	 * the Coordinate Vector inside this instance.
	 * 
	 * @return
	 */
	public MVector getCoords() {
		return new MVector(x, y, z);
	}

	/**
	 * Returns the Coordinates of thei Block as an Array Vector for use in Frustum
	 * Transformations.
	 * 
	 * @return
	 */
	public double[] packCoords() {
		double[] a = { x, y, z };
		return a;
	}

	/**
	 * Sets the selection of this Block to the boolean.
	 * 
	 * @author William Meng
	 * @param b
	 */
	public void select(boolean b) {
		isSelected = b;
	}

	/**
	 * Returns the size of this Block.
	 * 
	 * @return
	 */
	public double getSize() {
		return 0.5;
	}

	/**
	 * Returns the x coordinate of this Block.
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y coordinate of this Block.
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z coordinate of this Block.
	 * 
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 * A method that is meant to be overridden. (but not mandatory.)
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean containsPoint1(double x, double y, double z) {
		return false;
	}

	/**
	 * Translates the x coordinate by the given value.
	 * 
	 * @param d
	 */
	public void movex(double d) {
		x += d;

	}

	/**
	 * Translates the y coordinate by the given value.
	 * 
	 * @param d
	 */
	public void movey(double d) {
		y += d;

	}

	/**
	 * Translates the z coordinate by the given value.
	 * 
	 * @param d
	 */
	public void movez(double d) {
		z += d;

	}

	/**
	 * Blocks don't get drawn on the Server Side.
	 * 
	 * @param glut
	 * @param glu
	 * @param gl
	 * @param gl2
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		return;
	}

}
