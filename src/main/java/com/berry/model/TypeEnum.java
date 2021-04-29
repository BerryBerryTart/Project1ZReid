package com.berry.model;

public enum TypeEnum {
	BUISNESS(1, "BUISNESS"),
	RELOCATION(2, "RELOCATION"),
	OTHER(3, "OTHER");
	
	private int index;
	private String text;
	
	private TypeEnum(final int index, final String text) {
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
