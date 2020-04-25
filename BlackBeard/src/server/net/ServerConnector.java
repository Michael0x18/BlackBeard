package server.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

import server.mirrors.Ship;


public class ServerConnector extends Thread{
	private ServerSocket socket;
	private int port;
	//private BufferedReader reader;
	public static String[] BannedIPs;
	
	
	
	public ServerConnector(int port) throws IOException {
		//reader = new BufferedReader(new FileReader(("Server/Resources/banned.dat")));
		//System.out.println(reader.ready());
		//String in;
		//while((in = reader.readLine()) != null) {
		//	BannedIPs.add(in);
		//	
		//}
		String content = new String(Files.readAllBytes(Paths.get("Server/Resources/banned.dat")));
		BannedIPs = content.split("\n");
		for(String s : BannedIPs) {
			System.out.println(s);
		}
		socket = new ServerSocket(port);
		this.port = port;
		socket.getInetAddress();
		Server.println("The Server is runing at: "+ InetAddress.getLocalHost());
		Server.println("The TCP port is: "+this.port);
		this.start();
	}
	
	public void run() {
		Ship sh = new Ship("Queen_Anne");
		while (true) {
			try {
				Socket s = socket.accept();
				String ip = s.getInetAddress().getHostAddress();
				String content = new String(Files.readAllBytes(Paths.get("Server/Resources/banned.dat")));
				BannedIPs = content.split("\n");
				for(String d : BannedIPs) {
					if(d.equals(ip)){
						Server.println("Attempted connection by: "+ip);
						throw new IOException();
					}
				}
				Server.println(ip + " connected to the server!");
				ClientHandler c = new ClientHandler(s);
				c.check();
				System.out.println("data sent");
				c.sendEvent(new ExecutionEvent("ERROR, MAP IS UNDEFINED",ip));
			} catch (Exception e) {
				
			}
		}
	}

	public void check() {
		return;
		
	}
	
	
	
}
