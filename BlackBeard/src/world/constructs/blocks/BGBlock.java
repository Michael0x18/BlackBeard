package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

public class BGBlock extends Block{
	
	public static BGBlock fromDoubleCoords(double x2, double y2, double z2) {
		return new BGBlock(x2/2,y2/2,z2/2);
		
	}

	public BGBlock() {
		super();
	}
	
	public BGBlock(double a, double b, double c) {
		super(a,b,c);
	}
	
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		gl2.glColor4d(Color.LAWNGREEN.getRed(), Color.LAWNGREEN.getGreen(), Color.LAWNGREEN.getBlue(),40);
		gl2.glBegin(gl.GL_TRIANGLES);
		gl2.glVertex3d(0.25, -0.25, 0.25);
		gl2.glVertex3d(-0.25, -0.25, -0.25);
		gl2.glVertex3d(0, 0.25, 0.05);
		gl2.glVertex3d(-0.25, -0.25, 0.25);
		gl2.glVertex3d(0.25, -0.25, -0.25);
		gl2.glVertex3d(0.1, 0.25, 0);
		gl2.glEnd();
		gl2.glTranslated(-x, -y, -z);
		
	}
	
	public boolean containsPoint(double x, double y, double z) {
		return false;
	}

}
