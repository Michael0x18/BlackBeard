package server.net;



public class ServerMain {
	
	public static void main(String[] args) throws InterruptedException {
		
		Server.launch();
		Thread.sleep(1000);
		Server.startServer();
		//client.Client.launch();
	}

}
