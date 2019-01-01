package com.vince;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClientDemo {

	public static void main(String[] args) throws UnknownHostException, IOException {
		//创建一个Socket对象
		Socket socket=new Socket("localhost",6666);
        //获取socket的输入输出流
		PrintStream ps=new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
		
		BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		ps.println("hello,my name is Bin");
		ps.flush();
		//读取服务器返回的数据
		String info = br.readLine();
		System.out.println(info);
		
		ps.close();
		br.close();
	}

}
