package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.viewport.MVector;


/**
 * LightHouse style
 * @author Michael Ferolito
 *
 */
public class Light extends Block{
	private double rotation = 0;

	public static BlockTemplate fromDoubleCoords(double x2, double y2, double z2) {
		return new BlockTemplate(x2 / 2, y2 / 2, z2 / 2);

	}
	
	public void moveTo(double a, double b, double c) {
		x=a;
		y=b;
		z=c;
		mv = new MVector(x,y,z);
	}
	
	public Light() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
	}

	public Light(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);

	}
	
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		rotation+=5;
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		gl2.glTranslated(0, -0.25, 0);
		gl2.glRotated(rotation,0,1,0);
		gl2.glColor4d(Color.GREENYELLOW.getRed(), Color.GREENYELLOW.getGreen(), Color.GREENYELLOW.getBlue(), 128);
		glut.glutSolidCube(0.75f);
		gl2.glRotated(-rotation,0,1,0);
		gl2.glTranslated(0, 0.25, 0);
		gl2.glTranslated(-x, -y, -z);
	}

}
