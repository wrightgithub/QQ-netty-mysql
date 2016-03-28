package com.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class udpClientReceiveHandler  extends SimpleChannelInboundHandler<DatagramPacket>{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		
		ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String msg = new String(req);
        String[] ls=msg.split(" ",4);
        //System.out.println(msg);
		System.out.println("["+ls[2]+"]:"+ls[3]);
		
	}

}
