package com.victoroskar.hateup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.utils.SystemProperty;
import com.victoroskar.hateup.entity.Message;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(
    name = "hateup",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    clientIds = {Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class Sandbox {
 
  public static ArrayList<HelloGreeting> greetings = new ArrayList<HelloGreeting>();

  static {
    greetings.add(new HelloGreeting("hello world!"));
    greetings.add(new HelloGreeting("goodbye world!"));
  }

  public HelloGreeting getGreeting(@Named("id") Integer id) throws NotFoundException {
    try {
      return greetings.get(id);
    } catch (IndexOutOfBoundsException e) {
      throw new NotFoundException("Greeting not found with an index: " + id);
    }
  }

  public ArrayList<HelloGreeting> listGreeting() {
    return greetings;
  }

  @ApiMethod(name = "greetings.multiply", httpMethod = "post")
  public HelloGreeting insertGreeting(@Named("times") Integer times, HelloGreeting greeting) {
    HelloGreeting response = new HelloGreeting();
    StringBuilder responseBuilder = new StringBuilder();
    for (int i = 0; i < times; i++) {
      responseBuilder.append(greeting.getMessage());
    }
    response.setMessage(responseBuilder.toString());
    return response;
  }

  @ApiMethod(name = "greetings.authed", path = "hellogreeting/authed")
  public HelloGreeting authedGreeting(User user) {
    HelloGreeting response = new HelloGreeting("hello " + user.getEmail());
    return response;
  }
  
  public HelloGreeting postRealTalk(@Named("realTalk") String realTalk) throws NotFoundException {
	  HelloGreeting response = new HelloGreeting();
	  response.message = "REALTALKNIGGA";

	  Map<String, String> properties = new HashMap();
	  
	  try {
		  String url = null;
		  if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			// Connecting from App Engine.
			// Load the class that provides the "jdbc:google:mysql://"
			// prefix.
			  properties.put("javax.persistence.jdbc.driver",
			          "com.mysql.jdbc.GoogleDriver");
			      properties.put("javax.persistence.jdbc.url",
			          System.getProperty("cloudsql.url.prod"));
		  } else {
			  properties.put("javax.persistence.jdbc.driver",
			          "com.mysql.jdbc.Driver");
		      properties.put("javax.persistence.jdbc.url",
			          System.getProperty("cloudsql.url.dev"));
		      properties.put("javax.persistence.jdbc.user",
			          System.getProperty("cloudsql.url.dev.user"));
		  }
		
//		  Connection conn = DriverManager.getConnection(url);
//		  ResultSet rs = conn.createStatement().executeQuery("SELECT 1 + 1");
		  System.out.println("Step 1: "+properties.get("javax.persistence.jdbc.driver")+ "  "+properties.get("javax.persistence.jdbc.url"));
		  EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-app", properties);
		  System.out.println("Step 2");

		    // Insert a few rows.
		    EntityManager em = emf.createEntityManager();
		    em.getTransaction().begin();
			  System.out.println("Step 3");
	
		    em.persist(new Message("user"));
		    em.getTransaction().commit();
			  System.out.println("Step 4");
	
		    em.close();
		  
		  response.message = "test";
	  } catch(Exception e) {
		  System.out.println("Exception: \n");

		  System.out.println(e.toString()+"  \n ");
		  for(StackTraceElement x : e.getStackTrace()) {
			  System.out.println("Stack Element: "+x.toString());
		  }
	  }
	  response.message += "TESTERTS";
	  return response;
  }
}
