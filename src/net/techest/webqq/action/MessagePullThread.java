/*  Copyright 2010 princehaku
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  Created on : 2011-9-5, 下午12:22:45
 *  Author     : princehaku
 */

package net.techest.webqq.action;

import net.sf.json.JSONObject;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.api.PullDataAPI;
import net.techest.webqq.client.OnlineStatu;
import net.techest.webqq.sso.Action;

/**获取服务器返回给客户机的消息
 * 
 * @author princehaku
 *
 */
public class MessagePullThread extends Thread implements Action{
	/**登录的用户上下文
	 * 
	 */
	QQUser loginUser;
	/**抓取数据的api
	 * 
	 */
	PullDataAPI api;
	
	private boolean canEnd;

	public void setToEnd(boolean canEnd) {
		this.canEnd = canEnd;
	}

	public MessagePullThread(QQUser user){
		this.loginUser=user;
	}

	@Override
	public void run() {

		System.out.println("正在获取消息..");
		
		api =   (PullDataAPI)  loginUser.getServerContext().getWebQQAPI("webqq_pulldata");
		
		try {
			this.doit();
		} catch (ActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void doit() throws ActionException {
		while(!this.canEnd){
			try {
					api.process();
					JSONObject jsonObject = api.getResponseJson();
					
					System.out.println(jsonObject.toString());
//					
//					if(jsonObject.getLong("retcode")!=0&&jsonObject.getLong("retcode")!=102){
//						this.setToEnd(true);
//					}
					this.callBack();
			} catch (Exception e1) {
				//这里有可能是超时了  所以再根据messagecode细分一下进行处理
				//e1.getMessage().equals("connect timed out")||
				if(!(e1.getMessage().equals("Read timed out"))){
					e1.printStackTrace();
					this.setToEnd(true);
				}
			}
		}
		
		//设置服务窗口
		this.loginUser.getServerContext().setLiveStatu(OnlineStatu.OFFLINE);
	}
	/**推送到消息队列
	 * 
	 * @param jsonObject
	 */
	private void pushToMessage(JSONObject jsonObject) {
		this.loginUser.getServerContext().getMessageQueue().add(jsonObject);
	}


	@Override
	public void callBack() {
		pushToMessage(api.getResponseJson());
	}

}
