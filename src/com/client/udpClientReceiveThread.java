package com.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class udpClientReceiveThread implements Runnable{
	private int port;
	public udpClientReceiveThread(int port)
	{
		this.port=port;
	}
	@Override
	public void run() {
	    EventLoopGroup group = new NioEventLoopGroup();
	    Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
    	        .handler(new ChannelInitializer<Channel>() {
    				protected void initChannel(Channel ch) throws Exception {
    					ChannelPipeline pipeline=ch.pipeline();
//                    	pipeline.addLast(new ObjectEncoder());
//                    	pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, 
//                    										ClassResolvers.cacheDisabled(null)));
                    	pipeline.addLast(new udpClientReceiveHandler());
    					
    				}

    			});
        try {
        	//客户端udp接收消息
        	bootstrap.bind(port).sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
