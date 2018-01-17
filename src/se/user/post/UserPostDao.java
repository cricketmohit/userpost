package se.user.post;


public interface UserPostDao{

	public String createUser(User userToSave)  throws BaseException;

	public String login(User userToLogin) throws BaseException;
	
}
