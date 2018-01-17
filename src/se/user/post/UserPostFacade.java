package se.user.post;

public interface UserPostFacade {

	public String searchRace(String date) throws BaseException;

	public String createUser(User userToSave) throws BaseException;

	public String login(User userToLogin) throws BaseException;
	
	public String checkLoginStatus(int userId) throws BaseException;

	public String createPost(Post post)throws BaseException;

	public User getUserById(int userID) throws BaseException;

	public Post getPostById(int ID)throws BaseException;

	public String deletePost(int postID)throws BaseException;

	public Post searchPost(Post post)throws BaseException;

	
}
