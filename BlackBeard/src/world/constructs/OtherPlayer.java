package world.constructs;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

public class OtherPlayer {

	public static void draw(StructPlayer sp,GLUT glut, GLU glu, GL gl, GL2 gl2) {
		
		gl2.glTranslated(sp.x, sp.y, sp.z);
		//System.out.println(sp.pan);
		gl2.glColor3d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue());
		gl2.glRotated(Math.toDegrees(-sp.pan), 0, 1, 0);
		gl2.glTranslated(0, -0.15, 0);
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(0, -0.15, 0);
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(0, 0.15, 0);
		gl2.glTranslated(0, 0.15, 0);
		
		gl2.glRotated(Math.toDegrees(sp.tilt), 0, 0, 1);
		//System.out.println(sp.tilt);
		glut.glutSolidCube(0.15f);
		gl2.glRotated(Math.toDegrees(-sp.tilt), 0, 0, 1);
		gl2.glRotated(Math.toDegrees(sp.pan), 0, 1, 0);
		
		gl2.glTranslated(-sp.x, -sp.y, -sp.z);
		
		
	}

}
