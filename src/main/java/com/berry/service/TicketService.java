package com.berry.service;

import java.util.ArrayList;
import java.util.List;

import com.berry.DTO.CreateTicketDTO;
import com.berry.DTO.TicketStatusDTO;
import com.berry.dao.TicketRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.ImproperTypeException;
import com.berry.exception.NotFoundException;
import com.berry.exception.ServiceLayerException;
import com.berry.model.Reimbursement;
import com.berry.model.Users;

public class TicketService {
	private TicketRepo ticketRepo;
	
	public TicketService() {
		this.ticketRepo = new TicketRepo();
	}
	
	public Reimbursement CreateTicket(Users user, CreateTicketDTO createTicketDTO) throws BadParameterException, ServiceLayerException, ImproperTypeException {
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

	public Reimbursement getUserTicketById(Users user, String stringTicketID) throws BadParameterException, ServiceLayerException, NotFoundException {
		Reimbursement ticket = null;
		
		try {
			int id = Integer.parseInt(stringTicketID);
			ticket = ticketRepo.getUserTicketById(user, id);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Param must be an integer.");
		}
		
		if (ticket == null) {
			throw new ServiceLayerException("Service Layer Error");
		}
		
		return ticket;
	}

	public List<Reimbursement> getAllUserTickets(Users user) throws NotFoundException {
		List<Reimbursement> tickets = new ArrayList<Reimbursement>();
		
		tickets = ticketRepo.getAllUserTickets(user);
		
		return tickets;
	}

	public List<Reimbursement> getAllAdminTickets() throws NotFoundException {
		List<Reimbursement> tickets = new ArrayList<Reimbursement>();
		
		tickets = ticketRepo.getAllAdminTickets();
		
		return tickets;
	}

	public Reimbursement getAdminTicketById(String stringTicketID) throws BadParameterException, ServiceLayerException, NotFoundException {
		Reimbursement ticket = null;
		
		try {
			int id = Integer.parseInt(stringTicketID);
			ticket = ticketRepo.getAdminTicketById(id);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Param must be an integer.");
		}
		
		if (ticket == null) {
			throw new ServiceLayerException("Service Layer Error");
		}
		
		return ticket;
	}

	public Reimbursement updateAdminTicketById(String stringTicketID, Users user, TicketStatusDTO ticketStatusDTO) throws BadParameterException, ServiceLayerException, NotFoundException {
		Reimbursement ticket = null;
		if (ticketStatusDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		} else if (ticketStatusDTO.fieldsAreValid() == false) {
			throw new BadParameterException("'" + ticketStatusDTO.getStatus() + "' Is Not A Valid Status");
		}
		try {
			int id = Integer.parseInt(stringTicketID);
			ticket = ticketRepo.updateAdminTicketById(id, user, ticketStatusDTO);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Param must be an integer.");
		}
		
		if (ticket == null) {
			throw new ServiceLayerException("Service Layer Error");
		}		
		return ticket;
	}

	public byte[] fetchTicketBlob(Users user, String stringTicketID) throws BadParameterException, NotFoundException {
		byte[] ticketBlob = null;
		
		try {
			int id = Integer.parseInt(stringTicketID);
			ticketBlob = ticketRepo.fetchTicketBlob(user, id);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Param must be an integer.");
		}
		
		return ticketBlob;
	}
	
	
	
}
