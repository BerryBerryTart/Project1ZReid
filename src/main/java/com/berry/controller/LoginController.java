package com.berry.controller;

import com.berry.DTO.CreateUserDTO;
import com.berry.DTO.LoginDTO;
import com.berry.exception.BadParameterException;
import com.berry.model.Role;
import com.berry.model.Users;
import com.berry.service.LoginService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class LoginController implements Controller {	
	private LoginService loginService;

	public LoginController() {
		this.loginService = new LoginService();
	}

	private Handler loginHandler = (ctx) -> {
		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);
		
		Users user = loginService.loginUser(loginDTO);
		
		ctx.sessionAttribute("currentlyLoggedInUser", user);
		ctx.json(user);
	};
	
	private Handler registerUserHandler = (ctx) -> {
		CreateUserDTO createUserDTO = new CreateUserDTO();
		
		try {
			createUserDTO = ctx.bodyAsClass(CreateUserDTO.class);
		} catch (Exception e) {
			throw new BadParameterException(e.getMessage());
		}
		
		Users user = loginService.registerUser(createUserDTO);
		
		if (user != null) {
			ctx.json(user);
			ctx.status(201);
		}
		
	};

	private Handler currentUserHandler = (ctx) -> {
		Users user = (Users) ctx.sessionAttribute("currentlyLoggedInUser");
		
		if (user == null) {
			ctx.json(ResponseMap.getResMap("Error", "User Is Not Logged In."));
			ctx.status(400);
		} else {
			ctx.json(user);
		}
		
	};
	
	private Handler logoutHandler = (ctx) -> {
		ctx.req.getSession().invalidate();
		ctx.status(200);
	};

	public void mapEndpoints(Javalin app) {
		app.post("/login_acc", loginHandler);
		app.post("/register_acc", registerUserHandler);
		app.get("/current_user", currentUserHandler);
		app.post("/logout_acc", logoutHandler);
		app.get("/role", (ctx) -> {
			Users user = (Users) ctx.sessionAttribute("currentlyLoggedInUser");
			Role role = user.getRole_id();
			ctx.json(role);
			ctx.status(200);			
		});
	}

}
