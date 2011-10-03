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
import net.techest.webqq.net.HttpClient.REQ_TYPE;
import net.techest.webqq.net.QueryParam;

/**获取头像
 * 
 * 参数Uin  MarkName
 * @author haku
 */
public class ChangeMarkNameAPI extends CommonJsonAPI{
	
	private String uin;
	
	private String markName;

	public ChangeMarkNameAPI(){
		this.setRequestType(REQ_TYPE.POST);
		this.setRequestURI("http://s.web2.qq.com/api/change_mark_name2");
	}

	@Override
	public void initParam(QueryParam requestGetParam,JSONObject requestJson) {
		setRequestPostString("tuin="+this.getUin()+"&markname="+this.getMarkName());
	}

	
	public String getMarkName() {
		return markName;
	}

	public void setMarkName(String markName) {
		this.markName = markName;
	}
	
	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}
	
}
