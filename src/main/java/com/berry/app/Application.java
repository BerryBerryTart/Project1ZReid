package com.berry.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.berry.controller.Controller;
import com.berry.controller.ExceptionController;
import com.berry.controller.LoginController;
import com.berry.controller.TicketController;
import com.berry.util.PopulateTables;

import io.javalin.Javalin;

public class Application {
	private static Javalin app;
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	public static transient String fileIniPath;	
	
	public static void main(String[] args) {
		if (args.length != 0) {
			fileIniPath = args[0];
		} else {
			fileIniPath = "src/main/resources/db.ini";
		}
		
		try {
			PopulateTables.populate();
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.exit(-1);
		}
		
		app = Javalin.create(); //config -> {;
//			config.addStaticFiles("/static");
//		});
		
		app.before(ctx -> {
			String URI = ctx.req.getRequestURI();
			String httpMethod = ctx.req.getMethod();
			logger.info(httpMethod + " request to endpoint '" + URI + "' received");
		});
		
		mapControllers(new ExceptionController(), new LoginController(), new TicketController());
		
		app.start(5000);
		
	}
	
	public static void mapControllers(Controller... controllers) {
		for (Controller c : controllers) {
			c.mapEndpoints(app);
		}
	}

}
