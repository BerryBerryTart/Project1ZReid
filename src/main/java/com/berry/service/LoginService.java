package com.berry.service;

import com.berry.DTO.CreateUserDTO;
import com.berry.DTO.LoginDTO;
import com.berry.dao.LoginRepo;
import com.berry.exception.BadParameterException;
import com.berry.exception.CreationException;
import com.berry.exception.NotFoundException;
import com.berry.model.Users;

public class LoginService {
	private LoginRepo loginRepo;
	
	public LoginService(LoginRepo loginRepo) {
		this.loginRepo = loginRepo;
	}
	
	public LoginService() {
		this.loginRepo = new LoginRepo();
	}
	
	public Users registerUser(CreateUserDTO createUserDTO) throws BadParameterException, CreationException {
		Users user = null;
		
		if (createUserDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		}
		
		user = loginRepo.createUser(createUserDTO);
		
		return user;
	}

	public Users loginUser(LoginDTO loginDTO) throws BadParameterException, NotFoundException {
		Users user = null;
		
		if (loginDTO.noFieldEmpty() == false) {
			throw new BadParameterException("All Fields Are Required");
		}
		
		user = loginRepo.loginUser(loginDTO);
		
		return user;
	}

}
