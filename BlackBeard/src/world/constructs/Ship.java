package world.constructs;

import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import world.constructs.blocks.Block;
import world.constructs.blocks.Clippable;

/**
 * Client sided mirror for a ship. It gets replaced very fast. Players check
 * their coordinate relative to it using Michael's fancy rotation matrix.
 * 
 * @author Michael Ferolito
 * @version 2
 * @version 1
 * 
 *
 */
public class Ship {
	public double x = 0;
	public double y = 0;
	public double z = 0;
	public double rot = 0;

	/**
	 * Arr! Shiver me timbers! This be our ship, boys!
	 */
	public static final String ship = "Wood		0	0	0\n" + "Wood		1	0	0\n" + "Wood		2	0	0\n"
			+ "Wood		3	0	0\n" + "Wood		4	0	0\n" + "Wood		5	0	0\n" + "Wood		6	0	0\n"
			+ "Wood		7	0	0\n" + "Wood		8	0	0\n" + "Wood		9	0	0\n" + "Wood		10	0	0\n"
			+ "Wood		11	0	0\n" + "Wood		12	0	0\n" + "Wood		13	0	0\n" + "Wood		14	0	0\n"
			+ "Wood		15	0	0\n" + "Wood		0	0	1\n" + "Wood		1	0	1\n" + "Wood		2	0	1\n"
			+ "Wood		3	0	1\n" + "Wood		4	0	1\n" + "Wood		5	0	1\n" + "Wood		6	0	1\n"
			+ "Wood		7	0	1\n" + "Wood		8	0	1\n" + "Wood		9	0	1\n" + "Wood		10	0	1\n"
			+ "Wood		11	0	1\n" + "Wood		12	0	1\n" + "Wood		13	0	1\n" + "Wood		14	0	1\n"
			+ "Wood		0	0	-1\n" + "Wood		1	0	-1\n" + "Wood		2	0	-1\n"
			+ "Wood		3	0	-1\n" + "Wood		4	0	-1\n" + "Wood		5	0	-1\n"
			+ "Wood		6	0	-1\n" + "Wood		7	0	-1\n" + "Wood		8	0	-1\n"
			+ "Wood		9	0	-1\n" + "Wood		10	0	-1\n" + "Wood		11	0	-1\n"
			+ "Wood		12	0	-1\n" + "Wood		13	0	-1\n" + "Wood		14	0	-1\n" + "Wood		1	0	2\n"
			+ "Wood		2	0	2\n" + "Wood		3	0	2\n" + "Wood		4	0	2\n" + "Wood		5	0	2\n"
			+ "Wood		6	0	2\n" + "Wood		7	0	2\n" + "Wood		8	0	2\n" + "Wood		9	0	2\n"
			+ "Wood		10	0	2\n" + "Wood		11	0	2\n" + "Wood		12	0	2\n"
			+ "Wood		1	0	-2\n" + "Wood		2	0	-2\n" + "Wood		3	0	-2\n"
			+ "Wood		4	0	-2\n" + "Wood		5	0	-2\n" + "Wood		6	0	-2\n"
			+ "Wood		7	0	-2\n" + "Wood		8	0	-2\n" + "Wood		9	0	-2\n"
			+ "Wood		10	0	-2\n" + "Wood		11	0	-2\n" + "Wood		12	0	-2\n"
			+ "Wood		13	1	-2\n" + "Wood		14	1	-2\n" + "Wood		15	1	-1\n" + "Wood		16	1	0\n"
			+ "Wood		13	1	2\n" + "Wood		14	1	2\n" + "Wood		15	1	1\n" + "Wood		1	1	3\n"
			+ "Wood		2	1	3\n" + "Wood		3	1	3\n" + "Wood		4	1	3\n" + "Wood		5	1	3\n"
			+ "Wood		6	1	3\n" + "Wood		7	1	3\n" + "Wood		8	1	3\n" + "Wood		9	1	3\n"
			+ "Wood		10	1	3\n" + "Wood		11	1	3\n" + "Wood		12	1	3\n"
			+ "Wood		1	1	-3\n" + "Wood		2	1	-3\n" + "Wood		3	1	-3\n"
			+ "Wood		4	1	-3\n" + "Wood		5	1	-3\n" + "Wood		6	1	-3\n"
			+ "Wood		7	1	-3\n" + "Wood		8	1	-3\n" + "Wood		9	1	-3\n"
			+ "Wood		10	1	-3\n" + "Wood		11	1	-3\n" + "Wood		12	1	-3\n"
			+ "Wood		0	1	-2\n" + "Wood		0	1	2\n" + "\n" + "Wood		-1	1	1\n"
			+ "Wood		-1	1	-1\n" + "\n" + "Wood		13	2	-2\n" + "Wood		14	2	-2\n"
			+ "Wood		15	2	-1\n" + "Wood		16	2	0\n" + "Wood		13	2	2\n" + "Wood		14	2	2\n"
			+ "Wood		15	2	1\n" + "Wood		1	2	3\n" + "Wood		2	2	3\n" + "Wood		3	2	3\n"
			+ "Wood		4	2	3\n" + "Wood		5	2	3\n" + "Wood		6	2	3\n" + "Wood		7	2	3\n"
			+ "Wood		8	2	3\n" + "Wood		9	2	3\n" + "Wood		10	2	3\n" + "Wood		11	2	3\n"
			+ "Wood		12	2	3\n" + "Wood		1	2	-3\n" + "Wood		2	2	-3\n"
			+ "Wood		3	2	-3\n" + "Wood		4	2	-3\n" + "Wood		5	2	-3\n"
			+ "Wood		6	2	-3\n" + "Wood		7	2	-3\n" + "Wood		8	2	-3\n"
			+ "Wood		9	2	-3\n" + "Wood		10	2	-3\n" + "Wood		11	2	-3\n"
			+ "Wood		12	2	-3\n" + "Wood		0	2	-2\n" + "Wood		0	2	2\n" + "\n"
			+ "Wood		-1	2	1\n" + "Wood		-1	2	-1\n" + "\n" + "Wood		13	3	-2\n"
			+ "Wood		14	3	-2\n" + "Wood		15	3	-1\n" + "Wood		17	3	0\n" + "Wood		13	3	2\n"
			+ "Wood		14	3	2\n" + "Wood		15	3	1\n" + "Wood		1	3	3\n" + "Wood		2	3	3\n"
			+ "Wood		3	3	3\n" + "Wood		4	3	3\n" + "Wood		5	3	3\n" + "Wood		6	3	3\n"
			+ "Wood		7	3	3\n" + "Wood		8	3	3\n" + "Wood		9	3	3\n" + "Wood		10	3	3\n"
			+ "Wood		11	3	3\n" + "Wood		12	3	3\n" + "Wood		1	3	-3\n"
			+ "Wood		2	3	-3\n" + "Wood		3	3	-3\n" + "Wood		4	3	-3\n"
			+ "Wood		5	3	-3\n" + "Wood		6	3	-3\n" + "Wood		7	3	-3\n"
			+ "Wood		8	3	-3\n" + "Wood		9	3	-3\n" + "Wood		10	3	-3\n"
			+ "Wood		11	3	-3\n" + "Wood		12	3	-3\n" + "Wood		0	3	-2\n" + "Wood		0	3	2\n"
			+ "Wood		-1	3	0\n" + "Wood		-1	3	1\n" + "Wood		-1	3	-1\n" + "Wood		15	1	0\n"
			+ "Wood		16	3	1\n" + "Wood		16	3	-1\n" + "Wood		0	1	1\n"
			+ "Wood		0	1	-1\n" + "Wood		0	2	1\n" + "Wood		0	2	-1\n" + "Wood		0	3	1\n"
			+ "Wood		0	3	-1\n" + "Wood		0	3	0\n" + "Wood		1	1	1\n"
			+ "Wood		1	1	-1\n" + "Wood		1	2	1\n" + "Wood		1	2	-1\n"
			+ "Wood		1	1	-2\n" + "Wood		1	1	2\n" + "Wood		1	3	0\n" + "Wood		2	4	0\n"
			+ "Wood		3	4	0\n" + "Wood		4	4	0\n" + "Wood		5	4	0\n" + "Wood		6	4	0\n"
			+ "Wood		7	4	0\n" + "Wood		8	4	0\n" + "Wood		9	4	0\n" + "Wood		10	4	0\n"
			+ "Wood		11	4	0\n" + "Wood		12	4	0\n" + "Wood		13	4	0\n" + "Wood		14	4	0\n"
			+ "Wood		15	4	0\n" + "Wood		16	4	0\n" + "Wood		17	4	0\n" + "Wood		18	4	0\n"
			+ "Wood		2	4	1\n" + "Wood		3	4	1\n" + "Wood		4	4	1\n" + "Wood		5	4	1\n"
			+ "Wood		6	4	1\n" + "Wood		7	4	1\n" + "Wood		8	4	1\n" + "Wood		9	4	1\n"
			+ "Wood		10	4	1\n" + "Wood		11	4	1\n" + "Wood		12	4	1\n" + "Wood		13	4	1\n"
			+ "Wood		14	4	1\n" + "Wood		15	4	1\n" + "Wood		16	4	1\n"
			+ "Wood		2	4	-1\n" + "Wood		3	4	-1\n" + "Wood		4	4	-1\n"
			+ "Wood		5	4	-1\n" + "Wood		6	4	-1\n" + "Wood		7	4	-1\n"
			+ "Wood		8	4	-1\n" + "Wood		9	4	-1\n" + "Wood		10	4	-1\n"
			+ "Wood		11	4	-1\n" + "Wood		12	4	-1\n" + "Wood		13	4	-1\n"
			+ "Wood		14	4	-1\n" + "Wood		15	4	-1\n" + "Wood		16	4	-1\n" + "Wood		2	4	2\n"
			+ "Wood		3	4	2\n" + "Wood		4	4	2\n" + "Wood		5	4	2\n" + "Wood		6	4	2\n"
			+ "Wood		7	4	2\n" + "Wood		8	4	2\n" + "Wood		9	4	2\n" + "Wood		10	4	2\n"
			+ "Wood		11	4	2\n" + "Wood		12	4	2\n" + "Wood		13	4	2\n" + "Wood		14	4	2\n"
			+ "Wood		2	4	-2\n" + "Wood		3	4	-2\n" + "Wood		4	4	-2\n"
			+ "Wood		5	4	-2\n" + "Wood		6	4	-2\n" + "Wood		7	4	-2\n"
			+ "Wood		8	4	-2\n" + "Wood		9	4	-2\n" + "Wood		10	4	-2\n"
			+ "Wood		11	4	-2\n" + "Wood		12	4	-2\n" + "Wood		13	4	-2\n"
			+ "Wood		14	4	-2\n" + "Wood		2	4	3\n" + "Wood		3	4	3\n" + "Wood		4	4	3\n"
			+ "Wood		5	4	3\n" + "Wood		6	4	3\n" + "Wood		7	4	3\n" + "Wood		8	4	3\n"
			+ "Wood		9	4	3\n" + "Wood		10	4	3\n" + "Wood		11	4	3\n" + "Wood		12	4	3\n"
			+ "Wood		2	4	-3\n" + "Wood		3	4	-3\n" + "Wood		4	4	-3\n"
			+ "Wood		5	4	-3\n" + "Wood		6	4	-3\n" + "Wood		7	4	-3\n"
			+ "Wood		8	4	-3\n" + "Wood		9	4	-3\n" + "Wood		10	4	-3\n"
			+ "Wood		11	4	-3\n" + "Wood		12	4	-3\n" + "Wood		-1	4	0\n" + "Wood		0	4	0\n"
			+ "Wood		-1	4	1\n" + "Wood		-1	4	-1\n" + "Wood		0	4	1\n"
			+ "Wood		0	4	-1\n" + "Wood		0	4	2\n" + "Wood		0	4	-2\n"
			+ "Wood		1	4	-3\n" + "Wood		1	4	3\n" + "Wood		-1	5	0\n" + "Wood		19	5	0\n"
			+ "Wood		19	4	0\n" + "Wood		20	5	0\n" + "Wood		18	5	0\n" + "Wood		17	5	0\n"
			+ "Wood		16	5	1\n" + "Wood		16	5	-1\n" + "Wood		15	5	1\n"
			+ "Wood		15	5	-1\n" + "Wood		14	5	2\n" + "Wood		14	5	-2\n" + "Wood		13	5	2\n"
			+ "Wood		13	5	-2\n" + "Wood		12	5	-3\n" + "Wood		12	5	3\n"
			+ "Wood		11	5	-3\n" + "Wood		11	5	3\n" + "Wood		10	5	-3\n" + "Wood		10	5	3\n"
			+ "Wood		9	5	-3\n" + "Wood		9	5	3\n" + "Wood		8	5	-3\n" + "Wood		8	5	3\n"
			+ "Wood		8	5	-3\n" + "Wood		8	5	3\n" + "Wood		8	5	-3\n" + "Wood		8	5	3\n"
			+ "Wood		8	5	-3\n" + "Wood		8	5	3\n" + "Wood		7	5	-3\n" + "Wood		7	5	3\n"
			+ "Wood		6	5	-3\n" + "Wood		6	5	3\n" + "Wood		5	5	-3\n" + "Wood		5	5	3\n"
			+ "Wood		4	5	-3\n" + "Wood		4	5	3\n" + "Wood		3	5	-3\n" + "Wood		3	5	3\n"
			+ "Wood		2	5	-3\n" + "Wood		2	5	3\n" + "Wood		1	5	-3\n" + "Wood		1	5	3\n"
			+ "Wood		0	5	-3\n" + "Wood		0	5	3\n" + "Wood		-1	5	-1\n" + "Wood		-1	5	1\n"
			+ "Wood		0	5	-2\n" + "Wood		0	5	2\n" + "Wood		4	5	0\n" + "Wood		4	3	0\n"
			+ "Wood		4	2	0\n" + "Wood		4	1	0\n" + "Wood		4	6	0\n" + "Wood		4	7	0\n"
			+ "Wood		4	8	0\n" + "Wood		4	9	0\n" + "Wood		4	10	0\n" + "Wood		4	11	0\n"
			+ "Wood		4	12	0\n" + "Wood		4	13	0\n" + "Wood		4	14	0\n" + "Wood		4	15	0\n"
			+ "Wood		4	16	0\n" + "Wood		12	5	0\n" + "Wood		12	3	0\n" + "Wood		12	2	0\n"
			+ "Wood		12	1	0\n" + "Wood		12	6	0\n" + "Wood		12	7	0\n" + "Wood		12	8	0\n"
			+ "Wood		12	9	0\n" + "Wood		12	10	0\n" + "Wood		12	11	0\n" + "Wood		12	12	0\n"
			+ "Wood		12	13	0\n" + "Wood		12	14	0\n" + "Wood		5	16	0\n" + "Wood		5	16	1\n"
			+ "Wood		5	16	2\n" + "Wood		5	16	3\n" + "Wood		5	16	4\n"
			+ "Wood		5	16	-1\n" + "Wood		5	16	-2\n" + "Wood		5	16	-3\n"
			+ "Wood		5	16	-4\n" + "Sail		6	16	1\n" + "Sail		6	16	2\n" + "Sail		6	16	3\n"
			+ "Sail		6	16	4\n" + "Sail		6	16	-4\n" + "Sail		6	16	0\n"
			+ "Sail		6	16	-1\n" + "Sail		6	16	-2\n" + "Sail		6	16	-3\n" + "Sail		6	15	1\n"
			+ "Sail		6	15	2\n" + "Sail		6	15	3\n" + "Sail		6	15	4\n"
			+ "Sail		6	15	-4\n" + "Sail		6	15	0\n" + "Sail		6	15	-1\n"
			+ "Sail		6	15	-2\n" + "Sail		6	15	-3\n" + "Sail		6	14	1\n" + "Sail		6	14	2\n"
			+ "Sail		6	14	3\n" + "Sail		6	14	4\n" + "Sail		6	14	-4\n" + "Sail		6	14	0\n"
			+ "Sail		6	14	-1\n" + "Sail		6	14	-2\n" + "Sail		6	14	-3\n" + "Sail		6	13	1\n"
			+ "Sail		6	13	2\n" + "Sail		6	13	3\n" + "Sail		6	13	4\n"
			+ "Sail		6	13	-4\n" + "Sail		6	13	0\n" + "Sail		6	13	-1\n"
			+ "Sail		6	13	-2\n" + "Sail		6	13	-3\n" + "Sail		6	12	1\n" + "Sail		6	12	2\n"
			+ "Sail		6	12	3\n" + "Sail		6	12	4\n" + "Sail		6	12	-4\n" + "Sail		6	12	0\n"
			+ "Sail		6	12	-1\n" + "Sail		6	12	-2\n" + "Sail		6	12	-3\n" + "Sail		6	11	1\n"
			+ "Sail		6	11	2\n" + "Sail		6	11	3\n" + "Sail		6	11	4\n"
			+ "Sail		6	11	-4\n" + "Sail		6	11	0\n" + "Sail		6	11	-1\n"
			+ "Sail		6	11	-2\n" + "Sail		6	11	-3\n" + "Sail		6	10	1\n" + "Sail		6	10	2\n"
			+ "Sail		6	10	3\n" + "Sail		6	10	4\n" + "Sail		6	10	-4\n" + "Sail		6	10	0\n"
			+ "Sail		6	10	-1\n" + "Sail		6	10	-2\n" + "Sail		6	10	-3\n" + "Wood		5	10	0\n"
			+ "Wood		5	10	1\n" + "Wood		5	10	2\n" + "Wood		5	10	3\n" + "Wood		5	10	4\n"
			+ "Wood		5	10	-1\n" + "Wood		5	10	-2\n" + "Wood		5	10	-3\n"
			+ "Wood		5	10	-4\n" + "\n" + "Wood		12	15	0\n" + "Wood		12	15	1\n"
			+ "Wood		12	15	2\n" + "Wood		12	15	3\n" + "Wood		12	15	4\n"
			+ "Wood		12	15	-1\n" + "Wood		12	15	-2\n" + "Wood		12	15	-3\n"
			+ "Wood		12	15	-4\n" + "Sail		13	15	1\n" + "Sail		13	15	2\n" + "Sail		13	15	3\n"
			+ "Sail		13	15	4\n" + "Sail		13	15	-4\n" + "Sail		13	15	0\n"
			+ "Sail		13	15	-1\n" + "Sail		13	15	-2\n" + "Sail		13	15	-3\n" + "Sail		13	14	1\n"
			+ "Sail		13	14	2\n" + "Sail		13	14	3\n" + "Sail		13	14	4\n"
			+ "Sail		13	14	-4\n" + "Sail		13	14	0\n" + "Sail		13	14	-1\n"
			+ "Sail		13	14	-2\n" + "Sail		13	14	-3\n" + "Sail		13	13	1\n" + "Sail		13	13	2\n"
			+ "Sail		13	13	3\n" + "Sail		13	13	4\n" + "Sail		13	13	-4\n" + "Sail		13	13	0\n"
			+ "Sail		13	13	-1\n" + "Sail		13	13	-2\n" + "Sail		13	13	-3\n" + "Sail		13	12	1\n"
			+ "Sail		13	12	2\n" + "Sail		13	12	3\n" + "Sail		13	12	4\n"
			+ "Sail		13	12	-4\n" + "Sail		13	12	0\n" + "Sail		13	12	-1\n"
			+ "Sail		13	12	-2\n" + "Sail		13	12	-3\n" + "Sail		13	11	1\n" + "Sail		13	11	2\n"
			+ "Sail		13	11	3\n" + "Sail		13	11	4\n" + "Sail		13	11	-4\n" + "Sail		13	11	0\n"
			+ "Sail		13	11	-1\n" + "Sail		13	11	-2\n" + "Sail		13	11	-3\n" + "Sail		13	10	1\n"
			+ "Sail		13	10	2\n" + "Sail		13	10	3\n" + "Sail		13	10	4\n"
			+ "Sail		13	10	-4\n" + "Sail		13	10	0\n" + "Sail		13	10	-1\n"
			+ "Sail		13	10	-2\n" + "Sail		13	10	-3\n" + "Sail		13	9	1\n" + "Sail		13	9	2\n"
			+ "Sail		13	9	3\n" + "Sail		13	9	4\n" + "Sail		13	9	-4\n" + "Sail		13	9	0\n"
			+ "Sail		13	9	-1\n" + "Sail		13	9	-2\n" + "Sail		13	9	-3\n" + "Wood		13	9	0\n"
			+ "Wood		12	9	1\n" + "Wood		12	9	2\n" + "Wood		12	9	3\n" + "Wood		12	9	4\n"
			+ "Wood		12	9	-1\n" + "Wood		12	9	-2\n" + "Wood		12	9	-3\n" + "Wood		12	9	-4";

	/**
	 * contains the blocks present in this Ship
	 */
	public CopyOnWriteArrayList<Clippable> blocks;
	/**
	 * the ship's name and unique identifier.
	 */
	public String name;
	public double velocity;

	/**
	 * Creates a new ship instance with the specified name.
	 * @param name
	 */
	public Ship(String name) {
		blocks = new CopyOnWriteArrayList<Clippable>();
		String[] arr = ship.split("\n");
		for (String str : arr) {
			StringTokenizer st = new StringTokenizer(str);
			if (st.hasMoreTokens()) {

				String className = "world.constructs.blocks." + st.nextToken();
				Block b = loadClass(className);
				if (b == null) {
					System.err.println("There was an error processing the file");
					System.exit(1);
				}
				double c = Double.parseDouble(st.nextToken()) / 2;
				double d = Double.parseDouble(st.nextToken()) / 2;
				double e = Double.parseDouble(st.nextToken()) / 2;
				b.moveTo(c - 4, d, e);
				blocks.add(b);
			}

		}
		this.name = name;
	}

	/**
	 * equivalent of the Ship clippable method.
	 * @param glut
	 * @param glu
	 * @param gl
	 * @param gl2
	 */
	public void draw(GLUT glut, GLU glu, GL gl, GL2 gl2) {
		gl2.glTranslated(x, y, z);
		gl2.glRotated(-rot, 0, 1, 0);
		for (Clippable c : blocks) {
			c.draw(glut, glu, gl, gl2);
		}
		gl2.glRotated(rot, 0, 1, 0);
		gl2.glTranslated(-x, -y, -z);

	}

	/**
	 * classloader from file/string. ignores executions.
	 * @param whichClass
	 * @return
	 */
	private static Block loadClass(String whichClass) {
		try {
			Class<?> clazz = Class.forName(whichClass);
			return (Block) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

//	public void sail() {
//		for(Clippable b : blocks) {
//			b.movex(0.1);
//		}
//
//	}
//	
//	public void sailBack() {
//		for(Clippable b : blocks) {
//			b.movex(-0.2);
//		}
//	}

	/**
	 * translate
	 * @param x
	 * @param y
	 * @param z
	 */
	public void moveTo(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * used for parsing.
	 * @param bearing
	 */
	public void turn(double bearing) {
		this.rot = bearing;
	}

	public void setVelocity(String v) {
		this.velocity = Double.parseDouble(v);
		
	}

}
