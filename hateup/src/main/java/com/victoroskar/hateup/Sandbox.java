package com.victoroskar.hateup;

import java.security.InvalidParameterException;
import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.victoroskar.hateup.entity.Account;
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

		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			Query query = em.createQuery("Select m FROM Message m WHERE m.owner='" + accountName + "'");
			response = query.getResultList();
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
	public void postRealTalk(@Named("realTalk") String realTalk, @Named("account_id") Long accountID) throws NotFoundException {
		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			Query query = em.createQuery("Select a FROM Account a WHERE a.account_id='" + accountID + "'");
			List<Account> response = query.getResultList();
			
			em.getTransaction().begin();
			em.persist(new Message(realTalk, response.get(0)));
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@ApiMethod(name = "createAccount", path = "createaccount", httpMethod = HttpMethod.POST)
	public void createAccount(@Named("username") String username, @Named("location") String location) throws NotFoundException {
		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			em.getTransaction().begin();
			em.persist(new Account(username, location));
			em.getTransaction().commit();
			em.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@ApiMethod(name = "getAccount", path = "getaccount", httpMethod = HttpMethod.GET)
	public List<Account> getAccount(@Named("username") String username) throws NotFoundException {
		List<Account> response = null;

		try {
			EntityManager em = DataLayerFactory.CreateEntityManager();
			Query query = em.createQuery("Select a FROM Account a WHERE a.username='" + username + "'");
			response = query.getResultList();
			em.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}

		return response;
	}
}
