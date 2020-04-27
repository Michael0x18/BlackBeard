package world.constructs.blocks;

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

}
