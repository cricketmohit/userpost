package se.user.post;

import com.google.gson.Gson;

public class UserPostBizImpl implements UserPostBiz{


	@Override
	public String createProduct(CalendarDay calendar) throws BaseException {
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

}
