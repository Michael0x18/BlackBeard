package client.net;

/**
 * 
 * @author mferolito676
 * @version 1
 * Class for sending events to Server following a specific format.
 */
public class ExecutionEvent {
	private String message;
	private String target;
	
	/**
	 * 
	 * @param message - msg to send
	 * @param target -null in this case
	 */
	public ExecutionEvent(String message, String target) {
		this.message = message;
		this.target = target;
	}
	
	/**
	 * 
	 * @return - message contained within Event.
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * 
	 * @return target, null for server
	 */
	public String getTarget() {
		return this.target;
	}

}
