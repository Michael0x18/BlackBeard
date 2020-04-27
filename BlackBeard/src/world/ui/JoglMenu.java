package world.ui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * A convenience class for setting up the menu UI.
 * 
 * @author Michael Ferolito
 * @version 2
 * @since Version 2
 */
public class JoglMenu {

	/**
	 * Applies a menubar to a JFrame and returns it.
	 * 
	 * @param f - the frame
	 * @return the jmb
	 */
	public static JMenuBar applyMenu(JFrame f) {
		JMenuBar jmb = new JMenuBar();
		f.setJMenuBar(jmb);
		return jmb;
	}

}
