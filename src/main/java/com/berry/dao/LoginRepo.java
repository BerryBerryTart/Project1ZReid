package com.berry.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.DTO.CreateUserDTO;
import com.berry.DTO.LoginDTO;
import com.berry.app.Application;
import com.berry.exception.CreationException;
import com.berry.exception.NotFoundException;
import com.berry.model.Role;
import com.berry.model.Users;
import com.berry.util.SessionUtility;

public class LoginRepo {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public Users createUser(CreateUserDTO createUserDTO) throws CreationException {
		Session session = SessionUtility.getSession().openSession();
		
		Users user = null;
		Role role = session.load(Role.class, 1);		
		
		session.beginTransaction();
		user = new Users();
		user.setFirst_name(createUserDTO.getFirst_name());
		user.setLast_name(createUserDTO.getLast_name());
		user.setEmail(createUserDTO.getEmail());
		user.setUsername(createUserDTO.getUsername());
		user.setPassword(createUserDTO.getPassword());
		user.setRole_id(role);
		try {
			session.save(user);
			session.getTransaction().commit();			
			
		} catch (ConstraintViolationException e) {
			logger.error("Duplicate Key" + e.getMessage());
			throw new CreationException("Duplicate Key: " + e.getMessage());
		} finally {
			session.close();
		}		
		
		return user;
	}

	public Users loginUser(LoginDTO loginDTO) throws NotFoundException {		
		Session session = SessionUtility.getSession().openSession();
		
		Users user = null;
		String hql = "FROM Users u WHERE u.username = :username AND u.password = :password";
		
		try {
			user = (Users) session.createQuery(hql)
				.setParameter("username", loginDTO.getUsername())
				.setParameter("password", loginDTO.getPassword())
				.getSingleResult();
		} catch (NoResultException e) {
			throw new NotFoundException("User Not Found");
		} finally {
			session.close();
		}
		
		return user;
	}
}
