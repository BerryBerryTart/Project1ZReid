package com.berry.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "ers_users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int users_id;	
	
	@Column(name = "username", unique = true, length = 50)
	private String username;
	
	@Column(name = "password", length = 50)
	private String password;
	
	@Column(name = "first_name", length = 100)
	private String first_name;
	
	@Column(name = "last_name", length = 100)
	private String last_name;
	
	@Column(name = "email", unique = true, length = 150)
	private String email;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private Role role_id;

	public Users(String username, String password, String first_name, String last_name, String email) {
		super();
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
	}
	
	
	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole_id() {
		return role_id;
	}

	public void setRole_id(Role role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "Users [users_id=" + users_id + ", username=" + username + ", password=" + password + ", first_name="
				+ first_name + ", last_name=" + last_name + ", email=" + email + ", role_id=" + role_id + "]";
	}
}
