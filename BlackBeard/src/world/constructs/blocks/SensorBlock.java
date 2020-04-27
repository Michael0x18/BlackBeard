package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.viewport.Player;

/**
 * For sensing. Going to be deprecated as soon as William finished his, so
 * documentation is sparce.
 * 
 * @author Michael Ferolito
 *
 */
public class SensorBlock extends BGBlock {
	private Player p;
	private double x, y, z;

	/**
	 *super()
	 */
	public SensorBlock(Player p, double a, double b, double c) {
		this.p = p;
		x = a;
		y = b;
		z = c;
	}

	/**
	 * see super
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(p.getPosition().x, p.getPosition().y, p.getPosition().z);
		gl2.glTranslated(x, y, z);
		double a = Math.cos(p.getPan()) - 1;
		double b = Math.sin(p.getPan());
		gl2.glTranslated(a, 0, b);
		glut.glutWireCube(0.5f);
		gl2.glTranslated(-a, 0, -b);
		gl2.glTranslated(-x, -y, -z);
		gl2.glTranslated(-p.getPosition().x, -p.getPosition().y, -p.getPosition().z);

	}

}
