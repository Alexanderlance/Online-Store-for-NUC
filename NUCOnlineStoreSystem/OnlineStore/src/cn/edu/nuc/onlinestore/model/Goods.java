package cn.edu.nuc.onlinestore.model;

import java.io.Serializable;

public class Goods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=1756643714827212865l;
	private int gId;
	private String gName;
	private String  gPrice;//gPrice:购买的产品单价
	private String gCount;//gCount:商品库存
	private int gQuantity;//gQuantity:购买的商品的数量
	private String gIntroduce;//gIntroduce:商品简介
	public Goods(int gId,String gName,String gPrice,String gCount,String gIntroduce){
		this.gId=gId;
		this.gName=gName;
		this.gPrice=gPrice;
		this.gCount=gCount;
		this.gIntroduce=gIntroduce;
	}
	public  Goods(String gName){
		this.gName=gName;
	}
	public  Goods(int gQuantity){
		this.gQuantity=gQuantity;
	}
	public Goods(int gId,String gName,String gPrice,String gCount,int gQuantity,String gIntroduce){
		this.gId=gId;
		this.gName=gName;
		this.gPrice=gPrice;
		this.gCount=gCount;
		this.gQuantity=gQuantity;
		this.gIntroduce=gIntroduce;
	}
/*	public  Goods(String gId){
		this.gId=gId;
	}*/
	public Goods(){}
	public void setGId(int gId){
		this.gId=gId;
	}
	public  int getGId(){
		return gId;
	}
	public void setGname(String gName){
		this.gName=gName;
	}
	public String getGName(){
		return gName;
	}
	public void setGPrice(String gPrice){
		this.gPrice=gPrice;
	}
	public String getGPrice(){
		return gPrice;
	}
	public void setGCount(String  gCount){
		this.gCount=gCount;
	}
	public String getGCount(){
		return gCount;
	}
	public void setGQuantity(int gQuantity){
		this.gQuantity=gQuantity;
	}
	public int getGQuantity(){
		return gQuantity;
	}
	public  String getGIntroduce(){
		return gIntroduce;
	}
	public void setGIntroduce(String gIntroduce){
		this.gIntroduce=gIntroduce;
	}
	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gId;
		return result;
	}*/
	@Override
	public String toString() {
		return "[ 商品编号："+gId+", 商品名称：" + gName + ", 商品单价：" + gPrice
				+ ", 商品库存：" + gCount + "]";
	}
}
