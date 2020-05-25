package server.net;

//import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Creates a new JVM with a specified main class and arguments
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since 2
 *
 */
public class ProcessHelper {
	// private static Logger log = Logger.getLogger(ProcessHelper.class);
	private List<Process> processes;

	/**
	 * Creates a new tool.
	 */
	public ProcessHelper() {
		processes = new ArrayList<Process>();

	}

	/**
	 * Attempts to start a new Java Process (in a new JVM) by locating the java
	 * cmdlet and running it on the specified class. The process is kept for logging
	 * purposes.
	 * 
	 * @param optionsAsString
	 * @param mainClass
	 * @param arguments
	 * @return
	 * @throws IOException
	 */
	public Process startNewJavaProcess(final String optionsAsString, final String mainClass, final String[] arguments)
			throws IOException {

		ProcessBuilder processBuilder = createProcess(optionsAsString, mainClass, arguments);
		Process process = processBuilder.start();
		processes.add(process);
		// log.debug("Process " + process.toString() + " has started");
		return process;
	}

	/**
	 * Creates a new class. startNewJavaProcess is its wrapper.
	 * 
	 * @param optionsAsString
	 * @param mainClass
	 * @param arguments
	 * @return
	 */
	private ProcessBuilder createProcess(final String optionsAsString, final String mainClass,
			final String[] arguments) {
		String jvm = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		String classpath = System.getProperty("java.class.path");
		// log.debug("classpath: " + classpath);
		// String workingDirectory = System.getProperty("user.dir");

		String[] options = optionsAsString.split(" ");
		List<String> command = new ArrayList<String>();
		command.add(jvm);
		command.addAll(Arrays.asList(options));
		command.add(mainClass);
		command.addAll(Arrays.asList(arguments));

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		Map<String, String> environment = processBuilder.environment();
		environment.put("CLASSPATH", classpath);
		return processBuilder;
	}

	/**
	 * Calls the inherent destroy method of the specified process
	 * 
	 * @param process - the Process to destroy.
	 */
	public void killProcess(final Process process) {
		process.destroy();
	}

	/**
	 * Kill all processes.
	 */
	public void shutdown() {
		// log.debug("Killing " + processes.size() + " processes.");
		for (Process process : processes) {
			killProcess(process);
		}
	}
}