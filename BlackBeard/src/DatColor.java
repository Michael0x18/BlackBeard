import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

public class DatColor {
	
	public static void main(String[] args) {
		System.out.println(JColorChooser.showDialog(new JFrame(), "Pick Dat Color", Color.BLACK));
	}
	
}
