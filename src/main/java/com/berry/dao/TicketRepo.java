package com.berry.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.DTO.CreateTicketDTO;
import com.berry.app.Application;
import com.berry.exception.NotFoundException;
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

	public Reimbursement getUserTicketById(Users user, int id) throws NotFoundException {
		Session session = SessionUtility.getSession().openSession();
		
		Reimbursement ticket = new Reimbursement();
		String hql = "From Reimbursement r WHERE r.author_id = :authorid AND r.reimb_id = :reimbid";
	
		try {
			ticket = (Reimbursement) session.createQuery(hql)
					.setParameter("authorid", user)
					.setParameter("reimbid", id)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new NotFoundException("Ticket Not Found");
		} finally {
			session.close();
		}
		
		return ticket;
	}

	public List<Reimbursement> getAllUserTickets(Users user) throws NotFoundException {
		List<Reimbursement> tickets = new ArrayList<Reimbursement>();
		Session session = SessionUtility.getSession().openSession();
		
		String hql = "From Reimbursement r WHERE r.author_id = :authorid";
		
		try {
			tickets = session.createQuery(hql, Reimbursement.class).setParameter("authorid", user).getResultList();
		} catch (NoResultException e) {
			throw new NotFoundException("Ticket Not Found");
		} finally {
			session.close();
		}
		
		return tickets;
	}

	public List<Reimbursement> getAllAdminTickets(Users user) {
		List<Reimbursement> tickets = new ArrayList<Reimbursement>();
		Session session = SessionUtility.getSession().openSession();
		
		String hql = "From Reimbursement r";
		
		tickets = session.createQuery(hql, Reimbursement.class).getResultList();
		
		return tickets;
	}

}
