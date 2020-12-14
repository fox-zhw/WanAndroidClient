package com.example.wanandroid.data.remote.exception;

/**
 * @author 52512
 * @date 2020/12/13
 */
public class ServerException extends Exception {
	
	private int code;
	
	public ServerException(String message) {
		super(message);
	}
	
	public ServerException(String message, int code) {
		super(message);
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
}
