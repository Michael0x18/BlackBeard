package world.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Printer extends PrintWriter{

	public Printer(File file) throws FileNotFoundException {
		super(file);
		
	}
	
	public Printer(OutputStream s) {
		super(s);
	}
	
	public Printer (String filename) throws FileNotFoundException {
		super(new File(filename));
	}

}
