package com.data;

import java.io.Serializable;


/**
 * 协议格式:发送方qq 接收方qq 接收方的昵称 消息
 * @author xyy
 *
 */
public class UdpMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static UdpMessage msg;
	private User user;
	private String data;
	private User friend;
	public UdpMessage(User user,User friend,String data)
	{
		this.data=data;
		this.user=user;
		this.friend=friend;
	}
	public String getData()
	{
		return data;
	}
	public User getUser()
	{
		return user;
	}
	public String toString()
	{
		return user.getAccount()+" "+friend.getAccount()+" "+friend.getNickname()+" "+data;
	}
}
