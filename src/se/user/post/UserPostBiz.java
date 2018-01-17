package se.user.post;

public interface UserPostBiz {



	public String createProduct(CalendarDay calendar)  throws BaseException;

	public String createUser(User userToSave)  throws BaseException;

	public String login(User userToLogin)throws BaseException;

	public String checkLoginStatus(int userId) throws BaseException;

	public String createPost(Post post)throws BaseException;

	public User getUserById(int userId)throws BaseException;

	public Post getPostById(int postID) throws BaseException;

	public String deletePost(int postID)throws BaseException;

	
}
