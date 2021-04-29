package com.berry.service;

import com.berry.DTO.CreateTicketDTO;
import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.NotFoundException;
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

	public Reimbursement getTicketById(Users user, String stringTicketID) throws BadParameterException, ServiceLayerException, NotFoundException {
		Reimbursement ticket = null;
		
		try {
			int id = Integer.parseInt(stringTicketID);			
			ticket = ticketRepo.getTicketById(user, id);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Param must be an integer.");
		}
		
		if (ticket == null) {
			throw new ServiceLayerException("Service Layer Error");
		}
		
		return ticket;
	}
	
	
	
}
