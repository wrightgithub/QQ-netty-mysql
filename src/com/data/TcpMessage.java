package com.data;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TcpMessage implements Serializable{

	private User user;
	private Mode mode;
	public TcpMessage(Mode mode,User user)
	{
		this.mode=mode;
		this.user=user;
	}
	public Mode getMode()
	{
		return mode;
	}
	public User getUser()
	{
		return user;
	}
}
