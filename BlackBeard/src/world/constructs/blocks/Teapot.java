package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

/**
 * Seeing as GLUT decided to have their logo (a teapot) as a built in method, I
 * had to put this in. It actually looks pretty good.
 * 
 * @author Michael Ferolito
 * @version 2.5
 */
public class Teapot extends Block {

	/**
	 * I'm a little teapot, short and stout, here is my handle, here is my spout...
	 * 
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static Teapot fromDoubleCoords(double x2, double y2, double z2) {
		return new Teapot(x2 / 2, y2 / 2, z2 / 2);

	}

	/**
	 * Just for laughs.
	 * 
	 * @param i
	 * @param d
	 * @param j
	 */
	public Teapot(double i, double d, double j) {
		super(i, d, j);
	}

	/**
	 * ~
	 */
	public Teapot() {
		super();
	}

	@Override
	/**
	 * draws teh GLUT teapot!
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if (outOfRange())
			return;
		gl2.glColor3d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue());
		gl2.glTranslated(x, y, z);
		glut.glutSolidTeapot(0.25);
		// glut.glutWireCube(0.5f);
		gl2.glTranslated(-x, -y, -z);
		if (super.isSelected) {
			glut.glutWireCube(0.5f);
		}
	}

}
