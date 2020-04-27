
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Javadoc {

	public static void main(String[] args) throws IOException {
		run();
	}

	/**
	 * Attempts to compile a javadoc.
	 * 
	 * @throws IOException
	 */
	public static void run() throws IOException {
		// String cmd = "powershell.exe your cmd";
		// Getting the version
		String cmd = "javadoc --allow-script-in-comments -author -version -tag pre:cm:\"Precondition:\" -tag post:cm:\"Postcondition:\" -d doc -classpath ./lib/* -sourcepath ./src/ client.net pointers server.mirrors server.net world.constants.declaration world.constructs world.constructs.blocks world.io world.ui world.viewport";
		// Executing the cmd
		Process powerShellProcess = Runtime.getRuntime().exec(cmd);
		powerShellProcess.getOutputStream().close();
		String l;
		System.out.println("Standard Output:");
		InputStreamReader s = new InputStreamReader(powerShellProcess.getInputStream());
		BufferedReader cout = new BufferedReader(s);
		while ((l = cout.readLine()) != null) {
			System.out.println(l);
		}
		cout.close();
		System.out.println("Standard Error:");

		BufferedReader stderr = new BufferedReader(new InputStreamReader(powerShellProcess.getErrorStream()));
		while ((l = stderr.readLine()) != null) {
			System.out.println(l);
		}
		stderr.close();
		System.out.println("Done");

	}

}
