package world.viewport;


/**
 * 
 * @author Michael Ferolito
 * Created because processing's PVector is gay and doesn't work with jre 1.6
 *
 */
public class MVector {
	public double x;
	public double y;
	public double z;
	
	public MVector(){
		x=0;
		y=0;
		z=0;
	}
	
	public MVector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MVector add(MVector other){
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}
	
	public double getMagnitude(){
		return Math.sqrt(x*x+y*y+z*z);
	}

	public static MVector mult(MVector v, double d) {
		return new MVector(v.x*d,v.y*d,v.z*d);
	}

	public MVector normalize() {
		x = x/getMagnitude();
		y = y/getMagnitude();
		z = z/getMagnitude();
		return this;
	}

	public MVector mult(double d) {
		x*=d;
		y*=d;
		z*=d;
		return this;
		
	}

	public MVector copy() {
		return new MVector(x,y,z);
	}

	public static MVector add(MVector u, MVector v) {
		MVector r = u.copy();
		r.add(v);
		return r;
	}
	

	static public final double map(double value, double istart, double istop, double ostart, double ostop) {
	      return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

}
