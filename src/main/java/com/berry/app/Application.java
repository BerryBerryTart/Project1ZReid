package com.berry.app;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.model.Users;
import com.berry.util.SessionUtility;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	public static String fileIniPath;	
	
	public static void main(String[] args) {
		if (args.length != 0) {
			fileIniPath = args[0];
		} else {
			fileIniPath = "src/main/resources/db.ini";
		}
		Session session = SessionUtility.getSession();
		
		Transaction tx = session.beginTransaction();
		
		Users u1 = new Users("bob", "of", "booey", "yea", "man");
		session.save(u1);
		tx.commit();
		
		Users user = (Users) session.createQuery("SELECT u from Users").getResultList();
		System.out.println(user);
	}

}
