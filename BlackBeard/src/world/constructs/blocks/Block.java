package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.constants.declaration.ViewConstants;
import world.viewport.JoglPane;
import world.viewport.MVector;
import world.viewport.Player;

/**
 * Common superclass for all Blocks. When created, forms a "GRASS BLOCK"
 * 
 * @author Michael Ferolito,William Meng
 *
 */
public class Block implements Clippable {

	/**
	 * Constructs a Block from coordinates that are twice the specified amount.
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
	 * For use with the classloader library.
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
	 * Suggests that the block add extra verticies to its drawing.
	 */
	public static boolean TESSELATION_ON = false;

	/**
	 * The position of the block
	 */
	double x, y, z;
	// private double red = Math.random()*4;
	// private double green = Math.random()*4;
	// private double blue = Math.random()*4;
	/**
	 * The size of the block. Subclasses may choose to change this value.
	 */
	protected final double size = 0.5;

	/**
	 * The position vector, used for transformations.
	 */
	protected MVector mv;

	/**
	 * True if and only if the block is selected by the Player. Subclasses may need
	 * to be allowed access.
	 */
	protected boolean isSelected;

	/**
	 * Default for classloader.
	 */
	public Block() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	/**
	 * Constructs a block with the specified coordinates.
	 */
	public Block(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	/**
	 * Returns true if the Block is out of the Player's rendering range. Removed for
	 * lag reasons.
	 */
	protected boolean outOfRange() {
		// if(MVector.add(mv.copy().mult(-1),
		// JoglPane.currentLoader.getC().getPosition()).getMagnitude()>ViewConstants.ABSOLUTE_RENDER_RANGE)
		// {
		// return true;
		// }
		return false;
	}

	@Override
	/**
	 * Draws the Block using the viewport parameters given.
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
			if (!isSelected)
				;
			glut.glutSolidCube(0.5f);
		}
		if (isSelected) {
			glut.glutSolidCube(0.6f);
			glut.glutWireCube(0.5f);
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
	 * Returns the coordinates in a MVector.
	 */
	public MVector getCoords() {
		return new MVector(x, y, z);
	}

	@Override
	/**
	 * See MVector.getCoords
	 */
	public double[] packCoords() {
		double[] a = { x, y, z };
		return a;
	}

	/**
	 * sets the block selection to the boolean.
	 */
	public void select(boolean b) {
		isSelected = b;
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
	 * Returns the x coordinate of this block.
	 */
	public double getX() {
		return x;
	}

	@Override
	/**
	 * Returns the y coordinate of this Block.
	 */
	public double getY() {
		return y;
	}

	@Override
	/**
	 * Returns the z coordinate of this Block.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Meant to be overridden
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean containsPoint1(double x, double y, double z) {
		return false;
	}

	@Override
	/**
	 * Translate x
	 */
	public void movex(double d) {
		x += d;

	}

	@Override
	/**
	 * Translate y
	 */
	public void movey(double d) {
		y += d;

	}

	@Override
	/**
	 * Translate z
	 */
	public void movez(double d) {
		z += d;

	}

	/**
	 * For building Worlds. Returns a compensation MVector for blocks.
	 * 
	 * @author William Meng
	 * @version 2.5
	 * @since 2.5
	 */
	public MVector getDirection(Player p) {
		MVector r = p.getPosition();
		double distance = Math.sqrt(Math.pow(this.x - r.x, 2) + Math.pow(this.y - r.y, 2) + Math.pow(this.z - r.z, 2));
		double xA = Math.acos((this.x - r.x) / distance);
		double yA = Math.asin((this.y - r.y) / distance);
		double zA = Math.acos((this.z - r.z) / distance);
		if (xA < Math.PI / 2) {
			return new MVector(-1, 0, 0);
		} else if (xA > -3 * Math.PI / 2) {
			return new MVector(1, 0, 0);
		} else if (yA < Math.PI / 2) {
			return new MVector(0, -1, 0);
		} else if (yA > -3 * Math.PI / 2) {
			return new MVector(0, 1, 0);
		} else if (zA < Math.PI / 2) {
			return new MVector(0, 0, -1);
		} else if (zA > -3 * Math.PI / 2) {
			return new MVector(0, 0, 1);
		} else {
			return null;
		}
	}

	/**
	 * Returns true if this Block is selected.
	 * 
	 * @author William Meng
	 */
	public boolean getSelection() {
		return isSelected;
	}

}
