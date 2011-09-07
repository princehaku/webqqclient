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
 *  Created on : 2011-9-5, 上午9:27:23
 *  Author     : princehaku
 */

package net.techest.webqq.action;



import net.sf.json.JSONObject;
import net.techest.webqq.bean.api.LoginAPI;
import net.techest.webqq.response.AbstractResponseHandle;
import net.techest.webqq.sso.Action;
import net.techest.webqq.sso.LoginStatu;

/**
 * WEBQQ登录 继承自SSOLOGIN 
 * 需要先执行了SSOLOGIN才能使用WEBQQ的登录 
 * 如果这里登录失败 
 * 则将状态回写
 * 
 * @author princehaku
 */
public class WebQQLoginAction extends SSOLoginAction implements Action{

	protected AbstractResponseHandle resp;
	
	public void setResponseHandle(AbstractResponseHandle resp) {
		this.resp=resp;
	}
	
	/**执行回调
	 * 
	 */
	@Override
	public void callBack(){
		if (this.getStatu().equals(LoginStatu.SUCCESS)) {
			try {
				this.loginToQQ();
			} catch (Exception e) {
				e.printStackTrace();
				this.setLoginStatu(LoginStatu.UIN_ERROR);
				//throw new ActionException("登录到QQ失败" + e.getMessage());
			}
		}

		//通知操作
		this.notifyResponseHandle();
		
	}
	
	public void notifyResponseHandle(){
		//回调
		if(resp!=null){
			this.resp.handle(this);
		}
	}
	/**
	 * 
	 * 需要先执行了SSOLOGIN才能使用WEBQQ的登录 如果这里登录失败 则将状态回写
	 * 
	 * @throws Exception
	 */
	public void loginToQQ() throws Exception {
		LoginAPI api= (LoginAPI) loginUser.getServerContext().getWebQQAPI("webqq_login");
		api.process();
		JSONObject jsonObject = api.getResponseJson();
		if(jsonObject.getLong("retcode")!=0){
			this.setLoginStatu(LoginStatu.CONNECT_ERROR);
		}
	}

}
