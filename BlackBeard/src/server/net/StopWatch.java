package server.net;

import server.net.daemons.Daemon;

/**
 * A CAS Daemon used by the ClientHandlers to manage ping.
 * @author Michael Ferolito
 * @since Version 1
 * @version 2.5
 *
 */
public class StopWatch extends Daemon {

	private int counter = 0;
	private boolean stopped = false;

	/**
	 * null constructor.
	 */
	public StopWatch() {

	}

	/**
	 * increnemts a counter every millisecond.
	 */
	public void run() {
		while (true) {
			counter++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				if (Server.verbose)
					;
				e.printStackTrace();
			}
			
			if(stopped) {
				return;
			}

		}
	}

	/**
	 * Note. Method is called Halt and not Stop because Stop in Thread is final.
	 * kills the Thread cleanly with no deadlock and returns the counter value.
	 * @return
	 */
	public int halt() {
		this.stopped = true;
		return this.counter;
	}

}
