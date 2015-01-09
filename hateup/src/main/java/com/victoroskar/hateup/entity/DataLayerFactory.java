package com.victoroskar.hateup.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;

public class DataLayerFactory {
	public static EntityManager CreateEntityManager() {
		Map<String, String> properties = new HashMap();
		String url = null;
		
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
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
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-app", properties);
		EntityManager em = emf.createEntityManager();
		return em;
	}
}
