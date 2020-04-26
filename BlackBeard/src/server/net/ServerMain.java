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

		Server.launch();
		Thread.sleep(1000);
		Server.startServer();
		// client.Client.launch();
	}

}
