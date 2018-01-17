package se.user.post;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserPostDaoImpl implements UserPostDao {

	@Override
	public String createUser(User userToSave) throws BaseException {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			if (userToSave.getUserID() != 0) {
				User persistedUser = getUserById(userToSave.getUserID());
				persistedUser.setUsername(persistedUser.getUsername());
				persistedUser.setPassword(persistedUser.getPassword());
				StringBuffer sql1 = new StringBuffer(
						"UPDATE USER SET USERNAME = '");
				sql1.append(persistedUser.getUsername())
						.append("', PASSWORD = '")
						.append(persistedUser.getPassword())
						.append("' where USERID = ")
						.append(userToSave.getUserID());
				stmt.executeUpdate(sql1.toString());
			} else {
				StringBuffer sql = new StringBuffer(
						"INSERT INTO USER (USERID,USERNAME,EMAIL,PASSWORD,LASTLOGIN)"
								+ " VALUES ((SELECT MAX( USERID ) FROM USER) +1, '")
						.append(userToSave.getUsername()).append("', '")
						.append(userToSave.getEmail()).append("', '")
						.append(userToSave.getPassword()).append("', NULL) ");

				stmt.executeUpdate(sql.toString());
			}
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-01-01", e.getMessage());
		}
		return "SUCCESS";
	}

	@Override
	public String login(User userToLogin) throws BaseException {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);

			StringBuffer query = new StringBuffer(
					"SELECT * FROM USER where username = '");
			query.append(userToLogin.getUsername()).append("'");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			if (!rs.isBeforeFirst()) {
				throw new BaseException("APP-02-02",
						"User not available, please register");
			}
			while (rs.next()) {
				String password = rs.getString("PASSWORD");
				int userID = rs.getInt("USERID");
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lastLogin = null;
				try {
					if (rs.getString("LASTLOGIN") != null) {
						lastLogin = df.parse(rs.getString("LASTLOGIN"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

				Calendar date = Calendar.getInstance();
				long t = date.getTimeInMillis();
				Date afterRemovingThirtyMins = new Date(t
						- (30 * ONE_MINUTE_IN_MILLIS));

				if (password.equals(userToLogin.getPassword())) {
					if (lastLogin != null) {
						long last = lastLogin.getTime();
						lastLogin = new Date(last + (60 * ONE_MINUTE_IN_MILLIS));
						if (lastLogin.after(afterRemovingThirtyMins)) {
							throw new BaseException("APP-02-01",
									"Already Logged In");
						}
					}
					StringBuffer sql = new StringBuffer(
							"UPDATE USER SET LASTLOGIN = datetime('now')");
					sql.append(" where USERID = ").append(userID);
					stmt.executeUpdate(sql.toString());
					c.commit();
					rs.close();
					stmt.close();
					c.close();
					return "SUCCESS";
				} else {
					throw new BaseException("APP-02-01", "Incorrect Password");
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {

			throw new BaseException("APP-02-01", e.getMessage());
		}

		return "SUCCESS";
	}

	@Override
	public String checkLoginStatus(int userId) throws BaseException {
		Connection c = null;
		Statement stmt = null;
		String result = "FAILURE";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);

			StringBuffer query = new StringBuffer(
					"SELECT * FROM USER where USERID = '");
			query.append(userId).append("'");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());

			while (rs.next()) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date lastLogin = null;
				try {
					if (rs.getString("LASTLOGIN") != null) {
						lastLogin = df.parse(rs.getString("LASTLOGIN"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

				Calendar date = Calendar.getInstance();
				long t = date.getTimeInMillis();
				Date afterRemovingThirtyMins = new Date(t
						- (30 * ONE_MINUTE_IN_MILLIS));

				if (lastLogin != null) {
					long last = lastLogin.getTime();
					lastLogin = new Date(last + (60 * ONE_MINUTE_IN_MILLIS));
					if (lastLogin.after(afterRemovingThirtyMins)) {
						result = "SUCCESS";
					}
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-02-01", e.getMessage());
		}
		return result;
	}

	@Override
	public Post getPostById(int postID) throws BaseException {
		Connection c = null;
		Statement stmt = null;
		String result = "FAILURE";
		Post post = new Post();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);

			StringBuffer query = new StringBuffer(
					"SELECT * FROM POST where POSTID = '");
			query.append(postID).append("'");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			if (!rs.isBeforeFirst()) {
				throw new BaseException("APP-02-02", "Post not available");
			}
			while (rs.next()) {
				post.setUserID(rs.getInt("POSTUSERID"));
				post.setTitle(rs.getString("TITLE"));
				post.setBody(rs.getString("BODY"));
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = null;
				try {
					if (rs.getString("LASTLOGIN") != null) {
						date = df.parse(rs.getString("DATE"));
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				post.setDate(date);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-04-01", e.getMessage());
		}
		return post;
	}

	@Override
	public User getUserById(int userId) throws BaseException {
		Connection c = null;
		Statement stmt = null;
		String result = "FAILURE";
		User user = new User();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);

			StringBuffer query = new StringBuffer(
					"SELECT * FROM USER where USERID = '");
			query.append(userId).append("'");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			if (!rs.isBeforeFirst()) {
				throw new BaseException("APP-02-02",
						"User not available, please register");
			}
			while (rs.next()) {
				user.setUserID(rs.getInt("USERID"));
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setE_mail(rs.getString("EMAIL"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-04-01", e.getMessage());
		}
		return user;
	}

	@Override
	public String createPost(Post post) throws BaseException {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			if (post.getPostID() != 0) {
				Post persistedPost = getPostById(post.getPostID());
				persistedPost.setBody(post.getBody());
				persistedPost.setTitle(post.getTitle());
				StringBuffer sql1 = new StringBuffer(
						"UPDATE POST SET DATE = datetime('now'), BODY = '");
				sql1.append(persistedPost.getBody()).append("', TITLE = '")
						.append(persistedPost.getTitle())
						.append("' where POSTID = ").append(post.getPostID());
				stmt.executeUpdate(sql1.toString());
			} else {
				StringBuffer sql = new StringBuffer(
						"INSERT INTO POST (POSTID,POSTUSERID,TITLE,BODY,DATE)"
								+ " VALUES ((SELECT MAX( POSTID ) FROM POST) +1, '")
						.append(post.getUserID()).append("', '")
						.append(post.getTitle()).append("', '")
						.append(post.getBody()).append("', datetime('now')) ");

				stmt.executeUpdate(sql.toString());
			}
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-03-01", e.getMessage());
		}
		return "SUCCESS";
	}

	@Override
	public String deletePost(int postID) throws BaseException {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			StringBuffer sql = new StringBuffer(
					"Delete from POST where POSTID = '").append(postID).append(
					"'");

			stmt.executeUpdate(sql.toString());
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			throw new BaseException("APP-01-01", e.getMessage());
		}
		return "SUCCESS";
	}
}
