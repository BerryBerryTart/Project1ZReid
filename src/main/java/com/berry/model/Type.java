package com.berry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "ers_reimbursement_type")
@Proxy(lazy = false)
public class Type {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "type_id")
	private int type_id;
	
	@Column(name = "type", length = 10, unique = true)
	private String type;	

	public Type() {}
	
	public Type(int type_id, String type) {
		this.type_id = type_id;
		this.type = type;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
