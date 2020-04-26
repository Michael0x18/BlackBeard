package world.constructs.blocks;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * 
 * @author Michael Ferolito
 * @version 2
 * @since 2
 * A BARRIER is a type of Block. It consists of nothing more than an outline.
 * The outline is an arbitrary color. 
 *
 */
public class Barrier extends Block {
	
	/**
	 * same as block. All coordinates are divided by 2 to make the Shtuff easier.
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static Barrier fromDoubleCoords(double x2, double y2, double z2) {
		return new Barrier(x2/2,y2/2,z2/2);
		
	}
	
	/**
	 * same as block
	 */
	public Barrier() {
		super();
	}
	
	/**
	 * Creats a new Barrier with the given coordinates.
	 * @param i
	 * @param j
	 * @param k
	 */
	public Barrier(double i, double j, double k) {
		super(i,j,k);
	}
	
	/**
	 * draws a wire cube on the given gl.
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x,y,z);
		glut.glutWireCube(0.5f);
		gl2.glTranslated(-x,-y,-z);
	}

}
