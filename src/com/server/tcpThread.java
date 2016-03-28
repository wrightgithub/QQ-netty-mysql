package com.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class tcpThread implements Runnable
{
	int port;
	public tcpThread(int port)
	{
		this.port=port;
	}

	@Override
	public void run(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(); 
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); 
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() { 
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	ChannelPipeline pipeline= ch.pipeline();
                	pipeline.addLast(new ObjectEncoder());
                	pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, 
                										ClassResolvers.cacheDisabled(null)));
                	pipeline.addLast(new tcpServerHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          
             .childOption(ChannelOption.SO_KEEPALIVE, true); 

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); 
            System.out.println("tcp·þÎñÆ÷¿ªÆô.");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			e.printStackTrace();
  
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
		
	}
	

}