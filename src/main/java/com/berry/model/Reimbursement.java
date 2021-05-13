package com.berry.model;

import java.util.Arrays;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@Column(name = "receipt", columnDefinition = "LONGBLOB")
	private byte[] receipt;
	
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "users_id")
	private Users author_id;
	
	@ManyToOne
	@JoinColumn(name = "resolver_id", referencedColumnName = "users_id")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((author_id == null) ? 0 : author_id.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + reimb_id;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolver_id == null) ? 0 : resolver_id.hashCode());
		result = prime * result + ((status_id == null) ? 0 : status_id.hashCode());
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + ((type_id == null) ? 0 : type_id.hashCode());
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (author_id == null) {
			if (other.author_id != null)
				return false;
		} else if (!author_id.equals(other.author_id))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (reimb_id != other.reimb_id)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver_id == null) {
			if (other.resolver_id != null)
				return false;
		} else if (!resolver_id.equals(other.resolver_id))
			return false;
		if (status_id == null) {
			if (other.status_id != null)
				return false;
		} else if (!status_id.equals(other.status_id))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type_id == null) {
			if (other.type_id != null)
				return false;
		} else if (!type_id.equals(other.type_id))
			return false;
		return true;
	}
	
	
}
