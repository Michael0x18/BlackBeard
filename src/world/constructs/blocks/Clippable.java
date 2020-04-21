package world.constructs.blocks;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.viewport.MVector;

public interface Clippable {

	/**
	 * Drawable interface
	 * 
	 * @param glut
	 * @param glu
	 * @param gl
	 * @param gl2
	 */
	public abstract void draw(GLUT glut, GLU glu, GL gl, GL2 gl2);

	/**
	 * Check for collision "clip"
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public abstract boolean containsPoint(double x, double y, double z);

	/**
	 * Return coordinates in vector
	 * 
	 * @return
	 */
	public abstract MVector getCoords();

	/**
	 * Should return an double[] with the coordinates
	 * 
	 * @return - double[]
	 */
	public abstract double[] packCoords();

	public abstract double getSize();

	public abstract double getX();
	public abstract double getY();
	public abstract double getZ();
	
	public abstract void movex(double d);
	public abstract void movey(double d);
	public abstract void movez(double d);

}
