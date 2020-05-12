package server.net;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import server.mirrors.Ship;
import server.mirrors.projectiles.Projectile;
import server.net.daemons.ProjectileDaemon;
import server.net.daemons.ServerDaemon;

/**
 * Represents the main centralized location of the Server. The Server implements
 * a CAS Erlang type thread model. In other words, it contains a bunch of
 * threads that each do their own thing. There are few, if any, synchronized
 * blocks. Instead, Threads measure the value of something they are attempting
 * to change, and try to change it until their operation succeeds. When reading
 * and writing concurrently, Atomic operations are used to ensure the accurate
 * measurement from multiple Thread pools. The erlang part fo the Server is as
 * follows. BEAM is cleaner than the JVM, but its threading is more... Involved.
 * Here is the philosophy: If a client is sending malicious input to the Server
 * in the hopes of causing a buffer overflow, it does happen. The part of the
 * Server that deals with that client is specific only to that client. That
 * Thread crashes due to the buffer, and the malicious client is kicked out. If
 * the intent was only to fool the Server, the Thread crashes, picks itself
 * up(the entire Run method is inside of a try catch block in the
 * ClientHandlers, which this Server owns) and continues execution. Any sketch
 * input is dropped after causing an exception, and the Server pretends that
 * nothing has happened. This makes whoever is trying to break the Server have a
 * lot of lag (try catch takes a really long time) and possibly get kicked if
 * the lag reaches over three seconds. CAS is the underlying concept of Atomic
 * classes in the Concurrent package, as well as CopyOnWriteArrayList and
 * ConcurrentHashMap. This type of programming can also be considered
 * Relativistic Programming, a special subset of parallel programming where
 * events happen in an indistinct order.
 * 
 * @author Michael Ferolito
 * @since Version 1
 * @version 2
 *
 */
public class Server {
	/**
	 * Flag set to true if the Server should dump all the caught exceptions to the
	 * shell.
	 */
	public static final boolean verbose = true;
	/**
	 * The port to use. 4444 is open in Shelby's room.
	 */
	public static final int port = 4444;
	/**
	 * The default port to use. 4444 is open in Shelby's room.
	 */
	public static final int defaultPort = 4444;
	private static ServerWindow s;
	/**
	 * The console used for output to the GUI.
	 */
	public static final JTextArea console = new JTextArea();
	private static ServerConnector connector;
	private static boolean isRunning = false;
	/**
	 * Backend, used for the JList
	 */
	public static DefaultListModel<String> clientDisplayModel = new DefaultListModel<>();
	/**
	 * Display selector for clients.
	 */
	public static JList<String> clientDisplay = new JList<String>(clientDisplayModel);
	/**
	 * Holds all ClientHandler instances.
	 */
	public static final ConcurrentHashMap<String, ClientHandler> clientList = new ConcurrentHashMap<String, ClientHandler>();
	/**
	 * Server stdin.
	 */
	public static final JTextArea shell = new JTextArea();
	/**
	 * Holds all the banned players and ips.
	 */
	static File banned;
	/**
	 * Holds all the ExecutionListeners registered with this Server.
	 */
	@SuppressWarnings("deprecation")
	public static final CopyOnWriteArrayList<ExecutionListener> eListeners = new CopyOnWriteArrayList<ExecutionListener>();
	/**
	 * Holds all the player coordinates.
	 */
	public static final ConcurrentHashMap<String, String> playerCoords = new ConcurrentHashMap<String, String>();
	// public static final CopyOnWriteArrayList<SnowBall> shotsList = new
	// CopyOnWriteArrayList<SnowBall>(); TODO move to mirrors
	// public static final CopyOnWriteArrayList<Block> hitBlockList = new
	// CopyOnWriteArrayList<Block>();
	/**
	 * Holds the temporarily banned player ips.
	 */
	public static final CopyOnWriteArrayList<String> bannedIPs = new CopyOnWriteArrayList<String>();

	/**
	 * writes to the banned file.
	 */
	public static BufferedWriter bannedWriter;
	// public static OtherPlayer[] players;

	public static CopyOnWriteArrayList<Ship> ships = new CopyOnWriteArrayList<Ship>();
	public static ConcurrentHashMap<String, Ship> shipQuick = new ConcurrentHashMap<String, Ship>();
	public static CopyOnWriteArrayList<Projectile> shots = new CopyOnWriteArrayList<Projectile>();

	/**
	 * Performs all Server related setups.
	 */
	public static void launch() {

		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			// banned = new File("Server/Resources/banned.dat");
			// FileOutputStream out = new FileOutputStream("the-file-name");
			// banned = new File();
			// System.out.println(banned.exists());
			// System.out.println(banned.canWrite());

		} catch (Exception e) {
			Server.println("Error: could not locate file: Server/Resources/banned.dat");
			Server.println("Please ensure the file is there, and that it is not damaged");
			Server.println("Then, please restart the program.");
		}
		Server.println("**** HEADLESS OPERATION ACTIVE ****");
		s = new ServerWindow();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

		}
		Server.console.setText("");

		s.check();

		// ExecutionHandler.launch();
		ServerDaemon sd = new ServerDaemon();
		sd.setDaemon(true);
		sd.start();
		ProjectileDaemon pd = new ProjectileDaemon();
		pd.start();
		// MapCreator.createMap(); TODO- generate dynamic map
		// PlayerProcessor.launch();
	}

	/**
	 * Initializes and starts the Server, and all related resources.
	 */
	public static void startServer() {
		isRunning = true;
		try {
			connector = new ServerConnector(port);
			connector.check();
			FileWriter fw = new FileWriter("Server/Resources/banned.dat", true);
			bannedWriter = new BufferedWriter(fw);
//			for(String str:ServerConnector.BannedIPs) {
//				bannedWriter.write(str);
//				System.out.println(str);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the specified IP address to the list of banned players.
	 * 
	 * @param l
	 */
	public static void ban(List<String> l) {
		for (String s : l) {
			bannedIPs.add(s);
		}
		kill(l);
	}

	/**
	 * Writes the specified IP address to a file to keep it across Server restarts.
	 * 
	 * @param l
	 */
	public static void permaBan(List<String> l) {
		for (String s : l) {
			try {
				bannedWriter.write(s + "\n");
				bannedWriter.flush();
			} catch (IOException e) {
				if (Server.verbose)
					e.printStackTrace();
			}

		}
		kill(l);
	}

	/**
	 * prints to the Server, followed by a newline. No CR is used.
	 * 
	 * @param s
	 */
	public static void println(String s) {
		console.setText((console.getText() + s + "\n"));
	}

	/**
	 * prints to the Server console.
	 * 
	 * @param s
	 */
	public static void print(String s) {
		console.setText((console.getText() + s));
	}

	/**
	 * returns the state of the Server.
	 * 
	 * @return
	 */
	public static boolean getIsRunning() {
		return isRunning;
	}

	/**
	 * closes the socket associated with the given IP.
	 * 
	 * @param l
	 */
	public static void disconnect(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":disc");
		}
	}

	/**
	 * kill -9
	 * 
	 * @param l
	 */
	public static void terminate(List<String> l) {
		for (String s : l) {
			clientList.get(s).halt();
		}
	}

	/**
	 * Prints a helpful message to the Clients.
	 * 
	 * @param l
	 */
	public static void yeet(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":echo It is Time: To Yeet the Meat!!!!!");
		}
	}

	/**
	 * does nothing, looks sick. See Terry A. Davis's descriptions of CIA agents to
	 * be very confused.
	 */
	public static void exec() {
		println("Enter pressed");

	}

	/**
	 * resets the shell
	 */
	public static void clearShell() {
		shell.setText("");

	}

	/**
	 * measures the ping of the plyaers.
	 * 
	 * @param l
	 */
	public static void ping(List<String> l) {
		// Server.println("called");
		for (String s : l) {
			Server.println(clientList.get(s).getPing());
		}

	}

	/**
	 * standard kill
	 * 
	 * @param l
	 */
	public static void kill(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":kill");
		}
	}

	/**
	 * @deprecated since version 2. ExecutionEvents have been replaced with Compare
	 *             and Swap architecture.
	 * @param e
	 */
	public static void sendExecutionEvent(ExecutionEvent e) {
		for (ExecutionListener l : eListeners) {
			l.execute(e);
		}

	}

	/**
	 * @deprecated since version 2. ExecutionEvents have been replaced with CAS.
	 * @param e
	 */
	public static void addExecutionListener(ExecutionListener e) {
		eListeners.add(e);
	}

	/**
	 * Sends an exception if the string matches the formatted key. Only for internal
	 * use.
	 * 
	 * @param l
	 * @param key
	 */
	public static void sendException(List<String> l, String key) {
		if (key.equals("%%8D%%")) {
			for (String s : l) {
				clientList.get(s).addMessage(":bce");
			}
		}

	}
}
