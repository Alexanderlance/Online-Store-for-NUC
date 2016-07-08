package cn.edu.nuc.onlinestore.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
	private ServerSocket server=null;
	public TCPServer(){
		try {
			server=new ServerSocket(9000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		Socket client=null;
		BufferedReader reader=null;
		PrintWriter writer=null;
		while(true){
			try {
				client=server.accept();
				reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
				String msg=reader.readLine();
				String m[]=msg.split("@@");
				/*System.out.println("msg:" + msg);
				System.err.println(m.length);*/
				String result="error";
				switch( m[0] ){
					case "login":
						if(m.length==3){
							if(m[1].equals("")&&!m[2].equals(null)){
								result="NoUsername";
							}else if(!m[2].equals(null)&&!m[1].equals("@@")){
								File file=new File(getUserInfo(m[1]));
								if(!file.exists()){
									//ObjectStream.write("/admin/"+username+".txt", a1);
									result="NoRegistInfo";
								}else {
									result = "LoginCheck";
								}
							}
						}else if(m.length==2){
				
								result="NoPassword";
							
						}else if(m.length==1){
//							if(m[1].equals(null)&&m[2].equals(null)){
								result="NoUserPass";
						}
						break;
					case "list":
						result="ListAllGoodsInfo";
						break;
					case "search":
						if(m.length>1){
							if(!(m[1].equals(null))){
							result="ShowSearchInfo";
						    }
						}else{
								result="NoSearchInfo";
							}
						break;
					case "searchgoodsall":
						result="ShowGoodsText";
						break;
					case "addcart":
						result="addgoods";
						break;
				}
				writer = new PrintWriter(client.getOutputStream(), true);
				writer.println( result );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(writer!=null){
					writer.close();
				}
				if(reader!=null){
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	public static String getUserInfo( String username){
		return "d:/store/user/" +username+ ".txt";
	}
}
