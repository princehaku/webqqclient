�������������?
������javaд��webqq�Ľӿ�ʵ��

����
	QQUser u = new WebQQUser();
	u.setUin("389663316");
	u.setPassword("!52dearesta3");
	// ��һ���µķ������Ự
	ServerDialog sd = new ServerDialog(u);
	sd.setLoginAction(new WebQQLoginAction());
	// ���õ�¼�ص�
	((WebQQLoginAction) sd.getLoginAction()).setResponseHandle(new WebQQLoginResponseHandle(sd));
	sd.start();
	
	while (1 == 1) {
		JSONObject rjson = sd.getMessageQueue().pull();
		System.out.println("��Ϣ����" + rjson);
	}

Ŀǰ֧�ֵĵ��ýӿڰ���

	��ȡ��Ϣ
	<entry key="webqq_pulldata">
		<ref local="apimsgpull"/>
	</entry>
	��ȡ�����б�
	<entry key="webqq_get_friends">
		<ref local="apigetfriends"/>
	</entry>
	��ȡ���ߺ���
	<entry key="webqq_get_online_friends">
		<ref local="apigetonlinefriends"/>
	</entry>
	��ȡ���ѷ���
	<entry key="webqq_get_groups">
		<ref local="apigetgroups"/>
	</entry>
	�����ѷ�����Ϣ
	<entry key="webqq_send_msg_buddy">
		<ref local="apisendmsgbuddy"/>
	</entry>
	��������״̬
	<entry key="webqq_change_statu">
		<ref local="apichangestatu"/>
	</entry>
	��ȡ���ѵ���Ϣ
	<entry key="webqq_get_friend_info">
		<ref local="apigetfriendinfo"/>
	</entry>
	��ȡ�����ǳ�
	<entry key="webqq_get_friend_lnick">
		<ref local="apigetfriendlnick"/>
	</entry>
	��ȡ����ͷ��
	<entry key="webqq_get_face">
		<ref local="apigetface"/>
	</entry>