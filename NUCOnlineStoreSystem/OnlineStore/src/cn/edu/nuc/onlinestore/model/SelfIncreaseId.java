package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

public class SelfIncreaseId implements Serializable{
	private int id=1;
	public void setId(int id) {
		this.id = id;
	}
	public int getId(){
		return id;
	}
	public void getIncrease(){
		id++ ;
	}
}
