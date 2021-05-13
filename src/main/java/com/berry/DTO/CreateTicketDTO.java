package com.berry.DTO;

import java.util.Arrays;

public class CreateTicketDTO implements DTO{
	private double amount;
	private String description;
	private String type;
	private byte[] receipt;
	
	public CreateTicketDTO() {}

	public CreateTicketDTO(double amount, String description, String type) {
		this.amount = amount;
		this.description = description;
		this.type = type;
	}
	
	public CreateTicketDTO(double amount, String description, String type, byte[] receipt) {
		this.amount = amount;
		this.description = description;
		this.type = type;
		this.receipt = receipt;
	}

	@Override
	public boolean noFieldEmpty() {
		if (this.amount == 0.0) {
			return false;
		} else if (this.description == null || this.description.trim().equals("") == true) {
			return false;
		} else if (this.type == null || this.type.trim().equals("") == true) {
			return false;
		} else if (this.receipt == null || this.receipt.length == 0) {
			return false;
		}
		return true;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CreateTicketDTO other = (CreateTicketDTO) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (!Arrays.equals(receipt, other.receipt))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
