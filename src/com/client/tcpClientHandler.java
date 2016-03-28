package com.client;

import com.data.User;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class tcpClientHandler extends ChannelInboundHandlerAdapter{
	private static User rec_user;//该对象是保证是最新的对象
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		rec_user=(User)msg;
		System.out.println(rec_user.getInfo());
		Thread.sleep(100);//等对面先进入wait
		synchronized (menu_UI.class) {
			menu_UI.class.notifyAll();
		}
	}
	
	public  synchronized static User getUser()
	{
		return  rec_user;
	}
}
