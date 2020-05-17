package world.constructs.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;
import world.viewport.ObjectLoaderV_C;

/**
 * Provided for convenience. Is just fancy Block. (same deal)
 * @author Michael Ferolito 
 *
 */
public class Grass extends Block {

	public static Grass fromDoubleCoords(double x2, double y2, double z2) {
		return new Grass(x2 / 2, y2 / 2, z2 / 2);

	}

	public Grass() {
		super();
	}

	public Grass(double a, double b, double c) {
		super(a, b, c);
	}

	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		if(outOfRange())
			return;
		gl2.glTranslated(x, y, z);
		// glut.glutSolidCube((float) 0.5);
		gl2.glColor4d(Color.FORESTGREEN.getRed(), Color.FORESTGREEN.getGreen(), Color.FORESTGREEN.getBlue(),4);
		
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
		
//		gl2.glColor3d(0, .5, 0);
//		glut.glutWireCube(0.5001f);
//		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
//
//	    // Front Face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass4.png").getTextureObject(gl));
//	    gl2.glBegin(GL2.GL_QUADS);
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);
//	    gl2.glFlush();
//
//	    // Back Face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass3.png").getTextureObject(gl));
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);
//	    gl2.glFlush();
//
//	    // Top Face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass.png").getTextureObject(gl));
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);
//	    gl2.glFlush();
//
//	    // Bottom Face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass5.png").getTextureObject(gl));
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);
//	    gl2.glFlush();
//
//	    // Right face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass2.png").getTextureObject(gl));
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f( 0.25f, 0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f( 0.25f, -0.25f, 0.25f);
//	    gl2.glFlush();
//
//	    // Left Face
//	    gl.glBindTexture(GL2.GL_TEXTURE_2D, ObjectLoaderV_C.loaderHash.get("Client/Resources/Grass/Grass1.png").getTextureObject(gl));
//	    gl2.glTexCoord2f(0.0f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, -0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.0f); gl2.glVertex3f(-0.25f, -0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.25f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, 0.25f);
//	    gl2.glTexCoord2f(0.0f, 0.25f); gl2.glVertex3f(-0.25f, 0.25f, -0.25f);
//	    gl2.glEnd();
//	    gl2.glFlush();
		
		gl2.glTranslated(-x, -y, -z);

	}
}
