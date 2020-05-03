package server.net;


/**
 * Originally contained a license.
 * 
 * @author Michael Ferolito
 * @version 2
 * @since 1 Launches the Server. UIManager handling is set up inside Server.
 *
 */
public class ServerMain {

	/**
	 * ahoy!
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//System.out.println(System.getProperty("os.name"));
		if(System.getProperty("os.name").contains("Mac")) {
			MacSetup ms = (MacSetup)loadClass("server.net.MacSetup");
			ms.run();
		}
		Server.launch();
		Thread.sleep(1000);
		Server.startServer();
		
		
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
