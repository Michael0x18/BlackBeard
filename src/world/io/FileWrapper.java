package world.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class FileWrapper extends BufferedReader{
	
	public FileWrapper(Reader w) {
		super(w);
	}
	
	public FileWrapper(String s) throws FileNotFoundException {
		super(new FileReader(new File(s)));
	}

}
