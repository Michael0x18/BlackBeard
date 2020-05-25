package server.util;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Runs top
 * 
 * @author Michael Ferolito
 *
 */
public class top {

	/**
	 * Runs top and chain pipes it to System.out.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void runTop(String[] args) throws IOException {
		System.setOut(new PrintStream(Runtime.getRuntime().exec("top -u").getOutputStream()));
		while (true) {

		}
	}
}
