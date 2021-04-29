package com.berry.service;

import com.berry.DTO.CreateTicketDTO;
import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.ServiceLayerException;
import com.berry.model.Reimbursement;
import com.berry.model.Users;

public class TicketService {
	private TicketRepo ticketRepo;
	
	public TicketService() {
		this.ticketRepo = new TicketRepo();
	}
	
	public Reimbursement CreateTicket(Users user, CreateTicketDTO createTicketDTO) throws BadParameterException, ServiceLayerException {
		Reimbursement ticket = null;
		
		if (createTicketDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		}
		
		ticket = ticketRepo.createTicket(user, createTicketDTO);
		
		if (ticket == null) {
			throw new ServiceLayerException("Service Layer Error");
		}
		
		return ticket;
	}

}
