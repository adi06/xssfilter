package asu.cs541.ss.xssfilter.exception;

public class InvalidParameterException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int errorCode;

	public InvalidParameterException(String message, int errorCode) {
		super(message);
		this.setErrorCode(errorCode);
	}
	
	public InvalidParameterException(String message, int errCode, Throwable th) {
		super(message,th);
		this.setErrorCode(errCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
