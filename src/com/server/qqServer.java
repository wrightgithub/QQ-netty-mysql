package com.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class qqServer {
	public static void main(String[] args)
	{
		ExecutorService exec=Executors.newCachedThreadPool();
		exec.execute(new tcpThread(8888));
		exec.execute(new udpThread(8881));
		
	}
	
}



