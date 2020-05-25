package world.viewport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author William Meng
 * @version 1
 * @since 2.4
 */
public class Builder {

	/**
	 * The filename to use for export
	 */
	String filename;

	/**
	 * Creates a new Builder instance with the given filename
	 * 
	 * @param filename
	 */
	public Builder(String filename) {
		this.filename = filename;
	}

	/**
	 * Writes the data contained in this Builder object to a file.
	 * 
	 * @param p the ArrayList to write from. Must contain strings.
	 */
	public void write(ArrayList p) {
		File dataFile = new File(filename);
		try {
			// append characters to the file
			FileWriter writer = new FileWriter(filename, true);
			for (int i = 0; i < p.size(); i++) {
				writer.write(((String) p.get(i)) + "\n");
			}

			writer.close();
		} catch (IOException iox) {
			System.out.println("Problem writing " + filename);
		}
	}

	/**
	 * Writes a single string to the file.
	 * 
	 * @param s - the string.
	 */
	public void write(String s) {
		File dataFile = new File(filename);
		try {
			// append characters to the file
			FileWriter writer = new FileWriter(filename, true);
			writer.write(s + "\n");
			writer.close();
			Grid.load("TBD");
		} catch (IOException iox) {
			System.out.println("Problem writing " + filename);
		}
	}
}