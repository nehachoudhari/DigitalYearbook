package exception;

public class YearbookException extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	public YearbookException() {
		super();
	}
	
	public YearbookException(String message) {
		super(message);
	}
	
	public YearbookException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public YearbookException(Throwable cause) {
		super(cause);
	}
}
