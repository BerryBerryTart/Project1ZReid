package com.berry.model;

public enum StatusEnum {

	PENDING(1, "PENDING"), 
	COMPLETED(2, "COMPLETED");
	
	private int index;
	private String text;
	
	private StatusEnum(final int index, final String text) {
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
