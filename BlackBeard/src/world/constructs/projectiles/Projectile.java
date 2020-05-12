package world.constructs.projectiles;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.constructs.blocks.Clippable;
import world.viewport.MVector;

public abstract class Projectile implements Clippable {
	protected MVector position;
	
	public Projectile(double a,double b,double c){
		position = new MVector(a,b,c);
	}

	@Override
	public abstract void draw(GLUT glut, GLU glu, GL gl, GL2 gl2);

	@Override
	public boolean containsPoint(double x, double y, double z) {
		return false;
	}

	@Override
	public MVector getCoords() {
		return position;
	}

	@Override
	public double[] packCoords() {
		double[] a = { position.x, position.y, position.z };
		return a;
	}

	@Override
	public double getSize() {
		return 0;
	}

	@Override
	public double getX() {
		return position.x;
	}

	@Override
	public double getY() {
		return position.y;
	}

	@Override
	public double getZ() {
		return position.z;
	}

	@Override
	public void movex(double d) {
		position.x += d;
	}

	@Override
	public void movey(double d) {
		position.y += d;

	}

	@Override
	public void movez(double d) {
		position.z += d;

	}

}
