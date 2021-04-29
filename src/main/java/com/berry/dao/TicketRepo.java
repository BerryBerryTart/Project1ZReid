package com.berry.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.DTO.CreateTicketDTO;
import com.berry.app.Application;
import com.berry.model.Reimbursement;
import com.berry.model.Status;
import com.berry.model.StatusEnum;
import com.berry.model.Type;
import com.berry.model.TypeEnum;
import com.berry.model.Users;
import com.berry.util.SessionUtility;

public class TicketRepo {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public Reimbursement createTicket(Users user, CreateTicketDTO createTicketDTO) {
		Session session = SessionUtility.getSession().openSession();
		session.beginTransaction();
		
		Reimbursement ticket = new Reimbursement();
		
		try {
			TypeEnum typeEnum = TypeEnum.getEnum(createTicketDTO.getType());
			Type type = session.load(Type.class, typeEnum.getIndex());
			Status status = session.load(Status.class, StatusEnum.PENDING.getIndex());			
			
			ticket.setAmount(createTicketDTO.getAmount());
			ticket.setDescription(createTicketDTO.getDescription());
			ticket.setType_id(type);
			ticket.setAuthor(user);
			ticket.setStatus_id(status);
			
			session.save(ticket);
			session.getTransaction().commit();			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			session.close();
		}		
		
		return ticket;
	}

}
