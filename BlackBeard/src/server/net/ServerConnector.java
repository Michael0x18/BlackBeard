package server.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import server.mirrors.Ship;

/**
 * 
 * @author Michael Ferolito
 * @since Version 1
 * @version 2.0 Represents a ServerConnector. This is the class whose sole
 *          instance holds the ServerSocket to which all connection are made. It
 *          constantly polls the socket for connections, and upon detection
 *          immediately transfers it to a ClientHandler. It then continues.
 *          IMPORTANT: The ServerConnector is a direct subclass of Thread. It is
 *          not a daemon. Therefore, it will stop the JVM from exiting if it is
 *          the only Thread running.
 *
 */
public class ServerConnector extends Thread {
	private ServerSocket socket;
	private int port;
	// private BufferedReader reader;
	/**
	 * banned ips.
	 */
	public static String[] BannedIPs;

	/**
	 * Should be called only from the Server class. Sers up and runs the
	 * ServerConnector
	 * 
	 * @param port
	 * @throws IOException
	 */
	public ServerConnector(int port) throws IOException {
		// reader = new BufferedReader(new FileReader(("Server/Resources/banned.dat")));
		// System.out.println(reader.ready());
		// String in;
		// while((in = reader.readLine()) != null) {
		// BannedIPs.add(in);
		//
		// }
		String content = new String(Files.readAllBytes(Paths.get("Server/Resources/banned.dat")));
		BannedIPs = content.split("\n");
		for (String s : BannedIPs) {
			System.out.println(s);
		}
		socket = new ServerSocket(port);
		this.port = port;
		socket.getInetAddress();
		Server.println("The Server is runing at: " + InetAddress.getLocalHost());
		Server.println("The TCP port is: " + this.port);
		this.start();
	}

	@SuppressWarnings("unused")
	public void run() {
		//Ship sh = new Ship("Queen_Anne");
		Ship sh2 = new Ship("Flying_Duchman");
		//sh2.z = 10;
		//sh2.x = 10;
		while (true) {
			try {
				Socket s = socket.accept();
				String ip = s.getInetAddress().getHostAddress();
				String content = new String(Files.readAllBytes(Paths.get("Server/Resources/banned.dat")));
				BannedIPs = content.split("\n");
				for (String d : BannedIPs) {
					if (d.equals(ip)) {
						Server.println("Attempted connection by: " + ip);
						throw new IOException();
					}
				}
				Server.println(ip + " connected to the server!");
				ClientHandler c = new ClientHandler(s);
				c.check();
				System.out.println("data sent");
				// c.sendEvent(new ExecutionEvent("ERROR, MAP IS UNDEFINED",ip));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void check() {
		return;

	}

}
