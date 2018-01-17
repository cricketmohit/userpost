package se.user.post;

public class BaseException extends Exception{

	public BaseException(String exceptionCode, String message){
		this.exceptionCode = exceptionCode;
		this.message = message;

	}
	public String exceptionCode;
	public String message;

	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	@Override
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
