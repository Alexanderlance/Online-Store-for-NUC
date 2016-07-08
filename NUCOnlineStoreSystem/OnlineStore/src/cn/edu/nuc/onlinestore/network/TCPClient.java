package cn.edu.nuc.onlinestore.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	private Socket client=null;
	public TCPClient(){
		try {
			client=new Socket("127.0.0.1",9000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String send(String msg){
		PrintWriter writer=null;
		BufferedReader reader=null;
		try {
			writer=new PrintWriter(client.getOutputStream(),true);
			writer.println(msg);
		
			reader=new BufferedReader(new InputStreamReader(client.getInputStream()));
			String result=reader.readLine();
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if( writer != null) writer.close();
				if( client != null && client.isConnected() )
					client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	return "error";
	}
}
