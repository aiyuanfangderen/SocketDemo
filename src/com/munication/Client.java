package com.munication;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

public class Client {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
        ExecutorService es = Executors.newSingleThreadExecutor();
		
		try {
			Socket socket = new Socket("localhost", 8888);
			System.out.println("服务器连接成功");
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			// 向服务器发送登陆信息
			System.out.println("请输入名称：");
			String name = input.nextLine();
			Message message = new Message(name, null, MessageType.TYPE__LOGIN, null);
			oos.writeObject(message);
			message = (Message) ois.readObject();
			System.out.println(message.getInfo() + message.getFrom());

			// 启动读取消息的线程
            es.execute(new ReadInfoThread(ois));
            //使用主线程发送消息
            boolean flag=true;
            while(flag){
            	message=new Message();
            	System.out.println("To:");
            	message.setTo(input.nextLine());        	
            	message.setFrom(name);
            	message.setType(MessageType.TYPE__SEND);
            	System.out.println("Info:");
            	message.setInfo(input.nextLine());
            	
            	oos.writeObject(message);
            	
            }
            
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

class ReadInfoThread implements Runnable {

	private ObjectInput in;
	private boolean flag = true;


	public ReadInfoThread(ObjectInputStream in) {
		this.in=in;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {
		try {
			while (flag) {

				Message message = (Message) in.readObject();
				System.out.println("[" + message.getFrom() + "]对我说" + message.getInfo());

			}
			
			if(in!=null){
				in.close();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
	
	
	





