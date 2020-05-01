import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Glider2 extends JPanel{

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setBounds(0,0,10,10);
		f.setUndecorated(true);
		f.setOpacity(0);
		f.getContentPane().setBackground(new Color(0,0,0,0));
		Glider2 g = new Glider2();
		f.add(g);
		f.setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		sun.misc.Unsafe.getUnsafe();
	}
}
