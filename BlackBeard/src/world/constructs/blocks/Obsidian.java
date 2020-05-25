package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Pure black block. see Block.
 * 
 * @author Michael Ferolito
 *
 */
public class Obsidian extends Stone {

	/**
	 * See Block
	 */
	public static Obsidian fromDoubleCoords(double x2, double y2, double z2) {
		return new Obsidian(x2 / 2, y2 / 2, z2 / 2);

	}

	/**
	 * See Block
	 */
	public Obsidian() {
		super();
	}

	/**
	 * See Block
	 */
	public Obsidian(double x, double y, double z) {
		super(x, y, z);
	}

	/**
	 * See Block. Now replace green with black.
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if (outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// glut.glutSolidCube((float) 0.5);
		gl2.glColor3d(0, 0, 0);

		glut.glutSolidCube(0.5f);

//		gl2.glColor3d(0, .5, 0);
//		glut.glutWireCube(0.5001f);

		gl2.glTranslated(-x, -y, -z);

		if (super.isSelected) {
			glut.glutWireCube(0.5f);
		}
	}

}
