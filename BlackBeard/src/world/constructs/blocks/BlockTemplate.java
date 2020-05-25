package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.viewport.MVector;

/**
 * For Copy paste! _/¯( ͡° ͜ʖ ͡°)_/¯
 * 
 * @author Michael Ferolito
 *
 */
public class BlockTemplate extends Block {

	/**
	 * |_( ͡° ͜ʖ ͡°)_|
	 * 
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static BlockTemplate fromDoubleCoords(double x2, double y2, double z2) {
		return new BlockTemplate(x2 / 2, y2 / 2, z2 / 2);

	}

	/**
	 * |¯( ͡° ͜ʖ ͡°)¯|
	 */
	public void moveTo(double a, double b, double c) {
		x = a;
		y = b;
		z = c;
		mv = new MVector(x, y, z);
	}

	/**
	 * |¯( ͡° ͜ʖ ͡°)_|
	 */
	public BlockTemplate() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
	}

	/**
	 * |_( ͡° ͜ʖ ͡°)¯|
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public BlockTemplate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);

	}

	/**
	 * _/¯( ͡° ͜ʖ ͡°)_/¯
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if (outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// draw
		gl2.glTranslated(-x, -y, -z);
	}

}
