package com.victoroskar.hateup;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.utils.SystemProperty;
import com.victoroskar.hateup.entity.DataLayerFactory;
import com.victoroskar.hateup.entity.Message;

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(name = "hateup", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID, Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, audiences = { Constants.ANDROID_AUDIENCE })
public class Sandbox {

	@ApiMethod(name = "greetings.authed", path = "hellogreeting/authed")
	public HelloGreeting authedGreeting(User user) {
		HelloGreeting response = new HelloGreeting("hello " + user.getEmail());
		return response;
	}

	@ApiMethod(name = "getRealtalk", path = "getRealtalk", httpMethod = HttpMethod.GET)
	public List<Message> getRealTalk(@Named("account") String accountName) throws NotFoundException {

		if (accountName == null || accountName.isEmpty()) {
			throw new InvalidParameterException("No account provided.");
		}

		List<Message> response = null;
		Map<String, String> properties = new HashMap();

		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			Query query = em.createQuery("Select m FROM Message m WHERE m.owner='" + accountName + "'");
			response = query.getResultList();

			for (Message x : response) {
				System.out.println(x.message);
			}

			em.close();
		} catch (Exception e) {
			System.out.println("Exception: \n");
			System.out.println(e.toString() + "  \n ");

			for (StackTraceElement x : e.getStackTrace()) {
				System.out.println("Stack Element: " + x.toString());
			}
		}

		return response;
	}

	@ApiMethod(name = "postRealTalk", path = "postrealtalk", httpMethod = HttpMethod.POST)
	public void postRealTalk(@Named("realTalk") String realTalk, @Named("username") String username) throws NotFoundException {
		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			em.getTransaction().begin();
			em.persist(new Message(realTalk, username));
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			System.out.println("Exception: \n");

			System.out.println(e.toString() + "  \n ");
			for (StackTraceElement x : e.getStackTrace()) {
				System.out.println("Stack Element: " + x.toString());
			}
		}
	}
}
