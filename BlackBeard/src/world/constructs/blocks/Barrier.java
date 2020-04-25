package world.constructs.blocks;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * 
 * @author Michael Ferolito
 * 
 * A BARRIER is a type of Block. It consists of nothing more than an outline.
 * The outline is an arbitrary color. 
 *
 */
public class Barrier extends Block {
	
	public static Barrier fromDoubleCoords(double x2, double y2, double z2) {
		return new Barrier(x2/2,y2/2,z2/2);
		
	}
	
	public Barrier() {
		super();
	}
	
	public Barrier(double i, double j, double k) {
		super(i,j,k);
	}
	
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x,y,z);
		glut.glutWireCube(0.5f);
		gl2.glTranslated(-x,-y,-z);
	}

}
