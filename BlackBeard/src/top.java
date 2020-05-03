import java.io.IOException;
import java.io.PrintStream;

public class top {

	
	public static void main(String[] args) throws IOException {
		System.setOut((PrintStream)Runtime.getRuntime().exec("top").getOutputStream());
	}
}
