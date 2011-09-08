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
 *  Created on : 2011-9-4, 下午6:30:42
 *  Author     : princehaku
 */

package net.techest.webqq.client.dialog;

import java.util.Observable;
import java.util.Observer;

import net.techest.util.Log4j;
import net.techest.webqq.WebQQSystem;
import net.techest.webqq.action.ActionException;
import net.techest.webqq.action.MessagePullThread;
import net.techest.webqq.action.WebQQLoginAction;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.bean.api.APIBase;
import net.techest.webqq.bean.api.GetFaceAPI;
import net.techest.webqq.bean.api.WEBQQAPIFacroty;
import net.techest.webqq.bean.api.WebQQAPIInterface;
import net.techest.webqq.client.MessageQueue;
import net.techest.webqq.client.MessageReceiver;
import net.techest.webqq.client.OnlineStatu;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.sso.AbstractLoginAction;
import net.techest.webqq.sso.LoginStatu;

/**每一个客户端于服务器交互均会创建一个这个
 *
 * 当有状态消息变更时 
 * 更新自身消息接收链
 * @author princehaku
 *
 */
public class ServerDialog extends Thread {
	
	private QQUser loginUser;
	/**消息获取器
	 * 获取服务器推送的消息
	 * 
	 * 并维持用户在线
	 */
	private MessagePullThread msgpulltask;
	/**登录处理器
	 * 
	 * @param user
	 */
	private AbstractLoginAction loginAction;
	
	private OnlineStatu onlineStatu;
	/**消息接收链
	 * 
	 */
	private MessageQueue messageQueue;
	
	public ServerDialog(QQUser user){
		this.messageQueue=new MessageQueue();
		this.loginUser=user;
		this.loginUser.setServerContext(this);
	}
	/**设置登录处理器
	 * 
	 * @param loginAction
	 */
	public void setLoginAction(AbstractLoginAction loginAction){
		 this.loginAction=loginAction;
		 this.loginAction.setUser(this.loginUser);
	}
	
	public synchronized void run(){
		try {
			//发起验证连接
			this.userAuth();			
			int i=0;
			//如果需要验证码 则等待
			if(getLoginAction().getStatu().equals(LoginStatu.NEED_VERIFY)){
				Log4j.getInstance().debug("需要验证码");
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Log4j.getInstance().debug("检测登录状态"+getLoginAction().getStatu());
			//如果是登录成功的  阻塞掉进程
			if(getLoginAction().getStatu().equals(LoginStatu.SUCCESS)){
				//
				this.setLiveStatu(OnlineStatu.ONLINE);
				this.msgpulltask=new MessagePullThread(this.loginUser);
				this.msgpulltask.start();
				try {
					//只要不是离线 就一直阻塞这个进程
					while(!this.onlineStatu.equals(OnlineStatu.OFFLINE)){
						this.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				Log4j.getInstance().debug("登录错误 "+getLoginAction().getStatu());
			}
			
		} catch (DialogException e) {
			e.printStackTrace();
		}
		
		//释放掉其他线程 并结束
		
	}
	
	public synchronized void setLiveStatu(OnlineStatu statu) {
		this.onlineStatu=statu;
		this.notify();
	}
	/**验证码请求
	 * 
	 */
	public  synchronized void inputVerify(String verifyCode){
		try {
			getLoginAction().loginVerify(verifyCode);
			getLoginAction().callBack();
		} catch (Exception e) {
			this.loginAction.setLoginStatu(LoginStatu.VERIFY_ERROR);
		}
		//唤醒进程
		this.notify();
	}
	/**登录处理
	 * 默认使用WebQQLoginAction
	 * @param loginAction
	 */
	public AbstractLoginAction getLoginAction(){
		 if(this.loginAction==null){
			 this.loginAction=new WebQQLoginAction();
			 this.loginAction.setUser(this.loginUser);
		 }
		 return this.loginAction;
	}
	/**验证用户登录
	 * @throws DialogException 
	 * 
	 */
	public void userAuth() throws DialogException{
		try {
			getLoginAction().doit();
		} catch (ActionException e) {
			Log4j.getInstance().warn("登录时错误");
			e.printStackTrace();
			throw new DialogException("登录时错误"+e.getMessage());
		}
	}

    private static class InstanceHolder {
        final static HttpClient instance = new HttpClient();
    }
    /**得到单例的http连接类
     * 整个会话会一直使用它
     * 
     * @return 
     */
    public HttpClient getHttpClient() {
        return InstanceHolder.instance;
    }
	
	public APIBase getWebQQAPI(String apiName){
		APIBase api = null;
		WEBQQAPIFacroty apifac=(WEBQQAPIFacroty) WebQQSystem.getInstance().getContext().getBean("apifactory");
		try {
			api = apifac.getApiByName(apiName);
			if(api==null){
				Log4j.getInstance().error("不存在API ："+apiName);
				return api;
			}
			((WebQQAPIInterface)api).init((WebQQUser) this.loginUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return api;
	}
	
	public MessageQueue getMessageQueue() {
		return messageQueue;
	}
	
	public QQUser getUser() {
		return this.loginUser;
	}
	
}
