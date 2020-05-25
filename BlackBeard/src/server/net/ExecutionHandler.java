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

	/**
	 * Launch() sets up and runs the Internal ExecutionDaemon.
	 */
	public static void launch() {
		h = new ExecutionHandler();
		h.setDaemon(true);
		h.start();
	}

	/**
	 * Overrides Thread run. Note: This was before the addition of Daemon.
	 */
	public void run() {

		while (true) {
			ExecutionEvent e = executionEvents.poll();
			if (e != null) {
				Server.clientList.get(e.getTarget()).sendEvent(e);
			}
		}
	}

	/**
	 * Adds an execution Event to the Server Queue.
	 * 
	 * @param e
	 */
	public static void addEvent(ExecutionEvent e) {
		executionEvents.add(e);
	}
}
