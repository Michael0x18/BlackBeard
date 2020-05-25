package world.viewport;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javafx.scene.paint.Color;

/**
 * Represents the same drawing code as a normal Player, but without the fluff.
 * 
 * @author Michael Ferolito
 * @version 0
 * @since 2.5
 *
 */
public class PlayerTemplate {

	public static void draw(GL gl, GL2 gl2, GLU glu, GLUT glut) {
		gl2.glTranslated(0, 5.5, 0);
		gl2.glColor3d(Color.GHOSTWHITE.getRed(), Color.GHOSTWHITE.getGreen(), Color.GHOSTWHITE.getBlue());
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(0, -0.15, 0);
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(0, -0.15, 0);
		glut.glutSolidCube(0.15f);
		gl2.glTranslated(0, 0.15, 0);
		gl2.glTranslated(0, 0.15, 0);

	}

}
