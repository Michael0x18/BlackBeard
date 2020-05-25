package server.net;

/**
 * 
 * @author Michael Ferolito
 * @version 1
 * @since Version 1
 * 
 * @deprecated Since version 2. Originally meant for internal use, it is not
 *             thread safe. It is kept only for backwards compatibility with
 *             earlier versions.
 * 
 */
public class ExecutionEvent {
	private String message;
	private String target;

	/**
	 * Sends a message to the target.
	 * 
	 * @deprecated since Version 2
	 * @param message
	 * @param target
	 */
	public ExecutionEvent(String message, String target) {
		this.message = message;
		this.target = target;
	}

	/**
	 * Returns the message.
	 * 
	 * @return
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Returns the target.
	 * 
	 * @return
	 */
	public String getTarget() {
		return this.target;
	}

}
