package com.vince;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
//主线程用于监听客户端的连接，每次有连接成功，开启一个线程来处理该客户端的消息
public class MutilClientDemo {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner input=new Scanner(System.in);
		//创建一个Socket对象,指定要连接的服务器
		Socket socket=new Socket("localhost",6666);
        //获取socket的输入输出流
		PrintStream ps=new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
		
		BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		System.out.println("请输入：");
		String info = input.nextLine();
		
		ps.println(info);
		ps.flush();
		//读取服务器返回的数据
		info = br.readLine();
		System.out.println(info);
		
		ps.close();
		br.close();
	}

}
