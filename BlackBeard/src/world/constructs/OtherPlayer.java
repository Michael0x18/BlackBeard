package world.constructs;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class OtherPlayer {

	public static void draw(StructPlayer sp,GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(sp.x, sp.y, sp.z);
		gl2.glRotated(Math.toDegrees(sp.pan), 0, 1, 0);
		glut.glutSolidCube(0.5f);
		gl2.glRotated(-Math.toDegrees(sp.pan), 0, 1, 0);
		
		gl2.glTranslated(-sp.x, -sp.y, -sp.z);
		
		
	}

}
