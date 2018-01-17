package se.user.post;

import com.google.gson.Gson;

public class UserPostBizImpl implements UserPostBiz{


	@Override
	public String createProduct (CalendarDay calendar) throws BaseException {
		Gson gson = new Gson();
		Post post = new Post();
		
		return gson.toJson(post);

	}

	@Override
	public String createUser(User userToSave)  throws BaseException{
		UserPostDao dao = new UserPostDaoImpl();
		String result = dao.createUser(userToSave);
		return result;
	}

	@Override
	public String login(User userToLogin) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		String result = dao.login(userToLogin);
		return result;
	}

	@Override
	public String checkLoginStatus(int userId) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		String result = dao.checkLoginStatus(userId);
		return result;
	}
	@Override
	public User getUserById(int userId) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		User result = dao.getUserById(userId);
		return result;
	}
	@Override
	public Post getPostById(int postID) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		Post result = dao.getPostById(postID);
		return result;
	}

	@Override
	public String createPost(Post post) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		String result = dao.createPost(post);
		return result;
	}

	@Override
	public String deletePost(int postID) throws BaseException {
		UserPostDao dao = new UserPostDaoImpl();
		String result = dao.deletePost(postID);
		return result;
	}

}
