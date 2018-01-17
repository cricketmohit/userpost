package se.user.post;

public class Response {
	private User user;
	private Post post;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
