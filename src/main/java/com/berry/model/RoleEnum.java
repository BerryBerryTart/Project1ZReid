package com.berry.model;

public enum RoleEnum {
	EMPLOYEE(1, "EMPLOYEE"), 
	MANAGER(2, "MANAGER");
	
	private int index;
	private String text;
	
	private RoleEnum(final int index, final String text) {
		this.text = text;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
    public String toString() {
        return text;
    }
	
}

