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
 *  Created on : 2011-9-5, 下午10:33:56
 *  Author     : princehaku
 */

package net.techest.webqq.api;

import net.sf.json.JSONObject;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

public class CommonAPI  extends APIBase implements WebQQAPIInterface{

	protected WebQQUser user;
	
	public CommonAPI(String apiName){
		super(apiName);
		this.setRequestType(REQ_TYPE.POST);
	}

	@Override
	public void init(WebQQUser user) {
		this.user=user;
		hc=user.getServerContext().getHttpClient();
		hc.setRequestProperty("Referer","http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");
		String param="{\"clientid\":\""+user.getClientid()+"\",\"psessionid\":\""+user.getPsessionid()+"\",\"key\":0,\"ids\":[]}";
		JSONObject json=JSONObject.fromObject(param);
		this.setRequestJson(json);
	}
	
	@Override
	public void process() throws Exception{
		setRequestString("r="+getRequestJson().toString()+"&clientid="+user.getClientid()+"&psessionid="+user.getPsessionid());
		JSONObject tmp=this.requestJson;
		setRequestJson(null);
		super.process();
		setRequestJson(tmp);
		setRequestString("");
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		
	}

}
