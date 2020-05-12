package world.constructs.projectiles;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class MusketShot extends Projectile {
	
	public MusketShot(double a, double b, double c) {
		super(a,b,c);
	}

	@Override
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(position.x,position.y, position.z);
		//glut.glutSolidSphere(1, 1, 1);
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(-position.x,-position.y, -position.z);
	}

}