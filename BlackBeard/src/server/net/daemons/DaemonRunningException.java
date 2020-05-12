package server.net.daemons;

/**
 * 
 * @author Michael Ferolito
 * Thrown to prevent the re-instantiation of an already running daemon.
 *
 */
public class DaemonRunningException extends RuntimeException {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 2036690972934078802L;
	
}
