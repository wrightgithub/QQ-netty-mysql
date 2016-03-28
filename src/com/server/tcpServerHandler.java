package com.server;

import static com.data.Information.*;

import java.sql.ResultSet;

import com.data.TcpMessage;
import com.data.User;
import com.mysql.Data;
import com.mysql.Modify_friends_info;
import com.mysql.Modify_login_info;
import com.mysql.Modify_user_info;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class tcpServerHandler extends ChannelInboundHandlerAdapter{
	private int boundid;
	private volatile boolean quitIsNormal=false;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		TcpMessage tcpmsg=(TcpMessage)msg;
		User user=tcpmsg.getUser();
		boundid=user.getAccount();
		Data data;
		switch(tcpmsg.getMode())
		{
		case registe:
			 data=new Data( new Modify_user_info());
			//客户端给server昵称和密码，server给他账号。 
			user.setAccount(data.getOneAccount());
			user.setInfo(success);
			System.out.println("用户注册\n"+user);
			data.Insert(user);
			ctx.writeAndFlush(user);//返回给客户端一个qq账号。
			break;
		case login:
			 data=new Data( new Modify_user_info());
			 /*验证登录*/
			 boolean ret=data.Verify_login(user);
			 ctx.writeAndFlush(user);//返回造作成功与否
			 /*成功则更新登陆列表*/
			 if(ret){
			     user.setStatus(1);
			     new Data(new Modify_login_info()).Insert(user);
			     new Data(new Modify_friends_info()).Update(user);
			 }
			 //显示处理信息
			 System.out.println("客户端["+user.getAccount()+"] 登录"+user.getInfo()+"  ip="+user.getIP());
			
			break;
		case addfriend:
			ResultSet result=new Data(new Modify_friends_info())
				.getRrsult("select * from friends_info where user_id="
							+user.getAccount()+" and friend_id="+user.get_Friend_qq());
			if(!result.next())
			{
				new Data(new Modify_friends_info()).Insert(user);
				System.out.println("添加"+user.get_Friend_qq()+"作为"+user.getAccount()+"的好友");
			}
			break;
		case delfriend:
			 result=new Data(new Modify_friends_info())
			.getRrsult("select * from friends_info where user_id="
						+user.getAccount()+" and friend_id="+user.get_Friend_qq());
			if(result.next())
			{
				new Data(new Modify_friends_info()).Delete(user);
				System.out.println("删除"+user.getAccount()+"的好友"+user.get_Friend_qq());
			}
			
			break;
		case showfriends:
			new Data(new Modify_friends_info()).SearchFriend(user);
			/*返回给客户端好友列表*/
			ctx.writeAndFlush(user);
			break;
		case quit:
			//正常离线状态由客户端维护
			new Data(new Modify_login_info()).Delete(user);
			new Data(new Modify_friends_info()).Update(user);
			quitIsNormal=true;
			System.out.println(user.getAccount()+":离线");
			break;
		default:
			break;
		}
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if(!quitIsNormal)
		{
			new Data(new Modify_login_info()).Delete(new User(boundid,0));
			new Data(new Modify_friends_info()).Update(new User(boundid,0));
			System.out.println(boundid+":异常掉线!");
		}
		else
			quitIsNormal=false;
	}
}
