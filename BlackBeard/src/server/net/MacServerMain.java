package server.net;

@Deprecated
/**
 * Originally contained a license. This class is the server launcher for the Mac
 * platform. When running this as a cross platform java application, please run
 * from ServerMain, not MacServerMain. Since this is java jdk 8, this class
 * depends on the com.apple.eawt classes.
 * 
 * Note that this should be called only by the MacOSX app native launcher
 * toolkit.
 * 
 * IMPORTANT INFO: WHEN BUILDING A JAR FILE: DO NOT SPECITY THIS AS THE TARGET!!
 * 
 * @author Michael Ferolito
 * @version 2
 * @since 1 Launches the Server. UIManager handling is set up inside Server.
 *        This is a convenience class for mac apps.
 * 
 */
public class MacServerMain {

	/**
	 * ahoy!
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").contains("Mac")) {

		}
		Server.launch();
		Thread.sleep(1000);
		Server.startServer();

	}

}
