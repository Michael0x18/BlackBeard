package world.constructs.projectiles;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

public class MusketShot extends Projectile {
	
	public MusketShot(double a, double b, double c) {
		super(a,b,c);
	}

	@Override
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(position.x,position.y, position.z);
		gl2.glColor3d(Color.STEELBLUE.getRed(), Color.STEELBLUE.getGreen(), Color.STEELBLUE.getBlue());
		glut.glutSolidSphere(0.15, 10, 10);
//		glut.glutSolidCube(0.15f);
		gl2.glTranslated(-position.x,-position.y, -position.z);
	}

}
