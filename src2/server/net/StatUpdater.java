package server.net;


public class StatUpdater extends Daemon {

	public void run() {
		try {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			if (Server.verbose)
				e.printStackTrace();
		}
		
		//Server.clientDisplayModel.clear();
		String[] keys = Server.clientList.keySet().toArray(new String[]{"",""});
		for(String s : keys) {
			if(!Server.clientDisplayModel.contains(s)) {
				Server.clientDisplayModel.addElement(s);
			}
			
		}
		
		//Server.clientDisplayModel.clear();

		
		
		
		}catch(Exception e) {
			System.out.println("!");
			if(Server.verbose)
				e.printStackTrace();
		}
		this.run();
	}

}
