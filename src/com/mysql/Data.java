package com.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;


import  com.data.Information;
import com.data.User;

public class Data  
{
	public operation op;
	private  static DBHelper db=new DBHelper("qq","1234567890");
	public Data(){}
	public Data(operation op)
	{
		this.op=op;
		op.op_db=db;
	}
	public Data(operation op,DBHelper dbHelper)
	{
		this.op=op;
		op.op_db=dbHelper;
	}
	public void Insert(User user) {
		op.Insert(user);		
	}

	public void Delete(User user) {
		op.Delete(user);	
	}

	public void Update(User user) {
		op.Update(user);
	}
	public  ResultSet Select(int qq,String desired)
	{
		return op.Select(qq, desired);
	}
	public ResultSet getRrsult(String sql)
	{
		return db.getResultSet(sql);
	}
	/**
	 *获取一个账号
	 * 该函数需要用user_info来初始化operation
	 * @return
	 */
	public int getOneAccount()
	{
		Random rand=new Random();
		while(true)
		{
			
			int qq=rand.nextInt(99999999);
			ResultSet result=op.Select(qq, "user_id");
			try {
				if(!result.next()) return qq;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 验证登陆
	 * 该函数需要用user_info来初始化operation
	 * @param 
	 */
	public boolean Verify_login(User user)
	{
		
		try {
			ResultSet result=op.Select(user.getAccount(), "*");
			
			if(result.next())
			{
				if(result.getInt(1)==user.getAccount()
				  &&result.getString(2).equals(user.getpassword()))
				{
					user.setInfo(Information.success);
					return true;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setInfo(Information.login_fail);
		return false;
		
	}
	/**
	 * 更新登录
	 */
	public void  Update_Login(User user)
	{
		ResultSet result=op.Select(user.getAccount(), "*");
		try {
			if(result.next()) {
				user.setInfo(Information.login_fail);
			}
			else {
				op.Insert(user);
				user.setInfo(Information.success);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜索某个user的朋友
	 */
	public void SearchFriend(User user)
	{
		//先把好友列表清空
		user.FriendList.clear();
		/**找出所有的好友*/
		User friend;
		ResultSet result=op.Select(user.getAccount(), "*");
		try {
			while(result.next())
			{
				//获取账号,状态
				friend=new User(result.getInt(2),result.getInt(4));
				//获取并设置好友昵称
				ResultSet userSet=new Data(new Modify_user_info(),new DBHelper("qq","1234567890"))
										.op.Select(result.getInt(2), "user_nickname");
				if(userSet.next())
				  friend.setNickname(userSet.getString(1));
				
				user.FriendList.add(friend);
				//System.out.println("friend:"+friend);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	public static void main(String[] args)
//	{
//		Data data=new Data();
//		data=new Data(new Modify_user_info());
//		User user=new User(3333, "xxfghj");
//		user.setStatus(1);
//		data.Insert(user);
//		data=new Data(new Modify_login_info());
//		data.Insert(user);
//		
//		user.addFriend(123);
//		data=new Data(new Modify_friends_info());
//		data.Insert(user);
//		data.Delete(user);
//	}
}
