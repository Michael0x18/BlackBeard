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


public class Server {
	public static final boolean verbose = true;
	public static final int port = 4444;
	public static final int defaultPort = 4444;
	private static ServerWindow s;
	public static final JTextArea console = new JTextArea();
	private static ServerConnector connector;
	private static boolean isRunning = false;
	public static DefaultListModel<String> clientDisplayModel = new DefaultListModel<>();
	public static JList<String> clientDisplay = new JList<String>(clientDisplayModel);
	public static final ConcurrentHashMap<String, ClientHandler> clientList = new ConcurrentHashMap<String, ClientHandler>();
	public static final JTextArea shell = new JTextArea();
	static File banned;
	public static final CopyOnWriteArrayList<ExecutionListener> eListeners = new CopyOnWriteArrayList<ExecutionListener>();
	public static final ConcurrentHashMap<String, String> playerCoords = new ConcurrentHashMap<String, String>();
	//public static final CopyOnWriteArrayList<SnowBall> shotsList = new CopyOnWriteArrayList<SnowBall>(); TODO move to mirrors
	//public static final CopyOnWriteArrayList<Block> hitBlockList = new CopyOnWriteArrayList<Block>();
	public static final CopyOnWriteArrayList<String> bannedIPs = new CopyOnWriteArrayList<String>();

	public static BufferedWriter bannedWriter;
	//public static OtherPlayer[] players;

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
		ExecutionHandler.launch();
		ServerDaemon sd = new ServerDaemon();
		sd.setDaemon(true);
		sd.start();
		//MapCreator.createMap();  TODO- generate dynamic map
		//PlayerProcessor.launch();
	}

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

	public static void ban(List<String> l) {
		for (String s : l) {
			bannedIPs.add(s);
		}
		kill(l);
	}

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

	public static void println(String s) {
		console.setText((console.getText() + s + "\n"));
	}

	public static void print(String s) {
		console.setText((console.getText() + s));
	}

	public static boolean getIsRunning() {
		return isRunning;
	}

	public static void disconnect(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":disc");
		}
	}

	public static void terminate(List<String> l) {
		for (String s : l) {
			clientList.get(s).halt();
		}
	}

	public static void yeet(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":echo It is Time: To Yeet the Meat!!!!!");
		}
	}

	public static void exec() {
		println("Enter pressed");

	}

	public static void clearShell() {
		shell.setText("");

	}

	public static void ping(List<String> l) {
		// Server.println("called");
		for (String s : l) {
			Server.println(clientList.get(s).getPing());
		}

	}

	public static void kill(List<String> l) {
		for (String s : l) {
			clientList.get(s).addMessage(":kill");
		}
	}

	public static void sendExecutionEvent(ExecutionEvent e) {
		for (ExecutionListener l : eListeners) {
			l.execute(e);
		}

	}

	public static void addExecutionListener(ExecutionListener e) {
		eListeners.add(e);
	}

	public static void sendException(List<String> l, String key) {
		if (key.equals("%%8D%%")) {
			for (String s : l) {
				clientList.get(s).addMessage(":bce");
			}
		}

	}
}
