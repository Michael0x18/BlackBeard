package world.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Just a bufferedReader, but with better to construction.
 * @author Michael Ferolito
 *
 */
public class FileWrapper extends BufferedReader{
	
	/**
	 * see super constructor
	 * @param w
	 */
	public FileWrapper(Reader w) {
		super(w);
	}
	
	/**
	 * creates a new FileReader and a new this.
	 * @param s
	 * @throws FileNotFoundException
	 */
	public FileWrapper(String s) throws FileNotFoundException {
		super(new FileReader(new File(s)));
	}

}
