package com.berry.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.app.Application;
import com.berry.model.Role;
import com.berry.model.RoleEnum;
import com.berry.model.Status;
import com.berry.model.StatusEnum;
import com.berry.model.Type;
import com.berry.model.TypeEnum;
import com.berry.model.Users;

public class PopulateTables {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	private PopulateTables() {}
	
	public static void populate(){
		Session session = SessionUtility.getSession().openSession();
		session.beginTransaction();
		
		List<Role> roles = new ArrayList<Role>();
		List<Status> statuses = new ArrayList<Status>();
		List<Type> types = new ArrayList<Type>();
		
		//ROLES
		Role employeeRole = new Role(RoleEnum.EMPLOYEE.getIndex(), RoleEnum.EMPLOYEE.toString());
		roles.add(employeeRole);
		Role adminRole = new Role(RoleEnum.MANAGER.getIndex(), RoleEnum.MANAGER.toString());
		roles.add(adminRole);
		
		for (Role r : roles) {
			if (session.get(Role.class, r.getRole_id()) == null) {
				session.save(r);
			}
		}
		logger.info("ERS_ROLE TABLE INITIALISED");
		
		//STATUS
		Status pendingStatus = new Status(StatusEnum.PENDING.getIndex(), StatusEnum.PENDING.toString());
		statuses.add(pendingStatus);
		Status completedStatus = new Status(StatusEnum.COMPLETED.getIndex(), StatusEnum.COMPLETED.toString());
		statuses.add(completedStatus);
		Status rejectedStatus = new Status(StatusEnum.REJECTED.getIndex(), StatusEnum.REJECTED.toString());
		statuses.add(rejectedStatus);
		
		for (Status s : statuses) {
			if (session.get(Status.class, s.getStatus_id()) == null) {
				session.save(s);
			}
		}
		logger.info("ERS_STATUS TABLE INITIALISED");
		
		//TYPE
		Type buisnessType = new Type(TypeEnum.BUISNESS.getIndex(), TypeEnum.BUISNESS.toString());
		types.add(buisnessType);
		Type relocationType = new Type(TypeEnum.RELOCATION.getIndex(), TypeEnum.RELOCATION.toString());
		types.add(relocationType);
		Type otherType = new Type(TypeEnum.OTHER.getIndex(), TypeEnum.OTHER.toString());
		types.add(otherType);
		
		for (Type t : types) {
			if (session.get(Type.class, t.getType_id()) == null) {
				session.save(t);
			}
		}
		logger.info("ERS_TYPE TABLE INITIALISED");
		
		//DEFAULT ADMIN ACCOUNT
		Users admin = new Users();
		admin.setUsers_id(1);
		admin.setEmail("admin@admin.com");
		admin.setFirst_name("root");
		admin.setLast_name("root");
		admin.setUsername("root");
		admin.setPassword("root");			
		Role rootAdminRole = session.load(Role.class, RoleEnum.MANAGER.getIndex());
		admin.setRole_id(rootAdminRole);
		
		if (session.get(Users.class, 1) == null) {
			session.save(admin);
		}		
		
		//Goodbye
		session.getTransaction().commit();
		session.close();
	}	
}
