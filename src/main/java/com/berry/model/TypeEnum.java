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
	
	public static TypeEnum getEnum(String type) {
		TypeEnum returnType = null;
		switch(type.toLowerCase()) {
			case "buisness":
				returnType = BUISNESS;
				break;
			case "relocation":
				returnType = RELOCATION;
				break;
			default:
				return OTHER;
		}		
		return returnType;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
    public String toString() {
        return text;
    }
	
}
