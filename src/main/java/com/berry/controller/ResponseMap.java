package com.berry.controller;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {
	
	private ResponseMap() {}
	
	public static Map<String, String> getResMap(String key, String message){
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put(key, message);
		return resMap;
	}
}
