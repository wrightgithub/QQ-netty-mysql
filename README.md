# QQ-netty-mysql
qq模拟程序

此程序使用了netty4.1,mysql;  相应的jar在lib下
服务器架构：tcp+udp+mysql
            tcp:用于和客户端进行添加/删除好友，登陆，注册等操作的链接。
            udp：用于转发消息，因为udp是无连接的，所以在高并发时转发速度快

客户端架构：tcp+udp            
            tcp:用于和服务器进行添加/删除好友，登陆，注册等操作的链接。
            udp：发送和接收消息。

服务器后台maysql架构：
表1：user_info(user_id,user_password,user_nickname)
表2：friends_info(fnum,friend_id,user_id,friend_status)
表3：login_info(lnum,user_ip,user_id,user_status)


详细设计参考自：http://blog.csdn.net/zwx19921215/article/details/21392019
