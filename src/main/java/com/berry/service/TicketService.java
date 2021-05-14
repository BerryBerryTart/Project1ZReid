package com.berry.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
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
import com.berry.model.StatusEnum;
import com.berry.model.TypeEnum;
import com.berry.model.Users;

public class TicketService {
	private TicketRepo ticketRepo;
	
	public TicketService() {
		this.ticketRepo = new TicketRepo();
	}
	
	public TicketService(TicketRepo ticketRepo) {
		this.ticketRepo = ticketRepo;
	}
	
	public Reimbursement CreateTicket(Users user, CreateTicketDTO createTicketDTO) throws BadParameterException, ServiceLayerException, ImproperTypeException {
		Reimbursement ticket = null;
		
		//check DTO has all fields
		if (createTicketDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		}
		
		//CHECK if receipt is a valid file type
		String contentType = null;
		try {
			contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(createTicketDTO.getReceipt()));
		} catch (IOException e1) {
			throw new ImproperTypeException(e1.getMessage());
		}
		
		if (contentType == null ) {
			throw new ImproperTypeException("No File Provided");			
		} else if (contentType.equals("image/gif") || contentType.equals("image/jpeg") || contentType.equals("image/png")){
			
		} else {
			throw new ImproperTypeException("Invalid Receipt File Type");
		}
		
		//Check if type is valid
		if (TypeEnum.getEnum(createTicketDTO.getType()) == null) {
			throw new BadParameterException("Type Not Valid");
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
		
		//All fields are filled check
		if (ticketStatusDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		} 
		
		//query status is valid check
		if (!ticketStatusDTO.getStatus().toUpperCase().equals(StatusEnum.COMPLETED.toString()) 
				&& !ticketStatusDTO.getStatus().toUpperCase().equals(StatusEnum.REJECTED.toString())) {
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
