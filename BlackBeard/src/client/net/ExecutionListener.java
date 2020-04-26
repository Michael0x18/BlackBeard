package client.net;

/**
 * 
 * @author mferolito676
 * @version 1
 * @deprecated as of version 2
 * For events. Was used in project pre-alpha internally. Kept in the project for backwards compatibility.
 */
public interface ExecutionListener {
	
	/**
	 * Only method, interface for Server comms
	 * @param e
	 */
	public void execute(ExecutionEvent e);

}
