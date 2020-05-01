package client.net;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import com.apple.eawt.AppEvent.AboutEvent;

/**
 * Originally contained a license. This class is the server launcher for the Mac
 * platform. When running this as a cross platform java application, please run
 * from ServerMain, not MacServerMain. Since this is java jdk 8, this class
 * depends on the com.apple.eawt classes.
 * 
 * @author Michael Ferolito
 * @version 2
 * @since 1 Launches the Server. UIManager handling is set up inside Server.
 *        This is a convenience class for mac apps.
 * 
 */
public class MacClientMain {

	/**
	 * ahoy!
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").contains("Mac")) {
			com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
			Image image = Toolkit.getDefaultToolkit().getImage("Server/Build/Server.png");
			application.setDockIconImage(image);
			application.setDockMenu(new PopupMenu("hi"));
			application.setAboutHandler(new com.apple.eawt.AboutHandler() {

				@Override
				public void handleAbout(AboutEvent arg0) {
					try {
						Desktop.getDesktop().browse(new File("Client/Resources/about.html").toURI());
						// Desktop.getDesktop().browse("Server/");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
		}
		Client.launch(false);

	}

}
