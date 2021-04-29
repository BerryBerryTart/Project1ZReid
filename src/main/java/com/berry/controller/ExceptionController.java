package com.berry.controller;

import com.berry.exception.CreationException;
import com.berry.exception.NotFoundException;

import io.javalin.Javalin;

public class ExceptionController implements Controller {
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(CreationException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
		});
		app.exception(NotFoundException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
		});
		
	}
}
