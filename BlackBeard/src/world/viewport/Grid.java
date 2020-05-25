package world.viewport;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import world.constructs.blocks.BGBlock;
import world.constructs.blocks.Barrier;
import world.constructs.blocks.Block;
import world.constructs.blocks.Clay;
import world.constructs.blocks.Clippable;
import world.constructs.blocks.Null;
import world.constructs.blocks.Obsidian;
import world.constructs.Ship;
import world.constructs.StructPlayer;
import world.constructs.blocks.Stone;
import world.constructs.blocks.Teapot;
import world.constructs.projectiles.Projectile;
import world.io.FileWrapper;

/**
 * Holds world
 * 
 * @author Michael Ferolito
 *
 */
public class Grid {
	/**
	 * The blocks.
	 */
	public static CopyOnWriteArrayList<Clippable> world = new CopyOnWriteArrayList<Clippable>();
	/**
	 * The ships.
	 */
	public static CopyOnWriteArrayList<Ship> ships = new CopyOnWriteArrayList<Ship>();
	/**
	 * The ship buffer.
	 */
	public static CopyOnWriteArrayList<Ship> newShips = new CopyOnWriteArrayList<Ship>();
	/**
	 * The Player buffer.
	 */
	public static CopyOnWriteArrayList<StructPlayer> players = new CopyOnWriteArrayList<StructPlayer>();
	/**
	 * The shot buffer.
	 */
	public static CopyOnWriteArrayList<Projectile> shots = new CopyOnWriteArrayList<Projectile>();

	/**
	 * Loads a simple preset grid.
	 */
	public static void loadTemple() {
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				Grid.world.add(Clay.fromDoubleCoords(i, 0, j));
			}
		}
		Grid.world.add(Clay.fromDoubleCoords(50, 1, 50));
		Grid.world.add(Clay.fromDoubleCoords(51, 1, 51));
		Grid.world.add(Clay.fromDoubleCoords(50, 1, 51));
		Grid.world.add(Clay.fromDoubleCoords(50, 1, 51));
	}

	/**
	 * Loads a Grid from a file.
	 * 
	 * @param filename
	 */
	public static void load(String filename) {
		Grid.world.clear();
		FileWrapper fw;
		try {
			fw = new FileWrapper(filename);
			while (fw.ready()) {
				String str = fw.readLine();
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
					b.moveTo(c, d, e);
					Grid.world.add(b);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Classloader stuff.
	 * 
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

	/**
	 * Loads a crummy testing thingy that Michael used to debug the camera. It was
	 * left in because Arthur Dent found the construction notice in the basement.
	 * The lights had gone and so had the stairs. He found it in the bottom of a
	 * locked filing cabinet in a disused lavatory with a sign on the door that read
	 * "Beware of Leopard."
	 * 
	 * Thats why its still in here.
	 */
	public static void loadTowerOfBabel() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Grid.world.add(new Stone(0.5 * i, -0.5, 0.5 * j));
			}
		}
		Grid.world.add(new Obsidian(3.5, 0.5, 0));
		Grid.world.add(new Block(3.5, 0, 0));
		Grid.world.add(new Clay(3, 0, 0));
		Grid.world.add(new Block(2.5, 0, 0));
		Grid.world.add(new Block(1.5, 0, 0));
		Grid.world.add(new Block(1, 0, 0));
		Grid.world.add(new Block(3.5, 1, 0.5));
		Grid.world.add(new Block(4, 1.5, 0.5));
		Grid.world.add(new Block(4.5, 2, 0.5));
		Grid.world.add(new Block(4.5, 2, 0));
		Grid.world.add(new Block(4, 2.5, 0));
		Grid.world.add(new Block(3.5, 3, 0));
		Grid.world.add(new Block(3, 3.5, 0));
		Grid.world.add(new Block(2.5, 4, 0));
		Grid.world.add(new Block(2, 4.5, 0));
		Grid.world.add(new Block(1.5, 5, 0));
		Grid.world.add(new Block(1, 5.5, 0));
		Grid.world.add(new Block(0.5, 6, 0));
		Grid.world.add(new Block(0, 6, 0));
		Grid.world.add(new Block(0, 6, 0.5));
		Grid.world.add(new Block(0, 6.5, 1));
		Grid.world.add(new Block(0, 7, 1.5));
		Grid.world.add(new Block(0, 7.5, 2.0));
		Grid.world.add(new Block(0, 8, 2.5));
		Grid.world.add(new Barrier(0, 8.5, 3));
		Grid.world.add(new Block(0, 9, 3.5));
		Grid.world.add(new Block(0, 9.5, 4));
		Grid.world.add(new Block(0, 10, 4.5));
		Grid.world.add(new Barrier(0, 10.5, 5));
		Grid.world.add(new Block(0.5, 11, 5));
		Grid.world.add(new Teapot(1, 11.5, 5));
		Grid.world.add(new Teapot(1.5, 12, 5));
		Grid.world.add(new Teapot(2, 12.5, 5));
		Grid.world.add(new Teapot(2.5, 13, 5));
		Grid.world.add(new Teapot(3, 13.5, 5));
		Grid.world.add(new Teapot(3.5, 14, 5));
		Grid.world.add(new Teapot(4, 14.5, 5));
		Grid.world.add(new Teapot(4.5, 15, 5));
		Grid.world.add(new Teapot(5, 15, 5));
		Grid.world.add(new Teapot(5, 15.5, 4.5));
		Grid.world.add(new Null(5, 16, 4));
		Grid.world.add(new Null(5, 16.5, 3.5));
		Grid.world.add(new Null(5, 17, 3));
		Grid.world.add(new Null(5, 17.5, 2.5));
		Grid.world.add(new Teapot(5, 18, 2));
		Grid.world.add(new BGBlock(0, 0, 0));

	}

}
