package world.viewport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Builder {

	String filename;
	public Builder(String filename) {
		this.filename = filename;
	}
	public void write(ArrayList p) {
		File dataFile = new File(filename);
		try
		{  
			// append characters to the file
			FileWriter writer = new FileWriter(filename, true);
			for(int i = 0; i<p.size(); i++) {
				writer.write(((String) p.get(i)) + "\n");
			}

			writer.close();
		}
		catch ( IOException iox )
		{
			System.out.println("Problem writing " + filename );
		}
	}
	public void write(String s) {
		File dataFile = new File(filename);
		try
		{  
			// append characters to the file
			FileWriter writer = new FileWriter(filename, true);
			writer.write(s + "\n");
			writer.close();
			Grid.load("TBD");
		}
		catch ( IOException iox )
		{
			System.out.println("Problem writing " + filename );
		}
	}
}