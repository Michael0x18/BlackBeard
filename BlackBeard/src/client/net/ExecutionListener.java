package client.net;

/**
 * For events. Was used in project pre-alpha internally. Kept in the project for
 * backwards compatibility.
 * 
 * @author Michael Ferolito
 * @version 1
 * @deprecated as of version 2
 * 
 */
public interface ExecutionListener {

	/**
	 * Only method, interface for Server comms
	 * 
	 * @param e
	 */
	public void execute(ExecutionEvent e);

}
