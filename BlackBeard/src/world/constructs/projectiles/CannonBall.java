package world.constructs.projectiles;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Not implemented yet
 * 
 * @author Michael Ferolito
 *
 */
public class CannonBall extends Projectile {

	/**
	 * TBI
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	public CannonBall(double a, double b, double c) {
		super(a, b, c);
	}

	@Override
	/**
	 * TBI
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(position.x, position.y, position.z);
		glut.glutSolidSphere(1, 1, 1);
		gl2.glTranslated(-position.x, -position.y, -position.z);
	}

}
