package client.net;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * Class ClientConnector extens JFrame implements KeyListener, ActionListener
 * 
 * @author mferolito676
 * @version 2.0 window title is "Connection Utility" provides a stripped-down
 *          GUI for connections by explicit IP. The window is a widget based GUI
 *          made using swing. It contains extra functionality for plugins later
 *          to the project launcher.
 */
public class ClientConnector extends JFrame implements KeyListener, ActionListener {
	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea input;
	private JLabel Title;
	private JButton connectButton;
	public static final int port = 59090;
	public static Image bgGif = new ImageIcon("Client/Resources/BG/2.gif").getImage();
	/**
	 * The default port for incoming connections.
	 */
	public static final int defaultport = 4444;
	private JMenuBar jmb = new JMenuBar();
	Timer t = new Timer(10,this);
	int i = 0;
	// public static int port = 4444;

	/**
	 * Used only when starting a client program
	 */
	public ClientConnector() {
		super();
		// PlayList.launch();
		this.setTitle("BlackBeard");
		JPanel j = (new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6248704706106532221L;

			public void paintComponent(Graphics g) {
				this.setBackground(Color.BLACK);
				g.setColor(new Color(0,0,0,200));
				g.drawImage(bgGif,0,0,800,600,null);
				g.fillRect(0, 0, 800, 600);
//				if(i < 120);
//				Title.setBackground(new Color(i++));
			}
			
		});
		this.setContentPane(j);
		//this.getContentPane().add(j);
		j.setBackground(Color.BLACK);
		this.setLayout(null);
		j.setBounds(0,0,800,600);
		this.setBounds(10, 10, 800, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		input = new JTextArea();
		input.setBounds(205, 400, 270, 30);
		Title = new JLabel("BlackBeard");
		this.setAlwaysOnTop(true);
		this.setAutoRequestFocus(true);
		Title.setFont(new Font("Monospaced", 1, 100));
		Title.setForeground(new Color(40,60,60));
		Title.setBounds(120, 0, 800, 400);
		this.add(input);
		this.add(Title);
		input.setBackground(Color.LIGHT_GRAY);
		input.setToolTipText("Enter the IP address of a running BlackBeard server");
		input.setForeground(Color.decode("#400040"));
		connectButton = new JButton("Connect");
		this.add(connectButton);
		connectButton.setBounds(485, 400, 100, 30);
		connectButton.addActionListener(this); // CANCER
		t.start();
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
		this.setJMenuBar(jmb);		
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
				this.connect(input.getText(), port);
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
			this.connect(codes[0], port);
		} else {
			this.connect("", port);
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
			this.dispose();
			Loader l = new Loader();
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
//			synchronized (this) {
//				// Client.addExecutionListener(gs);
////				PlayerProcessor.launch();
//			}
			l.dispose();
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
		if(e.getSource().equals(t)) {
//			if(i < 120)
//			Title.setBackground(new Color(90,90,90));
			this.getContentPane().repaint();
		}

	}

}
