package client.net;

import java.util.LinkedList;

//import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import world.constructs.blocks.TexLoader;
//import javax.swing.plaf.metal.MetalLookAndFeel;
//import javax.swing.plaf.multi.MultiLookAndFeel;
//import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import world.fx._Mixer;

/**
 * Represents a TCP client class. Default port is 4444 Coupled strongly to other
 * backend client classes in client.net
 * 
 * The Client is the centralized location for the client.net classes. It holds
 * references to other important parts, such as the ServerListener, and the
 * ClientConnector. In contrast to the server networking classes, the client
 * only needs to manage one of each type of buffer and handle, which drastically
 * simplifies its implementation.
 * 
 * @author mferolito676
 * @version 2.5
 * @since Version 1
 */
public class Client {
	public static final boolean VerboseMode = true;

	private static ClientConnector connector;
	/**
	 * The sole instance of the ServerListener.
	 */
	public static ServerListener listener;
	/**
	 * For debug purposes only
	 */
	static boolean isBot = false;

	@SuppressWarnings("deprecation")
	private static volatile LinkedList<ExecutionListener> eListeners = new LinkedList<ExecutionListener>();

	/**
	 * Launches client, equivalent to static void main. b should be false, unless
	 * for testing.
	 * 
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
	 * 
	 * @param s - the listener
	 */
	public static void setServerListener(ServerListener s) {
		listener = s;

	}

	/**
	 * Uses Execution Events. Sends to server. Mainly for sending events to from
	 * Server from a non-back end class
	 * 
	 * @deprecated as of version 2
	 * @param msg
	 */
	public static synchronized void sendExecutionEvent(String msg) {
		ExecutionEvent e = new ExecutionEvent(msg, null);
		for (ExecutionListener l : eListeners) {
			l.execute(e);
		}

	}

	/**
	 * Uses execution events. For communication with the Server. Called by non back
	 * end classes that wish to communicate with the Server.
	 * 
	 * @deprecated as of version 2
	 * @param msg
	 */
	public static void sendExecutionEventToServer(String msg) {
		listener.addEvent(new ExecutionEvent(msg, null));
	}

	/**
	 * Functions like the addActionlistener found in Swing classes.
	 * 
	 * @deprecated as of version 2
	 * @param e - listener e
	 */
	public static void addExecutionListener(ExecutionListener e) {
		eListeners.add(e);
	}

}
