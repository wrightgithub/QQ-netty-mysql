package com.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class qqClient {
	public static void main(String[] args)
	{
		ExecutorService exec=Executors.newCachedThreadPool();
		exec.execute(new tcpClientThread(8888,"192.168.68.1"));
		exec.execute(new udpClientReceiveThread(8882));
		exec.execute(new udpClientSendThread("192.168.68.1"));
	}
}
