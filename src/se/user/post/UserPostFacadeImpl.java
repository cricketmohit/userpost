package se.user.post;


public class UserPostFacadeImpl implements UserPostFacade {

	

	@Override
	public String searchRace(String date) throws BaseException {
		//		Get Calendar Data
		CalendarDayProxy calProxy = new CalendarDayProxy();
		CalendarDay calendar = calProxy.getCalendar(date);
		//		//		Get Landing Page
		
		UserPostBiz biz = new UserPostBizImpl();



		return biz.createProduct(calendar);
	}

	@Override
	public String createUser(User userToSave) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		String result = biz.createUser(userToSave);
		return result;
	}

	@Override
	public String login(User userToLogin) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		String result = biz.login(userToLogin);
		return result;
	}

	@Override
	public String checkLoginStatus(int userId) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		String result = biz.checkLoginStatus(userId);
		return result;
	}
	@Override
	public User getUserById(int userId) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		User result = biz.getUserById(userId);
		return result;
	}

	@Override
	public String createPost(Post post) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		String result = biz.createPost(post);
		return result;
	}

	@Override
	public Post getPostById(int postID) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		Post result = biz.getPostById(postID);
		return result;
	}

	@Override
	public String deletePost(int postID) throws BaseException {
		UserPostBiz biz = new UserPostBizImpl();
		String result = biz.deletePost(postID);
		return result;
	}

}
