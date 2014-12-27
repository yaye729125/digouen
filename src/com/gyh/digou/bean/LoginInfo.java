package com.gyh.digou.bean;

import java.io.Serializable;


public class LoginInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4012761770753967041L;
	public  String ErrNum;
	public String ErrMsg;
	public LoginData data;
	public String getErrNum() {
		return ErrNum;
	}
	public void setErrNum(String errNum) {
		ErrNum = errNum;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	public LoginData getData() {
		return data;
	}
	public void setData(LoginData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		
		return ErrNum+","+ErrMsg+","+data;
	}
	
}
