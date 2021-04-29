package com.berry.DTO;

public class CreateUserDTO implements DTO{
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private String email;	

	public CreateUserDTO() {}
	
	public CreateUserDTO(String first_name, String last_name, String username, String password, String email) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public boolean noFieldEmpty() {
		if (this.first_name == null || this.first_name.trim().equals("") == true) {
			return false;
		} else if (this.last_name == null || this.last_name.trim().equals("") == true) {
			return false;
		} else if (this.username == null || this.username.trim().equals("") == true) {
			return false;
		} else if (this.password == null || this.password.trim().equals("") == true) {
			return false;
		} else if (this.email == null || this.email.trim().equals("") == true) {
			return false;
		}
		return true;
	}
	
	public String getFirst_name() {
		return first_name;
	}
	
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLast_name() {
		return last_name;
	}
	
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}	
}
