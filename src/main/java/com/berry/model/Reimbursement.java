package com.berry.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ers_reimbursement")
public class Reimbursement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reimb_id")
	private int reimb_id;
	
	@Column(name = "amount")
	private double amount;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submitted")
	private Date submitted;

	@Column(name = "resolved")
	private Date resolved;
	
	@Column(name = "description", length = 250)
	private String description;
	
	@Column(name = "receipt", columnDefinition = "LONGBLOB")
	private byte[] receipt;
	
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "user_id")
	private Users author_id;
	
	@ManyToOne
	@JoinColumn(name = "resolver_id", referencedColumnName = "user_id")
	private Users resolver_id;
	
	@OneToOne
	@JoinColumn(name = "status_id", referencedColumnName = "status_id")
	private Status status_id;
	
	@OneToOne
	@JoinColumn(name = "type_id", referencedColumnName = "type_id")
	private Type type_id;
	
	public Reimbursement() {}

	public Reimbursement(int amount, Date resolved, String description, Users author, Users resolver, Status status_id,
			Type type_id) {
		super();
		this.amount = amount;
		this.resolved = resolved;
		this.description = description;
		this.author_id = author;
		this.resolver_id = resolver;
		this.status_id = status_id;
		this.type_id = type_id;
	}

	public int getReimb_id() {
		return reimb_id;
	}

	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public Date getResolved() {
		return resolved;
	}

	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	public Users getAuthor() {
		return author_id;
	}

	public void setAuthor(Users author_id) {
		this.author_id = author_id;
	}

	public Users getResolver() {
		return resolver_id;
	}

	public void setResolver(Users resolver_id) {
		this.resolver_id = resolver_id;
	}

	public Status getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Status status_id) {
		this.status_id = status_id;
	}

	public Type getType_id() {
		return type_id;
	}

	public void setType_id(Type type_id) {
		this.type_id = type_id;
	}	
}
