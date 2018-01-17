package se.user.post;

public interface UserPostFacade {

	public String searchRace(String date) throws BaseException;

	public String createUser(User userToSave) throws BaseException;

	public String login(User userToLogin) throws BaseException;; 
}
