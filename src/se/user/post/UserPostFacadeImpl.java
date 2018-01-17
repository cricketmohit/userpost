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

}