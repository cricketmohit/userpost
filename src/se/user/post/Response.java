package se.user.post;

public class Response {
	private String result;
	private ErrorResultBaseType error;

	public ErrorResultBaseType getError() {
		return error;
	}

	public void setError(ErrorResultBaseType error) {
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
