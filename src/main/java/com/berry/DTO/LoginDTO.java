package com.berry.DTO;

public class LoginDTO implements DTO{
	private String username;
	private String password;
	
	public LoginDTO() {
		super();
	}
	
	public boolean noFieldEmpty() {
		if (this.username == null || this.username.trim().equals("") == true) {
			return false;
		} else if (this.password == null || this.password.trim().equals("") == true) {
			return false;
		} 
		
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
