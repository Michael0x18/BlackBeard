package client.net;

/**
 * 
 * @author mferolito676
 * @version 1
 * 
 * For events
 */
public interface ExecutionListener {
	
	/**
	 * Only method, interface for Server comms
	 * @param e
	 */
	public void execute(ExecutionEvent e);

}
