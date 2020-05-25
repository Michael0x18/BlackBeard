package server.util;
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JFrame;

/**
 * A simple color picking application stub. For use with blocks.
 * Directly calls the JColorChooser.showDialog(new JFrame(),...,...) method
 * 
 * @author Michael Ferolito
 *
 */
public class DatColor {
	
	public static void main(String[] args) {
		System.out.println(JColorChooser.showDialog(new JFrame(), "Pick Dat Color", Color.BLACK));
	}
	
}
