package client.net;

import javax.swing.JFrame;

/**
 * Represents a loader, which will display a loading animation to the user while
 * JoglPane gldriver sets up the graphics server
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since Version 2
 *
 */
public class Loader extends JFrame {

	/**
	 * UIS
	 */
	private static final long serialVersionUID = 1198480779643014032L;

	public Loader() {
		super();
		this.setUndecorated(true);
		this.setBounds(0, 0, 400, 200);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

}
