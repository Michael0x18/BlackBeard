package server.net.daemons;

/**
 * Thrown to prevent the re-instantiation of an already running daemon.
 * 
 * @author Michael Ferolito
 * @version 2.5
 * @since Version 2.3
 *
 */
public class DaemonRunningException extends RuntimeException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2036690972934078802L;

}
