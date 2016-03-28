package com.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class udpThread implements Runnable
{
	int port;
	public udpThread(int port)
	{
		this.port=port;
	}
	@Override
	public void run() {
	    Bootstrap b = new Bootstrap();
	    EventLoopGroup group = new NioEventLoopGroup();
	    b.group(group)
	        .channel(NioDatagramChannel.class)
	        .option(ChannelOption.SO_BROADCAST, true)       
	        .handler(new ChannelInitializer<Channel>() {
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline pipeline=ch.pipeline();
//                	pipeline.addLast(new ObjectEncoder());
//                	pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, 
//                										ClassResolvers.cacheDisabled(null)));
                	pipeline.addLast(new udpServerHandler());
					
				}

			});

	        
			ChannelFuture future;
			try {
				future = b.bind(port).sync();
				 System.out.println("udp·þÎñÆ÷¿ªÆô.");
				 future.channel().closeFuture().await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	  }
		
	
}