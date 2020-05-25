package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Clippable, but unseen. Stops players from walking off map.
 * 
 * @author Michael Ferolito
 * @version 2
 */
public class Null extends Barrier {

	/**
	 * See Block
	 */
	public static Null fromDoubleCoords(double x2, double y2, double z2) {
		return new Null(x2 / 2, y2 / 2, z2 / 2);

	}

	/**
	 * See Block
	 */
	public Null() {
		super();
	}

	/**
	 * See Block
	 */
	public Null(double a, double b, double c) {
		super(a, b, c);
	}

	/**
	 * Does nothing.
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {

	}

}
