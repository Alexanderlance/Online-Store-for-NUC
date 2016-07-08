package cn.edu.nuc.onlinestore.model;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import cn.edu.nuc.onlinestore.io.ObjectStream;

public class Cart implements Serializable {
	private static final long serialVersionUID = -5523647580332725112L;
	private Map<Goods, Integer> maps = new HashMap<>();
	//购物车商品总价格
	private int totalPrice = 0;
	//购物车商品总价格
	private int totalQuantity = 0;
	private int price=0;
	private int singleQuantity=0;
	
	/**
	 * 添加商品到购物车
	 * @param goods
	 * @param quantity
	 * @return 返回该商品在购物车内总数量
	 */
	public int add(Goods goods, int quantity){
	//	goods=ObjectStream.read(Goods.class, "/mapgoods/"+goods.getGName()+".txt");
		if( maps.get( goods ) == null ){
			//如果maps 里没有该商品,直接添加
			goods=new Goods(goods.getGId(),goods.getGName(),goods.getGPrice(),goods.getGCount(),quantity,goods.getGIntroduce());
			ObjectStream.write("/mapgoods/"+goods.getGName()+".txt", goods);
			maps.put(goods,quantity);
			
		} else {
			//如果maps 里已存在当前添加商品,则累加商品数量
			goods=new Goods(goods.getGId(),goods.getGName(),goods.getGPrice(),goods.getGCount(),goods.getGQuantity()+quantity,goods.getGIntroduce());
//			goods.setGQuantity(goods.getGQuantity()+quantity);
//			goods=new Goods(quantity);
			int g=goods.getGQuantity();
			File file = new File("d:/store/mapgoods/" +goods.getGName()+".txt");
			file.delete();
			ObjectStream.write("/mapgoods/"+goods.getGName()+".txt", goods);
			maps.put(goods, g);
		}
		//添加商品后调用计算购物车总金额、总价格方法
		return this.total();
		//返回该商品在购物车内总数量	 
	}
	
/*	*//**
	 * 将某件商品从购物车内移除
	 * @param goods 商品对象
	 * @return 返回移除数量
	 *//*
	public int remove(Goods goods){
		
		int q = maps.remove( goods );
		
		//移除商品后调用计算购物车总金额、总价格方法
		this.total();
		
		return q;
	}
	
	*//**
	 * 根据商品id将商品从购物车内移除
	 * @param gid
	 * @return
	 *//*
	public int remove(String gName){
		Goods goods = new Goods();
		goods.setGname(gName);
		
		return this.remove(goods);
	}*/
	
	
	/**
	 * 计算购物车总金额、总数量方法
	 */
	private int total(){
		//先将总金额、总数量归零
		this.totalPrice = 0;
		this.totalQuantity = 0;
		this.price=0;
		this.singleQuantity=0;
		//遍历购物车 maps 重新计算购物车总价格、总数量
		//代码省略....
		Set<Goods> s1=maps.keySet();
		for(Goods g:s1){
			Integer q =maps.get( g );
			//this.totalPrice += Double.parseDouble(g.getGPrice()) * q;	
			this.totalQuantity += q;
		}
		return this.totalQuantity ;
	}

	/**
	 * 返回购物车商品总金额(此处不应提供set方法,因为修改购物车商品金额不能由外面修改)
	 * @return
	 */
	public int getTotalPrice() {
		return totalPrice;
	}
	/**
	 * 返回购物车商品总数量(此处不应提供set方法,因为修改购物车商品数量不能由外面修改)
	 * @return
	 */
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public int getSingleQuantity() {
		return singleQuantity;
	}
	
	

}
