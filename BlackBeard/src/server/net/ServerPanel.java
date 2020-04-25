package server.net;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ServerPanel extends JPanel implements KeyListener{
	/**
	 * UID
	 */
	private static final long serialVersionUID = -4635591810573070538L;
	private ServerWindow parent;
	private JLabel consoleLabel = new JLabel("Console");
	private JScrollPane sc;
	private JScrollPane listScroll;
	
	public static ServerPanel s;
	
	public ServerPanel(ServerWindow parent) {
		super();
		s = this;
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		this.parent = parent;
		//ServerPaneServer.console.setBounds(0,10,800,200);
		sc = new JScrollPane(Server.console);
		sc.setAutoscrolls(true);
		Server.console.setAutoscrolls(true);
		Server.console.setWrapStyleWord(true);
		Server.console.setLineWrap(true);
		Server.console.setEditable(false);
		Server.console.setAutoscrolls(true);
		Server.shell.setLineWrap(true);
		Server.shell.setBounds(0,0,this.getWidth(),200);
		this.add(Server.shell);
		Server.shell.addKeyListener(this);
		//this.add(Server.console);
		this.add(sc);
		this.add(consoleLabel);
		Server.clientDisplay.setBounds(10, 10, this.getWidth()-20, this.getHeight()-300);
		listScroll = new JScrollPane(Server.clientDisplay);
		this.add(listScroll);
		//this.add(Server.clientDisplay);
		
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		//Server.clientDisplay = new JList<Object>(Server.clientList.keySet().toArray());
		
		//Server.clientDisplay.setBounds(10, 10, this.getWidth()-20, this.getHeight()-300);
		g.fillRect(0, 0, parent.getWidth(), parent.getHeight());
		//Server.console.setBounds(10,parent.getHeight()-320,parent.getWidth()-40,200);
		sc.setBounds(10,parent.getHeight()-320,parent.getWidth()-40,200);
		Server.shell.setBounds(10,sc.getY()+200,this.getWidth()-20,30);
		this.consoleLabel.setBounds(10,sc.getY()-60,100,100);
		this.consoleLabel.setForeground(Color.GREEN);
		Server.shell.setForeground(Color.GREEN);
		Server.shell.setBackground(Color.DARK_GRAY);
		this.listScroll.setBounds(10, 10, this.getWidth()-20, this.getHeight()-300);
		//this.setBackground(Color.black);
		//Server.println("It is time to yeet the meat");
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		if(Server.shell.getText().length() < 3) {
//			Server.shell.setText(">>>>");
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_ENTER) {
			Server.exec();
			Server.clearShell();
		}
		
	}

}
