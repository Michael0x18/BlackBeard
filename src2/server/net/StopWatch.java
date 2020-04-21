package server.net;

public class StopWatch extends Daemon {

	private int counter = 0;
	private boolean stopped = false;

	public StopWatch() {

	}

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

	public int halt() {
		this.stopped = true;
		return this.counter;
	}

}
