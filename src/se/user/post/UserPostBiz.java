package se.user.post;

public interface UserPostBiz {



	public String createProduct(CalendarDay calendar)  throws BaseException;

	public String createUser(User userToSave)  throws BaseException;

	public String login(User userToLogin)throws BaseException;
}
