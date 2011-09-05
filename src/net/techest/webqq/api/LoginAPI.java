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
 *  Created on : 2011-9-5, 下午4:54:33
 *  Author     : princehaku
 */

package net.techest.webqq.api;

import net.sf.json.JSONObject;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

public class LoginAPI extends APIBase implements WebQQAPIInterface{

	private WebQQUser user;
	
	public LoginAPI() {
		super("webqq_login");
		this.setRequestType(REQ_TYPE.POST);
		this.setRequestURI("http://d.web2.qq.com/channel/login2");
	}

	public void init(WebQQUser user) {
		this.user=user;
		hc=user.getServerContext().getHttpClient();
		hc.setRequestProperty("Referer","http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");
		String ptwebqq=hc.getCookies().get("ptwebqq");
		String ptuserinfo=hc.getCookies().get("ptuserinfo");
		String param="r={\"status\":\"online\",\"ptwebqq\":\""+ptwebqq+"\",\"passwd_sig\":\"\",\"clientid\":\""+ptuserinfo+"\",\"psessionid\":null}&clientid="+ptuserinfo+"&psessionid=null";
		this.setRequestString(param);
	}

	@Override
	public void callback() {
		JSONObject jsonObject = this.getResponseJson();
		if(jsonObject.getLong("retcode")==0){
			jsonObject=jsonObject.getJSONObject("result");
			this.user.setClientid(hc.getCookies().get("ptuserinfo"));
			this.user.setPsessionid(jsonObject.getString("psessionid"));
		}
	}


}
