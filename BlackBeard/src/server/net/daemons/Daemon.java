package server.net.daemons;

/**
 * 
 * @author Michael Ferolito In UNIX, a daemon is a thread that runs in the
 *         background. This Daemon class is the superclass for every Server
 *         Daemon.
 */
public abstract class Daemon extends Thread {

	/**
	 * sets isDaemon to true on construction. Daemons do not prevent the JVM from exiting.
	 */
	public Daemon() {
		this.setDaemon(true);
	}

}
