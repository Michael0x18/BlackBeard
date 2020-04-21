package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.viewport.JoglPane;
import world.viewport.MVector;

public class Sail extends Block {
	
	public static Sail fromDoubleCoords(double x2, double y2, double z2) {
		return new Sail(x2/2,y2/2,z2/2);
		
	}

	public Sail() {
		super();
	}

	public Sail(double x, double y, double z) {
		super(x, y, z);
	}
	
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// glut.glutSolidCube((float) 0.5);
		gl2.glColor4d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue(),4);
		
		if(TESSELATION_ON) {
		gl2.glTranslated(0.125, 0.125, 0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(-0.125, -0.125, -0.125);
		
		gl2.glTranslated(-0.125, 0.125, 0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(0.125, -0.125, -0.125);
		
		gl2.glTranslated(0.125, -0.125, 0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(-0.125, 0.125, -0.125);
		
		gl2.glTranslated(0.125, 0.125, -0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(-0.125, -0.125, 0.125);
		
		gl2.glTranslated(-0.125, -0.125, -0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(0.125, 0.125, 0.125);
		
		gl2.glTranslated(-0.125, -0.125, 0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(0.125, 0.125, -0.125);
		
		gl2.glTranslated(0.125, -0.125, -0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(-0.125, 0.125, 0.125);
		
		gl2.glTranslated(-0.125, 0.125, -0.125);
		glut.glutSolidCube(0.25f);
		gl2.glTranslated(0.125, -0.125, 0.125);
		}else {
			glut.glutSolidCube(0.5f);
		}
		
		//gl2.glColor3d(0, .5, 0);
		//glut.glutWireCube(0.5001f);

		gl2.glTranslated(-x, -y, -z);

	}

}
