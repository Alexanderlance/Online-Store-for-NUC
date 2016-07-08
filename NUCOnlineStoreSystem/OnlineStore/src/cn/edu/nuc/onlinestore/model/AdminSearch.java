package cn.edu.nuc.onlinestore.model;

import java.io.File;
import cn.edu.nuc.onlinestore.io.ObjectStream;

public class AdminSearch {
	private String text;
	public AdminSearch(String text){
		this.text=text;
	}
	public Goods findByName( ) {
		Goods goods =null;
		File f=new File("d:/store/goods/");
		String[] s=f.list();
		for(int i=0;i<s.length;i++){
		//写成文件形式的时候商品名称不存在的时候会出现FileNotFoundException，所以不能用File类型，	
		/*	File goodsFile = new File("d:/store/goods/"+text+".txt");
			if(goodsFile.getName().equals(text+".txt")){
				goods=ObjectStream.read(Goods.class,"/goods/"+text+".txt" );
			}*/
			if((text+".txt").equals(s[i])){
				goods=ObjectStream.read(Goods.class,"/goods/"+text+".txt" );
			}
		}
		return goods;
	}
}
