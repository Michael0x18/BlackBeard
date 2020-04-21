package world.constructs.blocks;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

public class Teapot extends Block{
	
	public static Teapot fromDoubleCoords(double x2, double y2, double z2) {
		return new Teapot(x2/2,y2/2,z2/2);
		
	}
	
	public Teapot(double i, double d, double j) {
		super(i,d,j);
	}
	
	public Teapot() {
		super();
	}

	@Override
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glColor3d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue());
		gl2.glTranslated(x, y, z);
		glut.glutSolidTeapot(0.25);
		//glut.glutWireCube(0.5f);
		gl2.glTranslated(-x, -y, -z);
		
	}

}
