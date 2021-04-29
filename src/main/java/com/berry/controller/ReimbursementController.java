package com.berry.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller  {
	
	private Handler addTicketHandler = (ctx) -> {

	};
	
	private Handler getAllTicketsHandler = (ctx) -> {

	};
	
	private Handler getTicketByIdHandler = (ctx) -> {

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
