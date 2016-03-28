package com.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.data.User;


public class Modify_friends_info extends operation
{
	public void Insert(User user ){
	
		if(!SelectFriend(user))
		{
			System.err.println("没有这个人");
			return;
		}	
		String sql="insert into friends_info(friend_id,user_id,friend_status) values('"+
					user.get_Friend_qq()+"','"+user.getAccount()+"','"+getfriendStatus(user)+"');";
		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
	}
	public void Delete(User user)
	{
		String sql="delete from friends_info where user_id="+user.getAccount()+
				" and "+"friend_id="+user.get_Friend_qq();

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
		
	}
	@Override
	//需要更新朋友状态  更新朋友是该user的所有行的状态
	public void Update(User user) {
		// TODO Auto-generated method stub
		String sql="update  friends_info set "+
				"friend_status='"+user.getStatus()+"'" +"where friend_id="+user.getAccount();

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
	}
	
	/*
	 * 找朋友，判断朋友是否存在
	 */
	public boolean SelectFriend(User user)
	{
		
		 ResultSet result= new Data(new Modify_user_info()).Select(user.get_Friend_qq(), "*");
		 try {
			if(result.next()) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return false;
	}
	/*
	 * 得到朋友状态
	 */
	public int getfriendStatus(User user)
	{
		ResultSet result=new Data( new Modify_login_info()).Select(user.get_Friend_qq(), "user_status");
		int status=0;
		try {
			if(result.next()){status=result.getInt(1);}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public ResultSet Select(int qq,String desired)  {
		// TODO Auto-generated method stub
		 String sql="select "+desired+" from friends_info where user_id="+qq;
		 ResultSet result=op_db.getResultSet(sql);
		 return result;
	}
}