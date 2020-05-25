package server.net;

import server.net.daemons.Daemon;

/**
 * Yet another cleanup Daemon
 * 
 * @author Michael Ferolito
 * @since version 1
 * @version 2.5
 */
public class StatUpdater extends Daemon {

	/**
	 * Not a user called class. Updates the HashMap-backed DisplayModel used with
	 * the GUI.
	 */
	public void run() {
		try {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				if (Server.verbose)
					e.printStackTrace();
			}

			// Server.clientDisplayModel.clear();
			String[] keys = Server.clientList.keySet().toArray(new String[] { "", "" });
			for (String s : keys) {
				if (!Server.clientDisplayModel.contains(s)) {
					Server.clientDisplayModel.addElement(s);
				}

			}

			// Server.clientDisplayModel.clear();

		} catch (Exception e) {
			System.out.println("!");
			if (Server.verbose)
				e.printStackTrace();
		}
		this.run();
	}

}
