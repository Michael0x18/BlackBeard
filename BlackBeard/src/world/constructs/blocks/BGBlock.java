package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

/**
 * unclippable block. Looks like a plant.
 * @author Michael Ferolito
 * @version 2
 * @since 2
 * 
 *
 */
public class BGBlock extends Block{
	
	/**
	 * See Block.fromDoubleCoords
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static BGBlock fromDoubleCoords(double x2, double y2, double z2) {
		return new BGBlock(x2/2,y2/2,z2/2);
		
	}

	/**
	 * see Block default constructor
	 */
	public BGBlock() {
		super();
	}
	
	/**
	 * See Block parameterized constructor.
	 * @param a
	 * @param b
	 * @param c
	 */
	public BGBlock(double a, double b, double c) {
		super(a,b,c);
	}
	
	/**
	 * Displays two uneven triangles.
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		gl2.glColor4d(Color.LAWNGREEN.getRed(), Color.LAWNGREEN.getGreen(), Color.LAWNGREEN.getBlue(),40);
		gl2.glBegin(GL.GL_TRIANGLES);
		gl2.glVertex3d(0.25, -0.25, 0.25);
		gl2.glVertex3d(-0.25, -0.25, -0.25);
		gl2.glVertex3d(0, 0.25, 0.05);
		gl2.glVertex3d(-0.25, -0.25, 0.25);
		gl2.glVertex3d(0.25, -0.25, -0.25);
		gl2.glVertex3d(0.1, 0.25, 0);
		gl2.glEnd();
		gl2.glTranslated(-x, -y, -z);
		
	}
	
	/**
	 * returns false because this should never experience a collision.
	 */
	public boolean containsPoint(double x, double y, double z) {
		return false;
	}

}
