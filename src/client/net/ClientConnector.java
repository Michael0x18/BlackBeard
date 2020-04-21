package client.net;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

/**
 * Class ClientConnector extens JFrame implements KeyListener, ActionListener
 * 
 * @author mferolito676
 * @version 1 window title is "Connection Utility" provides a stripped-down gui
 *          for connections by explicit IP.
 */
public class ClientConnector extends JFrame implements KeyListener, ActionListener {
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea input;
	private JLabel Title;
	private JButton connectButton;
	public static final int defaultport = 59090;
	private JMenuBar jmb = new JMenuBar();
	// public static int port = 4444;

	/**
	 * Used only when starting a client program
	 */
	public ClientConnector() {
		super();
		//PlayList.launch();
		this.setTitle("GENESIS::Launcher");
		this.getContentPane().setBackground(Color.BLACK);
		this.setLayout(null);
		this.setBounds(10, 10, 800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		input = new JTextArea();
		input.setBounds(205, 400, 270, 30);
		Title = new JLabel("GENESIS");
		this.setAlwaysOnTop(true);
		this.setAutoRequestFocus(true);
		Title.setFont(new Font("Monospaced", 1, 100));
		Title.setForeground(Color.DARK_GRAY);
		Title.setBounds(180, 0, 800, 400);
		this.add(input);
		this.add(Title);
		input.setBackground(Color.LIGHT_GRAY);
		input.setToolTipText("Enter the IP address of a GENESIS server");
		input.setForeground(Color.decode("#400040"));
		connectButton = new JButton("Connect");
		this.add(connectButton);
		connectButton.setBounds(485, 400, 100, 30);
		connectButton.addActionListener(this); // CANCER
//		JMenu File = new JMenu("File");
//		JMenu NoServer = new JMenu("No Server?");
//		JMenu Help = new JMenu("Help!");
//		JMenuItem youcan = new JMenuItem("You can...");
//		JMenuItem leviathan = new JMenuItem("Run my last year's java project");
//		JMenuItem egress = new JMenuItem("See the Egress");
//		JMenuItem reeeeeeeeadme = new JMenuItem("See the REEEEEEEEEADME");
//		JMenu getHelp = new JMenu("Get help");
//		JMenuItem about = new JMenuItem("See da about file");
//		JMenuItem docs = new JMenuItem("Read the Oracle api");
//		JMenu apps = new JMenu("Apps");
//		JMenuItem xl = new JMenuItem("XLogo");
//		apps.add(xl);
//		xl.addActionListener(e -> {
//			XLogo x = new XLogo();
//		});
//		about.addActionListener(e -> {
//			
//		});
//		
//		docs.addActionListener(e -> {
//			ProcessHelper p = new ProcessHelper();
//			try {
//				//String[] a = {"Readme.txt"};
//				p.startNewJavaProcess("", "other.JBrowser", new String[0]);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//		
//		reeeeeeeeadme.addActionListener(e -> {
//			ProcessHelper p = new ProcessHelper();
//			try {
//				String[] a = {"Readme.txt"};
//				p.startNewJavaProcess("", "other.JEditor", a);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		});
//
//		egress.addActionListener(e -> {
//			Runtime.getRuntime().halt(0);
//		});
//		leviathan.addActionListener(e -> {
//			ProcessHelper p = new ProcessHelper();
//			try {
//				p.startNewJavaProcess("", "leviathan.LEVIATHAN", new String[0]);
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		});
//
//		
//		Help.add(reeeeeeeeadme);
//		Help.add(getHelp);
//		getHelp.add(about);
//		getHelp.add(docs);
//		jmb.add(File);
//		jmb.add(Help);
//		jmb.add(NoServer);
//		jmb.add(apps);
//		NoServer.add(youcan);
//		NoServer.add(leviathan);
//		NoServer.add(egress);
//		this.setJMenuBar(jmb);		
		this.setVisible(true);
		input.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	/**
	 * Adds listener for enter key
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == (KeyEvent.VK_ENTER)) {
			String[] codes = input.getText().split(":");
			if (codes.length > 1) {
				this.connect(codes[0], Integer.parseInt(codes[1]));
			} else {
				this.connect("", 4444);
			}
			this.setVisible(false);
		}

	}

	/**
	 * code to connect to server
	 */
	public void go() {
		String[] codes = input.getText().split(":");
		if (codes.length == 2) {
			this.connect(codes[0], Integer.parseInt(codes[2]));
		} else if (codes.length == 1) {
			this.connect(codes[0], 4444);
		} else {
			this.connect("", 444);
		}
	}

	/**
	 * connect to server with following characteristics:
	 * 
	 * @param ip   - the IP address of the server
	 * @param port - the TCP port to be used for the connection
	 * @return true - if the connection was successful.
	 */
	public boolean connect(String ip, int port) {

		try {
//			l = new Load();

			Socket socket = new Socket(ip, port);
			socket.setKeepAlive(true);
			// Scanner sc = new Scanner(socket.getInputStream());
			// Thread.sleep(100);
			// System.out.println(sc.nextLine());

			// Scanner in = new Scanner(socket.getInputStream());
			ServerListener s = new ServerListener(socket);
			Client.setServerListener(s);
			// String[] info = {ip};
			// Olmec.main(info);
//			GridSetUp gs = new GridSetUp();
//			GridSetUp.gs = gs;
			synchronized (this) {
				// Client.addExecutionListener(gs);
//				PlayerProcessor.launch();
			}
			// l.dispose();
			return true;

		} catch (Exception e) {
			if (Client.VerboseMode)
				e.printStackTrace();
			return false;
		}

	}

	@Override
	/**
	 * overrides key released in KeyListener
	 */
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * overrides key typed in KeyListener
	 */
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * ensures !null
	 */
	public void check() {
		return;

	}

	@Override
	/**
	 * Listener for the button
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.connectButton)) {
			String[] codes = input.getText().split(":");
			if (codes.length > 1) {
				this.connect(codes[0], Integer.parseInt(codes[1]));
			} else {
				this.connect("", 4444);
			}
			this.setVisible(false);
		}

	}

}
