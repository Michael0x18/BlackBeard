package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.viewport.ObjectLoaderV_C;

/**
 * For use on ships. Sail color is javafx.scene.paint.Color.GhostWhite
 * @author Michael Ferolito
 *
 */
public class Sail extends Block {
	
	/**
	 * see super
	 * @param x2
	 * @param y2
	 * @param z2
	 * @return
	 */
	public static Sail fromDoubleCoords(double x2, double y2, double z2) {
		return new Sail(x2/2,y2/2,z2/2);
		
	}

	/**
	 * see super
	 */
	public Sail() {
		super();
	}

	/**
	 * see super
	 * @param x
	 * @param y
	 * @param z
	 */
	public Sail(double x, double y, double z) {
		super(x, y, z);
	}
	
	/**
	 * see super.
	 * white color.
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// glut.glutSolidCube((float) 0.5);
		gl2.glColor4d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue(),4);
//		
//		if(TESSELATION_ON) {
//		gl2.glTranslated(0.125, 0.125, 0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(-0.125, -0.125, -0.125);
//		
//		gl2.glTranslated(-0.125, 0.125, 0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(0.125, -0.125, -0.125);
//		
//		gl2.glTranslated(0.125, -0.125, 0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(-0.125, 0.125, -0.125);
//		
//		gl2.glTranslated(0.125, 0.125, -0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(-0.125, -0.125, 0.125);
//		
//		gl2.glTranslated(-0.125, -0.125, -0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(0.125, 0.125, 0.125);
//		
//		gl2.glTranslated(-0.125, -0.125, 0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(0.125, 0.125, -0.125);
//		
//		gl2.glTranslated(0.125, -0.125, -0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(-0.125, 0.125, 0.125);
//		
//		gl2.glTranslated(-0.125, 0.125, -0.125);
//		glut.glutSolidCube(0.25f);
//		gl2.glTranslated(0.125, -0.125, 0.125);
//		}else {
//			glut.glutSolidCube(0.5f);
//		}
		
		//gl2.glColor3d(0, .5, 0);
		//glut.glutWireCube(0.5001f);

		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Sailcloth/Sailcloth.png").getTextureObject(gl));
	      gl2.glBegin(GL2.GL_QUADS);

	      // Front Face
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);

	      // Back Face
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);

	      // Top Face
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);

	      // Bottom Face
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);

	      // Right face
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);

	      // Left Face
	      gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
	      gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);
	      gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);
	      gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
	      gl2.glEnd();
	      gl2.glFlush();
		
		gl2.glTranslated(-x, -y, -z);

	}

}
