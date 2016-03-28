package com.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class tcpClientThread implements Runnable{

	int port;
	String ip;
	public tcpClientThread(int port,String ip)
	{
		this.port=port;
		this.ip=ip;
	}

	@Override
	public void run(){
		  EventLoopGroup workerGroup = new NioEventLoopGroup();

	        try {
	            Bootstrap b = new Bootstrap(); 
	            b.group(workerGroup); 
	            b.channel(NioSocketChannel.class); 
	            b.option(ChannelOption.SO_KEEPALIVE, true);
	            b.handler(new ChannelInitializer<SocketChannel>() {
	                @Override
	                public void initChannel(SocketChannel ch) throws Exception {
	                	ChannelPipeline pipeline=ch.pipeline();
	                	pipeline.addLast(new ObjectEncoder());
	                	pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, 
	                						ClassResolvers.cacheDisabled(null)));
	                	pipeline.addLast(new tcpClientHandler());
	                }
	            });
	            
	            // Start the client.
	            ChannelFuture f = b.connect(ip, port).sync();
	            new menu_UI().MainUI(f.channel()); 
	            //f.channel().writeAndFlush();
	            // Wait until the connection is closed.
	           // f.channel().closeFuture().sync();
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            workerGroup.shutdownGracefully();
	        }
	    }

}
