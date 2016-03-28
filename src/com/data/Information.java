package com.data;


public enum Information {
	registe_fail("注册失败!"),
	login_fail("登陆失败!"),
	add_fail("添加好友失败!"),
	delete_fail("删除好友失败!"),
	success("成功!"),
	null_op("未接收到服务器返回的信息");
	
	String info;
	Information(String info)
	{
		this.info=info;
	}
	public String toString()
	{
		return info;
	}
}
