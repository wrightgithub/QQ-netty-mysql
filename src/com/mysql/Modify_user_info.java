package com.mysql;

import java.sql.ResultSet;
import com.data.User;

public class Modify_user_info extends operation
{
	public void Insert(User user ){
		String sql="INSERT INTO user_info VALUES ('"+
				user.getAccount()+"','"+user.getpassword()+"','"+user.getNickname()+"');";

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
	}
	public void Delete(User user)
	{
		String sql="delete from user_info where user_id="+user.getAccount();

		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
		
	}
	public void Update(User user)
	{
		String sql="update  user_info set "+
					"user_password='"+user.getpassword()+"' "+
					"user_nickname='"+user.getNickname()+"' "+
					"where user_id="+user.getAccount();
		
		if(!op_db.SetSql(sql))
		{
			System.err.println(sql);
			System.exit(0);
		}
		
	}
	@Override
	public ResultSet Select(int qq, String desired) {
		// TODO Auto-generated method stub
		String sql="select "+desired+" from user_info where user_id="+qq;
		 ResultSet result=op_db.getResultSet(sql);
		 return result;
	}

	
	
}