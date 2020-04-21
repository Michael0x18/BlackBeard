package server.net;

import java.util.LinkedList;
import java.util.Queue;

public class ExecutionHandler extends Thread{
	private static ExecutionHandler h;
	public static Queue<ExecutionEvent> executionEvents = new LinkedList<ExecutionEvent>();
	
	public static void launch() {
		h = new ExecutionHandler();
		h.setDaemon(true);
		h.start();
	}
	
	public void run() {
		
		while (true) {
			ExecutionEvent e = executionEvents.poll();
			if(e != null) {
				Server.clientList.get(e.getTarget()).sendEvent(e);
			}
		}
	}
	
	public static void addEvent(ExecutionEvent e) {
		executionEvents.add(e);
	}
}
