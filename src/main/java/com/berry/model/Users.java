package com.berry.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "ers_users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "users_id")
	private int users_id;	
	
	@Column(name = "username", length = 50, unique = true)
	private String username;
	
	@JsonIgnore
	@Column(name = "password", length = 65)
	private String password;
	
	@Column(name = "first_name", length = 100)
	private String first_name;
	
	@Column(name = "last_name", length = 100)
	private String last_name;
	
	@Column(name = "email", length = 150, unique = true)
	private String email;
	
	//TODO: keep an eye on this one
	@OneToOne (cascade= {CascadeType.PERSIST})
	@JoinColumn(name = "role_id", referencedColumnName = "role_id")
	private Role role_id;
	
	public Users() {}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((role_id == null) ? 0 : role_id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}	
}
