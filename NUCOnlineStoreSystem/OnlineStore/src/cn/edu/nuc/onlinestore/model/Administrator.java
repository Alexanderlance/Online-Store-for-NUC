package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

public class Administrator implements Serializable {
	private String aUsername;
	private String aPassword;
	public void setAUsername(String aUsername){
		this.aUsername=aUsername;
	}
	public String getAUsername(){
		return aUsername;
	}
	public void setAPassword(String aPassword){
		this.aPassword=aPassword;
	}
	public String getAPassword(){
		return aPassword;
	}
	public Administrator(String aUsername, String aPassword){
		this.aUsername=aUsername;
		this.aPassword=aPassword;
	}
}
