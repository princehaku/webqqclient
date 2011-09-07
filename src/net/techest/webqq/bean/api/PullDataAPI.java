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
 *  Created on : 2011-9-5, 下午12:14:47
 *  Author     : princehaku
 */

package net.techest.webqq.bean.api;


import net.sf.json.JSONObject;
import net.techest.webqq.net.QueryParam;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

/**从服务器拉取消息
 * 
 * @author haku
 *
 */
public class PullDataAPI extends CommonAPI{

	public PullDataAPI() {
		this.setRequestType(REQ_TYPE.POST);
		this.setRequestURI("http://d.web2.qq.com/channel/poll2");
	}

	@Override
	public void initParam(QueryParam requestGetParam,JSONObject requestJson) {
		this.getHc().setResponseTimeOut(120000);
	}
}
