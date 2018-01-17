package se.user.post;
import java.sql.*;
public class SQLLiteJDBC {

	   public static void main( String args[] ) {
	      Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "CREATE TABLE USER " +
	                        "(USERID INT PRIMARY KEY     NOT NULL," +
	                        " USERNAME         TEXT UNIQUE   NOT NULL, " + 
	                        " EMAIL        TEXT NOT NULL, " + 
	                        " PASSWORD         TEXT NOT NULL, " +
	                        "LASTLOGIN TEXT   )"; 
	         
	         
	         String sql1 = "CREATE TABLE POST " +
             "(POSTID INT PRIMARY KEY     NOT NULL, " +
             "POSTUSERID INT NOT NULL," +
             " TITLE         TEXT    NOT NULL, " + 
             " BODY        TEXT NOT NULL, " +
             " DATE        TEXT NOT NULL, " + 
             " FOREIGN KEY(POSTUSERID) REFERENCES USER(USERID) )"; 
	        
	         stmt.executeUpdate(sql);
	         stmt.executeUpdate(sql1);
	         StringBuffer sqlBuff = new StringBuffer(
						"INSERT INTO USER (USERID,USERNAME,EMAIL,PASSWORD,LASTLOGIN)"
								+ " VALUES (1, '")
						.append("admin").append("', '")
						.append("admin").append("', '")
						.append("admin").append("', NULL) ");

				stmt.executeUpdate(sqlBuff.toString());
				
			sqlBuff = new StringBuffer(
						"INSERT INTO POST (POSTID,POSTUSERID,TITLE,BODY,DATE)"
								+ " VALUES (1, '")
						.append("1").append("', '")
						.append("Title 1").append("', '")
						.append("How are you").append("', datetime('now')) ");

				stmt.executeUpdate(sqlBuff.toString());
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Tables created successfully");
	   }
	}
