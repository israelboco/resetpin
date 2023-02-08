package com.he.resetpin.model;

public class Response {
	
	private int id;
	private int code;
	private String message;
	private Object data;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int created) {
		this.code = created;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object page) {
		this.data = page;
	}
	
}
