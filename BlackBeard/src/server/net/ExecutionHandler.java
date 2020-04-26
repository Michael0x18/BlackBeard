package server.net;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author Michael Ferolito
 *
 * @since version 1
 * @version 1
 * @deprecated since version 1. For an explanation, see the description under
 *             ExecutionEvent. This is a class formerly used as an internal
 *             mechanism of the Server.
 */
public class ExecutionHandler extends Thread {
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
			if (e != null) {
				Server.clientList.get(e.getTarget()).sendEvent(e);
			}
		}
	}

	public static void addEvent(ExecutionEvent e) {
		executionEvents.add(e);
	}
}
