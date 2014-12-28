package com.victoroskar.hateup;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;

import java.util.ArrayList;
import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import com.google.appengine.api.utils.SystemProperty;
import javax.inject.Named;

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
public class Greetings {

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
	  System.out.println("asdasdsad");
	  try {
		  System.out.println("ddddd");
	  String url = null;
//	  if (SystemProperty.environment.value() ==
//	  SystemProperty.Environment.Value.Production) {
	  // Connecting from App Engine.
	  // Load the class that provides the "jdbc:google:mysql://"
	  // prefix.
	  Class.forName("com.mysql.jdbc.GoogleDriver");
	  url =
	  "jdbc:google:mysql://natural-client-801:hateup-primary?user=root";
//	  } else {
//	    // You may also assign an IP Address from the access control
//	  // page and use it to connect from an external network.
//	  }
	  System.out.println("fffff");
	  Connection conn = DriverManager.getConnection(url);
	  ResultSet rs = conn.createStatement().executeQuery(
	  "SELECT 1 + 1");
	  System.out.println("ddasdasdasdasdsddd");
	  response.message = rs.toString();
	  } catch(Exception e) {
		  System.out.println(e.toString()+"  "+e);
		  
	  }
	  return response;
  }
}
