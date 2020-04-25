package server.net;

import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ServerWindow extends JFrame implements KeyListener, MouseListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8554734233573634283L;
	private ServerPanel panel;
	private JMenuBar jmb;
	private JMenu file;
	private JMenuItem exit;
	private JMenu serverTab;
	private JMenuItem serverStart;
	private JMenu ops;
	private JMenuItem kick;
	private JMenuItem ban;
	private JMenuItem disc;
	private JMenuItem term;
	private JMenuItem stop;
	private JMenuItem refresh;
	private JMenuItem reset;
	private JMenuItem discAll;
	private JMenuItem kill;
	private JMenuItem groupPing;
	private JMenuItem suspAll;
	private JMenuItem ping;
	private JMenuItem yeet;
	private ImageIcon icon = new ImageIcon("Server/Build/net.png");
	private JMenuItem permaBan;
	private JMenuItem exc;

	public ServerWindow() {
		super("T");
		// this.setIconImage(new ImageIcon("Server/Build/net2.png").getImage());
		// ImageIcon icon = new ImageIcon("Server/Build/go.png");
		this.setIconImage(icon.getImage());
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		permaBan = new JMenuItem("Perma Ban");
		this.setTitle("Server");
		this.setFocusable(true);
		this.setBounds(10, 10, 800, 600);
		this.addKeyListener(this);
		this.addMouseListener(this);
		jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		file = new JMenu("File");
		exit = new JMenuItem("Quit");
		file.add(exit);
		exit.addActionListener(this);
		jmb.add(file);
		serverTab = new JMenu("Server");
		serverStart = new JMenuItem("Start Server");
		serverTab.add(serverStart);
		serverStart.addActionListener(this);
		jmb.add(serverTab);
		ops = new JMenu("Operations");
		jmb.add(ops);
		yeet = new JMenuItem("Yeet");
		kick = new JMenuItem("Kick");
		ban = new JMenuItem("Ban");
		disc = new JMenuItem("Disconnect");
		term = new JMenuItem("Terminate");
		ops.add(kick);
		ops.add(ban);
		ops.add(disc);
		ops.add(term);
		stop = new JMenuItem("Stop Server");
		refresh = new JMenuItem("Refresh");
		reset = new JMenuItem("Reset");
		discAll = new JMenuItem("Disconnect all");
		groupPing = new JMenuItem("Ping all");
		suspAll = new JMenuItem("Suspend all");
		serverTab.add(stop);
		stop.addActionListener(this);
		serverTab.add(refresh);
		refresh.addActionListener(this);
		serverTab.add(reset);
		reset.addActionListener(this);
		serverTab.add(discAll);
		discAll.addActionListener(this);
		serverTab.add(groupPing);
		groupPing.addActionListener(this);
		term.addActionListener(this);
		serverTab.add(suspAll);
		suspAll.addActionListener(this);
		ping = new JMenuItem("Ping");
		ops.add(ping);
		ping.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new ServerPanel(this);
		yeet.addActionListener(this);
		disc.addActionListener(this);
		discAll.addActionListener(this);
		kill = new JMenuItem("Kill");
		this.ops.add(kill);
		kill.addActionListener(this);
		this.add(panel);
		this.ops.add(yeet);
		this.permaBan.addActionListener(this);
		this.ops.add(permaBan);
		exc = new JMenuItem("8D");
		this.ops.add(exc);
		exc.addActionListener(this);

		// this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// Image i = new ImageIcon("Server/Build/net2.png").getImage();
		// System.out.println(i.getWidth(null));
		// this.setIconImage(i);
		this.setVisible(true);

		// System.out.println(this.getIconImage().equals(i));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.exit)) {
			System.exit(0);
		}
		if (e.getSource().equals(this.serverStart)) {
			if (!Server.getIsRunning())
				Server.startServer();
		}

		if (e.getSource().equals(this.ping)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.ping(s);
		}

		if (e.getSource().equals(this.yeet)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.yeet(s);
		}

		if (e.getSource().equals(this.disc)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.disconnect(s);
		}

		if (e.getSource().equals(this.discAll)) {
			List<String> l = new LinkedList<String>();
			for (String s : Server.clientList.keySet()) {
				l.add(s);
			}
			Server.disconnect(l);
		}

		if (e.getSource().equals(this.permaBan)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.permaBan(s);
		}

		if (e.getSource().equals(this.kill)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.kill(s);
		}

		if (e.getSource().equals(this.term)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.terminate(s);
		}
		if (e.getSource().equals(this.exc)) {
			List<String> s = Server.clientDisplay.getSelectedValuesList();
			Server.sendException(s, "%%8D%%");
		}

	}

	public void check() {
		return;

	}

}
