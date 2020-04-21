package server.net;

public abstract class Daemon extends Thread {
	
	public Daemon() {
		this.setDaemon(true);
	}

}
