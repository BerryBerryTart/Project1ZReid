package com.berry.util;

import javax.lang.model.element.TypeElement;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.app.Application;
import com.berry.exception.DatabaseInitException;
import com.berry.model.Role;
import com.berry.model.RoleEnum;
import com.berry.model.Status;
import com.berry.model.StatusEnum;
import com.berry.model.Type;
import com.berry.model.TypeEnum;

public class PopulateTables {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private PopulateTables() {}
	
	public static void populate() throws DatabaseInitException {
		Session session = SessionUtility.getSession().openSession();
		session.beginTransaction();
		
		//ROLES
		Role employeeRole = new Role(RoleEnum.EMPLOYEE.getIndex(), RoleEnum.EMPLOYEE.toString());
		Role adminRole = new Role(RoleEnum.MANAGER.getIndex(), RoleEnum.MANAGER.toString());
		
		//STATUS
		Status pendingStatus = new Status(StatusEnum.PENDING.getIndex(), StatusEnum.PENDING.toString());
		Status completedStatus = new Status(StatusEnum.COMPLETED.getIndex(), StatusEnum.COMPLETED.toString());
		
		//TYPE
		Type buisnessType = new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString());
		Type relocationType = new Type(TypeEnum.RELOCATION.getIndex(), TypeEnum.RELOCATION.toString());
		Type otherType = new Type(TypeEnum.OTHER.getIndex(), TypeEnum.OTHER.toString());
		
		try {
			session.save(employeeRole);
			session.save(adminRole);
			
			session.save(pendingStatus);
			session.save(completedStatus);
			
			session.save(buisnessType);
			session.save(relocationType);
			session.save(otherType);
		} catch (ConstraintViolationException e) {
			logger.error("Duplicate Key" + e.getMessage());
			throw new DatabaseInitException("Failure To Initialise Tables.");
		} finally {
			session.close();
		}
	}	
}
