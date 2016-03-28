package com.server;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.sql.ResultSet;

import com.mysql.Data;
import com.mysql.Modify_login_info;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
public class udpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String msg = new String(req);
        String[] ls=msg.split(" ",4);
        
        int friendid=Integer.parseInt(ls[1]);
        String data=ls[3];
        
        //判断他的朋友是否在线，在线则转发，否则不转发。
        ResultSet result=new Data(new Modify_login_info()).Select(friendid, "*");
        /*在线,转发消息*/
        if(result.next()){
        	//寻找对方ip;
        	String ip=result.getString(2);
            // 创建发送方的套接字
            DatagramSocket sendSocket = new DatagramSocket();  
            InetSocketAddress ipaddr = new InetSocketAddress(ip, 8882);
            // 创建发送类型的数据报：  
            java.net.DatagramPacket sendPacket 
            		= new java.net.DatagramPacket(req, req.length, ipaddr);
            // 通过套接字发送数据：  
            sendSocket.send(sendPacket);
            sendSocket.close();
            
            System.out.println(ls[0]+"==>"+ls[1]+":"+data);
        }
        else{
        	 System.out.println(ls[0]+"==>"+ls[1]+":"+ls[1]+" 离线无法转发消息");
        }
		
		
	}

}
