package com.mysql;

import java.sql.ResultSet;

import com.data.User;

public abstract class operation {
	public DBHelper op_db;
	public abstract void Insert(User user);
	public abstract void Delete(User user);
	public abstract void Update(User user);
	public abstract ResultSet Select(int qq,String desired);
}




