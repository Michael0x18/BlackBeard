package server.net;


public class ServerDaemon extends Daemon {

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
				//PlayerProcessor.getpProcess().execute(":exec");
				String str = "";
				//String thisIp = socket.getInetAddress().getHostAddress();
				// System.out.println(thisIp);
				synchronized (this) {
					for (String s : Server.playerCoords.keySet()) {
						if (!s.equals("Server++NULL")) {
							str += Server.playerCoords.get(s);
							str += "_";

						}
					}

				}
				if (str.length() > 0) {
					// System.out.println(str.substring(0, str.length() - 1));\
					if (str.substring(0, str.length() - 1).equals("null")) {
						System.err.println("NULL!!!!!!");
					}
					//PlayerProcessor.getpProcess().execute(":exec<PlayerData>" + str.substring(0, str.length() - 1));
					
				}
				Server.playerCoords.clear();
				
			} catch (InterruptedException e) {
				if (Server.verbose) {
					e.printStackTrace();
				}
			}

		}
	}

}
