package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.viewport.MVector;

/**
 * 
 * @author Michael Ferolito Common superclass for all Blocks. When created,
 *         forms a "GRASS BLOCK"
 *
 */
public class Block implements Clippable {

	/**
	 * Creates a new block from the specified coordinates. The actual coordinates
	 * are half of what is given, hence the name. This is a factory constructor.
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
	 * Translates the Block. For use with the Runtime Classloader.
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
	 * If enabled, suggests that Block and all supporting subclasses attempt to
	 * smooth lighting by adding additional vertices to each area.
	 */
	public static boolean TESSELATION_ON = false;

	/**
	 * The Block's coordinates in three dimensional space.
	 */
	double x, y, z;
//	private double red = Math.random()*4;
//	private double green = Math.random()*4;
//	private double blue = Math.random()*4;
	/**
	 * The size of the Block.
	 */
	protected final double size = 0.5;

	/**
	 * The position vector.
	 */
	protected MVector mv;

	/**
	 * Default constructor. Sets all coordinates to zero and the MVector to {0,0,0}.
	 */
	public Block() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
	}

	/**
	 * Creates a Block at the specified coordinates.
	 * @param x
	 * @param y
	 * @param z
	 */
	public Block(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);

	}

	/**
	 * 
	 * @return true if the block is out of the player's render range.
	 */
	protected boolean outOfRange() {
//		if(MVector.add(mv.copy().mult(-1), JoglPane.currentLoader.getC().getPosition()).getMagnitude()>ViewConstants.ABSOLUTE_RENDER_RANGE) {
//			return true;
//		}
		return false;
	}

	@Override
	/**
	 * Draws the block using the viewport parameters. gl2 is redundant; it can be obtained by going gl.getGL2();
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if (outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// glut.glutSolidCube((float) 0.5);
		gl2.glColor3d(Color.LAWNGREEN.getRed(), Color.LAWNGREEN.getGreen(), Color.LAWNGREEN.getBlue());
		if (TESSELATION_ON) {
			gl2.glTranslated(0.125, 0.125, 0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(-0.125, -0.125, -0.125);

			gl2.glTranslated(-0.125, 0.125, 0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(0.125, -0.125, -0.125);

			gl2.glTranslated(0.125, -0.125, 0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(-0.125, 0.125, -0.125);

			gl2.glTranslated(0.125, 0.125, -0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(-0.125, -0.125, 0.125);

			gl2.glTranslated(-0.125, -0.125, -0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(0.125, 0.125, 0.125);

			gl2.glTranslated(-0.125, -0.125, 0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(0.125, 0.125, -0.125);

			gl2.glTranslated(0.125, -0.125, -0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(-0.125, 0.125, 0.125);

			gl2.glTranslated(-0.125, 0.125, -0.125);
			glut.glutSolidCube(0.25f);
			gl2.glTranslated(0.125, -0.125, 0.125);
		} else {
			glut.glutSolidCube(0.5f);
		}

		// gl2.glColor3d(0, .5, 0);
		// glut.glutWireCube(0.5001f);

		gl2.glTranslated(-x, -y, -z);

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

	@Override
	/**
	 * Returns the coordinates in a copy MVector
	 */
	public MVector getCoords() {
		return new MVector(x, y, z);
	}

	@Override
	/**
	 * Packs the coordinates into a 1*3 matrix for use in transforms.
	 */
	public double[] packCoords() {
		double[] a = { x, y, z };
		return a;
	}

	@Override
	/**
	 * Returns the size of the Block.
	 */
	public double getSize() {
		return 0.5;
	}

	@Override
	/**
	 * Returns the x coordinate of the Block
	 */
	public double getX() {
		return x;
	}

	@Override
	/**
	 * Returns the y coordinate of the Block.
	 */
	public double getY() {
		return y;
	}

	@Override
	/**
	 * Returns the z coordinate of the Block.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * returns false, This is a test method.
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean containsPoint1(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**
	 * Moves by the specified x value.
	 */
	public void movex(double d) {
		x += d;

	}

	@Override
	/**
	 * Moves by the specified y value.
	 */
	public void movey(double d) {
		y += d;

	}

	@Override
	/**
	 * Moves by the specified z value.
	 */
	public void movez(double d) {
		z += d;

	}

}
