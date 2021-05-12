package com.berry.util;

import java.io.File;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.app.Application;

public class SessionUtility {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private static transient String url;
	private static transient String user;
	private static transient String pass;
	
	private static SessionFactory sessionFactory;

	public synchronized static Session getSession() {
		if (sessionFactory == null) {
			getCreds();
			sessionFactory = new Configuration()
					.setProperty("hibernate.connection.url", url)
					.setProperty("hibernate.connection.username", user)
					.setProperty("hibernate.connection.password", pass)
					.configure("hibernate.cfg.xml").buildSessionFactory();
		}

		return sessionFactory.openSession();
	}
	
	private static void getCreds() {
		Wini ini;
		try {
			ini = new Wini(new File(Application.fileIniPath));
			url = ini.get("database", "servername");
			user = ini.get("database", "username");
			pass = ini.get("database", "password");
		} catch (InvalidFileFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
	}
}
