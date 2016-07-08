package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

public class Guest implements Serializable {
	private String gUsername;
	private String gPassword;
	public void setGUsername(String gUsername){
		this.gUsername=gUsername;
	}
	public String getGUsername(){
		return gUsername;
	}
	public void setGPassword(String gPassword){
		this.gPassword=gPassword;
	}
	public String getGPassword(){
		return gPassword;
	}
	public Guest(String gUsername){
		this.gUsername=gUsername;
	} 
	public Guest(String gUsername,String gPassword){
		this.gUsername=gUsername;
		this.gPassword=gPassword;
	} 
	public Guest(){}
}
