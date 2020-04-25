package server.net;

public class ExecutionEvent {
	private String message;
	private String target;
	
	public ExecutionEvent(String message, String target) {
		this.message = message;
		this.target = target;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getTarget() {
		return this.target;
	}

}
