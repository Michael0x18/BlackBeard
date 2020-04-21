package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class Null extends Barrier{
	
	public static Null fromDoubleCoords(double x2, double y2, double z2) {
		return new Null(x2/2,y2/2,z2/2);
		
	}
	
	public Null() {
		super();
	}
	
	public Null(double a, double b, double c) {
		super(a,b,c);
	}
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		
	}

}
