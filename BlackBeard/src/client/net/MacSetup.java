package client.net;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import com.apple.eawt.AppEvent.AboutEvent;

/**
 * Performs Mac-specific setup. Generally stuff like the application image,
 * name, help page.
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since 2
 */
public class MacSetup {

	/**
	 * Runs mac specific utilities. Throws exceptions on Windows machines, so the
	 * driver class doesn't call it.
	 */
	public void run() {
		com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
		Image image = Toolkit.getDefaultToolkit().getImage("Server/Build/Server.png");
		application.setDockIconImage(image);
		application.setDockMenu(new PopupMenu("hi"));
		application.getDockMenu().add(new MenuItem("Say HIIII"));
		application.setAboutHandler(new com.apple.eawt.AboutHandler() {

			@Override
			public void handleAbout(AboutEvent arg0) {
				try {
					Desktop.getDesktop().browse(new File("Server/Resources/about.html").toURI());
					// Desktop.getDesktop().browse("Server/");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

}
