package client.net;


import server.net.MacSetup;
import world.fx.SoundClip;

/**
 * Originally contained a license. This class is the server launcher for the Mac
 * platform. When running this as a cross platform java application, please run
 * from ServerMain, not MacServerMain. Since this is java jdk 8, this class
 * depends on the com.apple.eawt classes.
 * 
 * @author Michael Ferolito
 * @version 2
 * @since 1 Launches the Server. UIManager handling is set up inside Server.
 *        
 * 
 */
public class ClientMain {

	/**
	 * ahoy!
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").contains("Mac")) {
			MacSetup ms = (MacSetup)loadClass("server.net.MacSetup");
			ms.run();
		}
		Client.launch(false);
		//SoundClip c = new SoundClip("Client/Resources/Sounds/OnTheHighSeas.wav");
		

	}
	private static Object loadClass(String whichClass) {
		try {
			Class<?> clazz = Class.forName(whichClass);
			return clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
