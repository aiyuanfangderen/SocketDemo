package com.vince;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 处理多个客户端
 * @author 陈回
 *
 */
public class MutilServerDemo {
  public static void main(String[] args) {
	
	  ExecutorService es=Executors.newFixedThreadPool(3);
	  
	  try {
		ServerSocket server=new ServerSocket(6666);
		System.out.println("服务器已启动,正在等待连接");
		
		while(true)
		{
			Socket s=server.accept();
			System.out.println(s.getInetAddress().getHostAddress());
			es.execute(new UserThread(s));
			
		}
		
	} catch (IOException e) {
		
		e.printStackTrace();
	}
}
}

//用来处理客户端请求的线程任务
class UserThread implements Runnable{

	private Socket s;
	
    public UserThread(Socket s) {
	this.s=s;
	}
	
	@Override
	public void run() {
	try {
		BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintStream ps=new PrintStream(new BufferedOutputStream(s.getOutputStream()));
		String info=br.readLine();
		System.out.println(info);
		ps.println("echo"+info);
		ps.flush();
		ps.close();
		br.close();
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

	
}


