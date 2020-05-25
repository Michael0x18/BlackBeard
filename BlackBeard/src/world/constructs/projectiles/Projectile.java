package world.constructs.projectiles;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.constructs.blocks.Clippable;
import world.viewport.MVector;
import world.viewport.Player;

/**
 * Like a Block, but round.
 * 
 * @author Michael Ferolito
 *
 */
public abstract class Projectile implements Clippable {
	protected MVector position;

	/**
	 * Legit just a Block.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public Projectile(double a, double b, double c) {
		position = new MVector(a, b, c);
	}

	/**
	 * It even has a select method.
	 */
	public void select(boolean b) {
		return;
	}

	@Override
	/**
	 * Draws a ball.
	 */
	public abstract void draw(GLUT glut, GLU glu, GL gl, GL2 gl2);

	@Override
	/**
	 * Point containing. False!
	 */
	public boolean containsPoint(double x, double y, double z) {
		return false;
	}

	@Override
	/**
	 * returns link to Positin Vector.
	 */
	public MVector getCoords() {
		return position;
	}

	@Override
	/**
	 * Packs the coordinates into an array vector.
	 */
	public double[] packCoords() {
		double[] a = { position.x, position.y, position.z };
		return a;
	}

	@Override
	/**
	 * returns 0
	 * 
	 */
	public double getSize() {
		return 0;
	}

	@Override
	/**
	 * returns x
	 */
	public double getX() {
		return position.x;
	}

	@Override
	/**
	 * returns y
	 */
	public double getY() {
		return position.y;
	}

	@Override
	/**
	 * returns z
	 */
	public double getZ() {
		return position.z;
	}

	@Override
	/**
	 * translate
	 */
	public void movex(double d) {
		position.x += d;
	}

	@Override
	/**
	 * translate
	 */
	public void movey(double d) {
		position.y += d;

	}

	@Override
	/**
	 * translate
	 */
	public void movez(double d) {
		position.z += d;

	}

	@Override
	/**
	 * Returns false.
	 */
	public boolean getSelection() {
		return false;
	}

	@Override
	/**
	 * Returns null
	 */
	public MVector getDirection(Player p) {
		return null;
	}

}
