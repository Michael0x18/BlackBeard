package server.net.daemons;

import server.net.Server;

public class ProjectileDaemonFlushType extends Daemon{
	
	public ProjectileDaemonFlushType() {
		super();
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Server.shots.clear();
//			for(int i = 0; i < Server.shots.size(); ++i) {
////				if(Server.shots.get(i).dead) {
////					Server.shots.remove(i);
////					i--;
////				}
//			}
		}
	}

}
