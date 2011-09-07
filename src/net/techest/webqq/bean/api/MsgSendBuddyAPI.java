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
 *  Created on : 2011-9-5, 上午11:49:19
 *  Author     : princehaku
 */

package net.techest.webqq.bean.api;

import net.sf.json.JSONObject;
import net.techest.webqq.net.QueryParam;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

/**消息发送API
 * 
 * @author haku
 *
 */
public class MsgSendBuddyAPI extends CommonAPI{
	
	private String txt;
	
	private long toWhom;
	
	public MsgSendBuddyAPI(){
		this.setRequestType(REQ_TYPE.POST);
		this.setRequestURI("http://d.web2.qq.com/channel/send_buddy_msg2");
	}

	@Override
	public void initParam(QueryParam requestGetParam,JSONObject json) {
		JSONObject newj=JSONObject.fromObject("{\"to\":"+this.getToWhom()+",\"face\":564,\"content\":\"[\\\""+this.getTxt()+"\\\"]\",\"msg_id\":123123123}");
		newj.put("clientid", json.get("clientid"));
		newj.put("psessionid", json.get("psessionid"));
	}

	public long getToWhom() {
		return toWhom;
	}

	public void setToWhom(long toWhom) {
		this.toWhom = toWhom;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
}
