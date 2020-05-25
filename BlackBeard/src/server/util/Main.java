package server.util;

import world.viewport.Grid;
import world.viewport.JoglPane;

/**
 * A minor tester class
 * 
 * @author Michael Ferolito
 *
 */
public class Main {

	/**
	 * A minor tester class.
	 * 
	 * @param args
	 */
	public static void run(String[] args) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Grid.load("TEMPLE.GRID");
				}
			}

		}.start();
		JoglPane._driver_start(args);
	}

}
