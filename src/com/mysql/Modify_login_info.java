package com.mysql;

import java.sql.ResultSet;

import com.data.User;

public class Modify_login_info extends operation
{

	public void Insert(User user ){
		String sql="insert into login_info(user_ip,user_id,user_status) values('"+
					user.getIP()+"','"+user.getAccount()+"','"+user.getStatus()+"');";

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
	}
	public void Delete(User user)
	{
		String sql="delete from login_info where user_id="+user.getAccount();

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
		
	}
	@Override
	//登陆信息不需要更新，上线插入，下线删除就好。
	public void Update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSet Select(int qq,String desired)  {
		// TODO Auto-generated method stub
		String sql="select "+desired+" from login_info where user_id="+qq;
		 ResultSet result=op_db.getResultSet(sql);
		 return result;
	}
}