package com.berry.controller;

import com.berry.DTO.CreateTicketDTO;
import com.berry.model.Reimbursement;
import com.berry.model.Users;
import com.berry.service.TicketService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class TicketController implements Controller  {
	private TicketService ticketService;
	
	public TicketController() {
		this.ticketService = new TicketService();
	}
	
	private Handler addTicketHandler = (ctx) -> {
		Users user = (Users) ctx.sessionAttribute("currentlyLoggedInUser");		
		if (user == null) {
			ctx.json(ResponseMap.getResMap("Error", "User Is Not Logged In."));
			ctx.status(400);
		} else {		
			Reimbursement ticket = null;		
			CreateTicketDTO createTicketDTO = new CreateTicketDTO();		
			createTicketDTO = ctx.bodyAsClass(CreateTicketDTO.class);
			
			ticket = ticketService.CreateTicket(user, createTicketDTO);
			
			if (ticket != null) {
				ctx.json(ticket);
				ctx.status(201);
			}
		}
		
	};
	
	private Handler getAllTicketsHandler = (ctx) -> {
		Users user = (Users) ctx.sessionAttribute("currentlyLoggedInUser");		
		if (user == null) {
			ctx.json(ResponseMap.getResMap("Error", "User Is Not Logged In."));
			ctx.status(400);
		} else {		
			
		}
	};
	
	private Handler getTicketByIdHandler = (ctx) -> {
		Users user = (Users) ctx.sessionAttribute("currentlyLoggedInUser");		
		if (user == null) {
			ctx.json(ResponseMap.getResMap("Error", "User Is Not Logged In."));
			ctx.status(400);
		} else {		
			Reimbursement ticket = null;
			String stringTicketID = ctx.pathParam("id");
			
			ticket = ticketService.getTicketById(user, stringTicketID);
			
			if (ticket != null) {
				ctx.json(ticket);
				ctx.status(201);
			}
		}
	};
	
	//ADMIN PRIVS
	
	private Handler adminViewAllTicketsHandler = (ctx) -> {
		
	};
	
	private Handler adminViewTicketByIdHandler = (ctx) -> {

	};
	
	private Handler adminUpdateTicketHandler = (ctx) -> {

	};
	
	public void mapEndpoints(Javalin app) {
		app.post("/add_ticket", addTicketHandler);
		app.get("/get_ticket_status/", getAllTicketsHandler);
		app.get("/get_ticket_status/:id", getTicketByIdHandler);
		
		//ADMIN PRIVS
		app.get("/view_ticket", adminViewAllTicketsHandler);
		app.get("/view_ticket/:id", adminViewTicketByIdHandler);
		app.put("/update_ticket/:id", adminUpdateTicketHandler);
	}
}
