package com.munication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //保存客户端处理的线程
	public static void main(String[] args) throws IOException {	
	ExecutorService es=Executors.newFixedThreadPool(5);
		Vector<UserThread> vector=new Vector<>();
		
		//创建服务器端的Socket
		ServerSocket server=new ServerSocket(8888);
		System.out.println("服务器已启动，正在等待连接");
		while(true)
		{
			Socket socket=server.accept();
			UserThread user=new UserThread(socket, vector);
			es.execute(user);
		}
		
		
		
	}
	
}


//客户端处理的线程
class UserThread implements Runnable{

	private String name;//客户端的用户名称（唯一）
	private Socket socket;
    private	Vector<UserThread> vector;//客户端处理线程的集合
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private boolean flag=true;
	public UserThread(Socket socket, Vector<UserThread> vector) {
		
		this.socket = socket;
		this.vector = vector;
		vector.add(this);
	}

	@Override
	public void run() {
	System.out.println("客户端"+socket.getInetAddress().getHostAddress()+"已连接");
		try {
			ois=new ObjectInputStream(socket.getInputStream());
			oos=new ObjectOutputStream(socket.getOutputStream());
			
			
			while(flag){
				
					Message message=(Message)ois.readObject();
			        int type=message.getType();
			        switch(type)
			        {
			        case MessageType.TYPE__LOGIN:
			        	name=message.getFrom();
			        	message.setInfo("欢迎您：");
			        	oos.writeObject(message);
			        	
			        break;
			        
			        case MessageType.TYPE__SEND:
			        	String to = message.getTo();
			        	UserThread  ut;
			        	int size = vector.size();
			        	for(int i=0;i<size;i++){
			        		ut=vector.get(i);
			        		if(to.equals(ut.name)&&ut!=this){
			        			ut.oos.writeObject(message);
			        			break;
			        		}
			        	}
			        	break;
			        
			        }
			        
			}
			ois.close();
			oos.close();
			
		} catch (IOException |  ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}