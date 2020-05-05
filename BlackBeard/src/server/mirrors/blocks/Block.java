package server.mirrors.blocks;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import server.util.MVector;

/**
 * 
 * @author Michael Ferolito Common superclass for all Blocks. When created,
 *         forms a "GRASS BLOCK"
 *
 */
public class Block{

	public static Block fromDoubleCoords(double x2, double y2, double z2) {
		return new Block(x2 / 2, y2 / 2, z2 / 2);

	}

	public void moveTo(double a, double b, double c) {
		x=a;
		y=b;
		z=c;
		mv = new MVector(x,y,z);
	}

	public static boolean TESSELATION_ON = false;

	double x, y, z;
	//	private double red = Math.random()*4;
	//	private double green = Math.random()*4;
	//	private double blue = Math.random()*4;
	protected final double size = 0.5;

	protected MVector mv;

	protected boolean isSelected;

	public Block() {
		x = 0;
		y = 0;
		z = 0;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	public Block(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		mv = new MVector(x, y, z);
		isSelected = false;
	}

	protected boolean outOfRange() {
		//		if(MVector.add(mv.copy().mult(-1), JoglPane.currentLoader.getC().getPosition()).getMagnitude()>ViewConstants.ABSOLUTE_RENDER_RANGE) {
		//			return true;
		//		}
		return false;
	}

	
	/**
	 * Check bounds Greater or less than no equal to.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public boolean containsPoint(double x, double y, double z) {
		// the x y z coords of the block are in the center so +/- by size/2 in all
		// directions to get the edges
		double left = this.x - size / 2;
		double right = this.x + size / 2;
		double top = this.y - size / 2;
		double bottom = this.y + size / 2;
		double front = this.z - size / 2;
		double back = this.z + size / 2;
		if (x >= left && x <= right && y >= top && y <= bottom && z >= front && z <= back) {
			return true;
		}

		return false;
	}

	public MVector getCoords() {
		return new MVector(x, y, z);
	}

	
	public double[] packCoords() {
		double[] a = { x, y, z };
		return a;
	}

	public void select(boolean b) {
		isSelected = b;
	}

	
	public double getSize() {
		return 0.5;
	}

	
	public double getX() {
		return x;
	}

	
	public double getY() {
		return y;
	}

	
	public double getZ() {
		return z;
	}

	public boolean containsPoint1(double x, double y, double z) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void movex(double d) {
		x+=d;

	}

	
	public void movey(double d) {
		y+=d;

	}

	
	public void movez(double d) {
		z+=d;

	}

	
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		return;
	}

}
