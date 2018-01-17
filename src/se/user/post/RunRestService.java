package se.user.post;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

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

	@GET
	@Path("getUser")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUser() {

		Response response = new Response();
		System.out.println("Inside Method");
		System.out.println("str");
		Gson gson = new Gson();
		try {
			// Calling getUser through Facade layer
			UserPostFacade userFacade = new UserPostFacadeImpl();

			response.setResult("Success");
			if (false) {
				throw new BaseException("", "");
			}

		} catch (BaseException serviceException) {

			ErrorResultBaseType erBase = new ErrorResultBaseType();
			erBase.setErrorCode(serviceException.getExceptionCode());
			erBase.setErrorDescription(serviceException.getMessage());
			response.setError(erBase);
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
