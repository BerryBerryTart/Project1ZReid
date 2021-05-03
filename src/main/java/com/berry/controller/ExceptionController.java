package com.berry.controller;

import com.berry.exception.BadParameterException;
import com.berry.exception.CreationException;
import com.berry.exception.DatabaseExeption;
import com.berry.exception.ImproperTypeException;
import com.berry.exception.NotFoundException;

import io.javalin.Javalin;

public class ExceptionController implements Controller {
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(CreationException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
			ctx.status(500);
		});
		app.exception(NotFoundException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
			ctx.status(404);
		});
		app.exception(BadParameterException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
			ctx.status(400);
		});
		app.exception(DatabaseExeption.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
			ctx.status(500);
		});
		app.exception(ImproperTypeException.class, (e, ctx) -> {
			ctx.json(ResponseMap.getResMap("Error", e.getMessage()));
			ctx.status(401);
		});
	}
}
