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

import net.techest.webqq.net.HttpClient;
import net.techest.webqq.sso.LoginStatu;
import net.techest.webqq.sso.SSOLoginAction;

public class WebQQLoginAction extends SSOLoginAction{
	
	public void auth() throws ActionException {
		super.auth();
		if(this.getStatu().equals(LoginStatu.SUCCESS)){
			try {
				this.loginToQQ();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ActionException("登录到QQ失败"+e.getMessage());
			}
		}
	}
	
	public void loginToQQ() throws Exception{
		HttpClient hc=this.getUser().getServerContext().getHttpClient();
		//需要构造
		hc.setUrl("http://d.web2.qq.com/channel/login2");
		hc.setRequestProperty("Referer","http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");
		hc.setPostString("r=%7B%22status%22%3A%22online%22%2C%22ptwebqq%22%3A%2241152f95c3718abf3a15824ac05608cfb237ad66bbfcc7a93e399afe2db78b3a%22%2C%22passwd_sig%22%3A%22%22%2C%22clientid%22%3A%2238359557%22%2C%22psessionid%22%3Anull%7D&clientid=38359557&psessionid=null");
		byte[] content=hc.exec();
		System.out.println(new String(content));
		
		
		
	}
	
}
