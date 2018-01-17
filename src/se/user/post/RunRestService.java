package se.user.post;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;
// Rest API
@Path("userPost")
public class RunRestService {

	public static void main(String[] args) {
		final String baseUri = "http://localhost:8001/";
		final Map<String, String> initParams = new HashMap<String, String>();

		// Register the package that contains your javax.ws.rs-annotated beans
		// here
		initParams.put("com.sun.jersey.config.property.packages",
				"se.user.post");

		System.out.println("Starting grizzly...");
		try {
			SelectorThread threadSelector = GrizzlyWebContainerFactory.create(
					baseUri, initParams);
			System.out.println(String.format("Jersey started with WADL "
					+ "available at %sapplication.wadl.", baseUri));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("getUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(String str) {

		Response response = new Response();
		
		Gson gson = new Gson();
		User userToGet = gson.fromJson(str, User.class);
		User user = null;
		try {
			// check for user login
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String status = userFacade.checkLoginStatus(userToGet.getUserID());
			// Calling getUser through Facade layer
			if(status.equalsIgnoreCase("SUCCESS")){
				user = userFacade.getUserById(userToGet.getUserID());	
			}else{
				throw new BaseException("APP-01-02", "Please Login first, for first time user please register");
			}
			
			response.setResult("Success");
			response.setUser(user);
		} catch (BaseException serviceException) {
			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
		}
		return gson.toJson(response);
	}
	
	@POST
	@Path("getPostById")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostById(String str) {

		Response response = new Response();
		Gson gson = new Gson();
		Post postById = gson.fromJson(str, Post.class);
		Post post  = null;
		try {
			// check for user login
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String status = userFacade.checkLoginStatus(postById.getUserID());
			// Calling getUser through Facade layer
			if(status.equalsIgnoreCase("SUCCESS")){
			post = userFacade.getPostById(post.getPostID());	
			}else{
				throw new BaseException("APP-01-02", "Please Login first, for first time user please register");
			}
			response.setResult("Success");
			response.setPost(post);
		} catch (BaseException serviceException) {
			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
		}
		return gson.toJson(response);
	}
	
	@POST
	@Path("createPost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createPost(String str) {
		Response response = new Response();
		Gson gson = new Gson();
		Post post = gson.fromJson(str, Post.class);
		post.setBody(post.getBody().substring(0, 150));
		try {
			// check for user login
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String status = userFacade.checkLoginStatus(post.getUserID());
			// Calling create user through Facade layer
			String result = "FAILURE";
			if(status.equalsIgnoreCase("SUCCESS")){  
			result = userFacade.createPost(post);
			}else{
				throw new BaseException("APP-01-02", "Please Login first, for first time user please register");
			}

			response.setResult(result);

		} catch (BaseException serviceException) {

			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
			response.setResult("FAILURE");
		}

		return gson.toJson(response);
	}
	
	@POST
	@Path("searchPost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String searchPost(String str) {
		Response response = new Response();
		Gson gson = new Gson();
		Post postStr = gson.fromJson(str, Post.class);
		postStr.setBody(postStr.getBody().substring(0, 150));
		try {
			Post post = null;
			UserPostFacade userFacade = new UserPostFacadeImpl();
			post = userFacade.searchPost(post);
			response.setPost(post);
			response.setResult("SUCCESS");
		} catch (BaseException serviceException) {
			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
			response.setResult("FAILURE");
		}
		return gson.toJson(response);
	}
	
	@POST
	@Path("deletePost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deletePost(String str) {
		Response response = new Response();
		Gson gson = new Gson();
		Post post = gson.fromJson(str, Post.class);
		post.setBody(post.getBody().substring(0, 150));
		try {
			// check for user login
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String status = userFacade.checkLoginStatus(post.getUserID());
			// Calling create user through Facade layer
			String result = "FAILURE";
			if(status.equalsIgnoreCase("SUCCESS")){  
			result = userFacade.deletePost(post.getPostID());
			}else{
				throw new BaseException("APP-01-02", "Please Login first, for first time user please register");
			}

			response.setResult(result);

		} catch (BaseException serviceException) {

			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
			response.setResult("FAILURE");
		}

		return gson.toJson(response);
	}
	@POST
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createUser(String str) {
		Response response = new Response();
		Gson gson = new Gson();
		User userToSave = gson.fromJson(str, User.class);
		try {
			// Calling create user through Facade layer
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String result = userFacade.createUser(userToSave);
			response.setResult(result);
		} catch (BaseException serviceException) {
			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
			response.setResult("FAILURE");
		}

		return gson.toJson(response);
	}
	
	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String str) {
		Response response = new Response();
		Gson gson = new Gson();
		User userToLogin = gson.fromJson(str, User.class);
		try {
			// Calling create user through Facade layer
			UserPostFacade userFacade = new UserPostFacadeImpl();
			String result = userFacade.login(userToLogin);

			response.setResult(result);

		} catch (BaseException serviceException) {

			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
			response.setResult("FAILURE");
		}
		return gson.toJson(response);
	}
}
