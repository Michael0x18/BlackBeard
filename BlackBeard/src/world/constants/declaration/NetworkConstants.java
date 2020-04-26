package world.constants.declaration;

/**
 * privides basic network commands for reference by the user
 * @author Michael Ferolito
 * @version 2
 * @since 2
 *
 */
public interface NetworkConstants {
	
	/**
	 * ping
	 */
	public static final String PING_MESSAGE = ":ping";
	/**
	 * Dont use this, its for execution Events
	 */
	public static final String EXECUTE_MESSAGE = ":exec<*>";
	/**
	 * main kill
	 */
	public static final String KILL_MESSAGE = ":kill";
	/**
	 * kill -9 (sigkill win)
	 */
	public static final String TERMINATE_MESSAGE = ":terminate";
	/**
	 * get name
	 */
	public static final String WHOIS = ":WHOIS";
	/**
	 * return ping
	 */
	public static final String PING_REPLY = "-ping";
	/**
	 * return exec
	 */
	public static final String EXECUTE_REPLY = "/exec<*>";

	

}
