package world.constructs;

public class StructPlayer extends Structure{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1966683972301498894L;
	public double x;
	public double y;
	public double z;
	public double pan;
	public double tilt;
	public String ip;
	
	public StructPlayer(double a, double b, double c, double d, double e, String s) {
		x = a;
		y = b;
		z = c;
		pan = d;
		tilt = e;
		ip = s;
	}
	

}
