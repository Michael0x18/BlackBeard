package world.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * A fancy way to print to a file.
 * 
 * @author Michael Ferolito
 * @version 1
 */
public class Printer extends PrintWriter {

	/**
	 * Creates a printer from a file
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	public Printer(File file) throws FileNotFoundException {
		super(file);

	}

	/**
	 * Creates a Printer from an OutputStream
	 * 
	 * @param s
	 */
	public Printer(OutputStream s) {
		super(s);
	}

	/**
	 * Creates a Printer from a filename
	 * 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public Printer(String filename) throws FileNotFoundException {
		super(new File(filename));
	}

}
