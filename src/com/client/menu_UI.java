package com.client;

import java.io.IOException;
import java.util.Scanner;

import com.data.Information;
import com.data.Mode;
import com.data.TcpMessage;
import com.data.UdpMessage;
import com.data.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class menu_UI {
	private Scanner in=new Scanner(System.in);
	//public static menu_UI menu=new menu_UI();
	public int Int_input(Scanner input)
	{
		int qq=0;
		try {
			qq=input.nextInt();
		} catch (Exception e) {
			System.out.println("输入有误");
			String string=input.next();
			return Integer.MIN_VALUE;
		}
		return qq;
	}
	public void MainUI(Channel ch) throws InterruptedException
	{
		
		 while(true)
         {
     		System.out.println("请选择操作：\n1.注册  \n2.登陆");
     		int num;
     		while((num=Int_input(in))==Integer.MIN_VALUE)
     		{
     			System.out.println("请选择操作：\n1.注册  \n2.登陆");
     		}
     		switch (num) {
     		case 1://注册
     			System.out.println("----------------------------");
     			System.out.println("请输入昵称:");
     			String nickname=in.next();
     			System.out.println("请输入密码:");
     			String password=in.next();
     			User user=new User(nickname,password);
     			ch.writeAndFlush(new TcpMessage(Mode.registe, user));
     			synchronized (menu_UI.class) {
     				menu_UI.class.wait();
				}
     			System.out.println("分配到的账号："+tcpClientHandler.getUser().getAccount());
     			break;
     		case 2:
     			System.out.println("输入账号:");
				int qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				
     			System.out.println("输入密码:");
     			password=in.next();
     			user=new User(qq,password);
     			ch.writeAndFlush(new TcpMessage(Mode.login, user));
     			synchronized (menu_UI.class) {
     				menu_UI.class.wait();
				}
     			if(tcpClientHandler.getUser().getInfo()==Information.success)
     			{
     				After_login(ch);
     			}
     			break;
     		default:
     			break;
     		}
         }
	}
	public void After_login(Channel ch) throws InterruptedException
	{
		User user;
		System.out.println("进入qq界面....................................");
		while(true)
		{
			System.out.println("请选择以下操作:\n1.添加好友\n2.删除好友\n3.显示好友列表\n4.退出登录");
			switch (in.nextInt()) {
			case 1:
				user=new tcpClientHandler().getUser();
				System.out.print("请输入朋友的qq号:");
				int qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				user.addFriend(qq);
				ch.writeAndFlush(new TcpMessage(Mode.addfriend, user));
				break;
			case 2:
				user=new tcpClientHandler().getUser();
				System.out.print("请输入朋友的qq号:");
				qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				user.delFriend(qq);
				ch.writeAndFlush(new TcpMessage(Mode.delfriend, user));
				break;
			case 3:
				user=tcpClientHandler.getUser();
				ch.writeAndFlush(new TcpMessage(Mode.showfriends, user));
				synchronized (menu_UI.class) {
					menu_UI.class.wait();
				}
				/*显示并选择好友*/
				if(user.getInfo()==Information.success)
				{
					user=tcpClientHandler.getUser();
					System.out.println("请选择需要聊天的对象:--------------(-1返回上一层)");
					int num=user.FriendList.size();
					for(int i=0;i<num;i++)
					{
						System.out.println("<"+i+">"+user.FriendList.get(i).UserShow());
					}
				}
				if(user.FriendList.size()==0)
				{
					System.out.println("没有好友");
					break;
				}
				int index = 0;
				if((index=in.nextInt())>=user.FriendList.size())
				{
					System.out.println("选择错误：");
					break;
				}
				if(index==-1)
					break;
				
				while(true){
					System.out.println("请输入聊天内容:-----------<END is 返回上一层>");
					String data=in.next();
					if(data.equals("END"))break;
					//构造udp包
					UdpMessage.msg=new UdpMessage(user,user.FriendList.get(index), data);
					synchronized (udpClientSendThread.class) {
						udpClientSendThread.class.notifyAll();//唤醒udp发送聊天数据
					}
					
				}
				break;
			case 4:
				user=tcpClientHandler.getUser();
				user.setStatus(0);
     			ChannelFuture f=ch.writeAndFlush(new TcpMessage(Mode.quit, user));
				f.addListener(ChannelFutureListener.CLOSE);
				System.exit(0);
				break;

			default:
				break;
			}
		}
	}
}
