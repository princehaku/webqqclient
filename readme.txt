这是嘛玩意儿呢?
这是用java写的webqq的接口实现

例子
	QQUser u = new WebQQUser();
	u.setUin("389663316");
	u.setPassword("!52dearesta3");
	// 打开一个新的服务器会话
	ServerDialog sd = new ServerDialog(u);
	sd.setLoginAction(new WebQQLoginAction());
	// 设置登录回调
	((WebQQLoginAction) sd.getLoginAction()).setResponseHandle(new WebQQLoginResponseHandle(sd));
	sd.start();
	
	while (1 == 1) {
		JSONObject rjson = sd.getMessageQueue().pull();
		System.out.println("消息到来" + rjson);
	}

目前支持的调用接口包括

	获取消息
	<entry key="webqq_pulldata">
		<ref local="apimsgpull"/>
	</entry>
	获取好友列表
	<entry key="webqq_get_friends">
		<ref local="apigetfriends"/>
	</entry>
	获取在线好友
	<entry key="webqq_get_online_friends">
		<ref local="apigetonlinefriends"/>
	</entry>
	获取好友分组
	<entry key="webqq_get_groups">
		<ref local="apigetgroups"/>
	</entry>
	给好友发送消息
	<entry key="webqq_send_msg_buddy">
		<ref local="apisendmsgbuddy"/>
	</entry>
	更改在线状态
	<entry key="webqq_change_statu">
		<ref local="apichangestatu"/>
	</entry>
	获取好友的信息
	<entry key="webqq_get_friend_info">
		<ref local="apigetfriendinfo"/>
	</entry>
	获取好友昵称
	<entry key="webqq_get_friend_lnick">
		<ref local="apigetfriendlnick"/>
	</entry>
	获取好友头像
	<entry key="webqq_get_face">
		<ref local="apigetface"/>
	</entry>