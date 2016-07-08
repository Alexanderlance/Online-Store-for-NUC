package cn.edu.nuc.onlinestore.model;

import java.io.File;
import cn.edu.nuc.onlinestore.io.ObjectStream;

public class GoodsIdFetch {
	private final static String BASEPATH="d:/store/goodsid/";
	
	public static int getGoodId(){
		
		File file = new File(BASEPATH);
		//创建保存id的文件目录
		if( !file.exists()){
			file.mkdirs();
		}
		//创建保存id的文件
		File file1 = new File(BASEPATH + "GOODSID.txt");
		if( !file1.exists()){
			SelfIncreaseId goodsid =new SelfIncreaseId();
			int c_id =goodsid.getId();
			//id自增
			goodsid.getIncrease();
			ObjectStream.write( "/goodsid/GOODSID.txt",goodsid);
			return c_id;
		}
		SelfIncreaseId goodsid1 = ObjectStream.read  (SelfIncreaseId.class,  "/goodsid/GOODSID.txt");
		int c_id = goodsid1.getId();
		goodsid1.getIncrease();
		ObjectStream.write( "/goodsid/GOODSID.txt",goodsid1);
		return c_id;
	}
}