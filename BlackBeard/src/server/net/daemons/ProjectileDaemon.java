package server.net.daemons;

import server.mirrors.projectiles.Projectile;
import server.net.Server;

/**
 * A Server Daemon for the handling of projectiles.
 * 
 * @author Michael Ferolito
 *
 */
public class ProjectileDaemon extends Daemon {
	private ProjectileDaemon theDaemon = null;

	@SuppressWarnings("unused")
	/**
	 * Called once, preferably in a static block.
	 * 
	 * @throws DaemonRunningException
	 */
	public ProjectileDaemon() throws DaemonRunningException {
		super();
		if (theDaemon != null) {
			throw new DaemonRunningException();
		} else {
			theDaemon = this;
			this.start();
			ProjectileDaemonFlushType p = new ProjectileDaemonFlushType();
		}
	}

	/**
	 * Overridden run method.
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
				String s = "";
				for (Projectile p : Server.shots) {
					s += ";";
					s += p.getSerialInfo();
				}
				for (String ip : Server.clientList.keySet()) {
					if (Server.clientList.get(ip) != null) {
						Server.clientList.get(ip).addMessage(":shots" + s, "projectileDaemon.run");
					}
				}

			} catch (Exception e) {
				if (Server.verbose) {
					e.printStackTrace();
				}
			}
		}
	}

}
