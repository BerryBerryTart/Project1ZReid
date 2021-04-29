package com.berry.DTO;

import com.berry.model.StatusEnum;

public class TicketStatusDTO implements DTO{
	private String status;

	public TicketStatusDTO() {}

	@Override
	public boolean noFieldEmpty() {
		if (this.status == null || this.status.trim().equals("") == true) {
			return false;
		}
		return true;
	}
	
	public boolean fieldsAreValid() {
		if (this.status.toUpperCase().equals(StatusEnum.COMPLETED.toString())) {
			return true;
		} else if (this.status.toUpperCase().equals(StatusEnum.REJECTED.toString())) {
			return true;
		}
		return false;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
