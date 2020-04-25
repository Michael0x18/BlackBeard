package client.net;

import java.util.LinkedList;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;



/**
 * 
 * @author mferolito676
 * @version 1
 * Represents a TCP client class
 * Default port is 4444
 * Coupled strongly to other backend client classes in client.net
 */
public class Client {
	public static final boolean VerboseMode = true;
	
	private static ClientConnector connector;
	public static ServerListener listener;
	public static boolean isBot = false;
	
	private static volatile LinkedList<ExecutionListener> eListeners = new LinkedList<ExecutionListener>();
	
	/**
	 * Launches client, equivalent to static void main.
	 * b should be false, unless for testing.
	 * @param b
	 */
	public static void launch(boolean b) {
		try {
			UIManager.setLookAndFeel(new javax.swing.plaf.nimbus.NimbusLookAndFeel());
		} catch (Exception e) {

		}
		
		isBot = b;
		
		connector = new ClientConnector();
		connector.check();
		
		
	}

	/**
	 * Sets the ServerListener that this client communicates with.
	 * @param s - the listener
	 */
	public static void setServerListener(ServerListener s) {
		listener = s;
		
	}

	/**Uses Execution Events.
	 * Sends to server. 
	 * Mainly for sending events to from Server from a non-back end class
	 * @param msg
	 */
	public static synchronized void sendExecutionEvent(String msg) {
		ExecutionEvent e = new ExecutionEvent(msg,null);
		for(ExecutionListener l : eListeners) {
			l.execute(e);
		}
		
	}
	
	/** Uses execution events.
	 * For communication with the Server. Called by non
	 * back end classes that wish to communicate with the Server.
	 * 
	 * @param msg
	 */
	public static void sendExecutionEventToServer(String msg) {
		listener.addEvent(new ExecutionEvent(msg,null));
	}
	
	/** Functions like the addActionlistener found in Swing classes.
	 * 
	 * @param e - listener e
	 */
	public static void addExecutionListener(ExecutionListener e) {
		eListeners.add(e);
	}

}
