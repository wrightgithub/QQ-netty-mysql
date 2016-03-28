package com.client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import com.data.UdpMessage;

public class udpClientSendThread implements Runnable{
	private String ip;//服务器ip
	public udpClientSendThread(String ip)
	{
		this.ip=ip;
	}
	public void run()
	{
		while(true)
		{
			synchronized (udpClientSendThread.class) {
					try {
						udpClientSendThread.class.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
	       
	        	try {
					 DatagramSocket sendSocket = new DatagramSocket();  
		            InetSocketAddress ipaddr = new InetSocketAddress(ip, 8881);
		            // 创建发送类型的数据报：  
		            byte[]  bytes=UdpMessage.msg.toString().getBytes();
		            java.net.DatagramPacket sendPacket 
		            		= new java.net.DatagramPacket(bytes, bytes.length, ipaddr);
		            // 通过套接字发送数据：  
		            sendSocket.send(sendPacket);
		            sendSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
           
		}
		
	}

}
