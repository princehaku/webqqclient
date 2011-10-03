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

package net.techest.webqq.bean.api;

import net.sf.json.JSONObject;
import net.techest.util.Log4j;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.HttpClient.REQ_TYPE;
import net.techest.webqq.net.QueryParam;

/**这个API类代表返回JSON对象的api
 * 
 * @author haku
 *
 */
public abstract class CommonJsonAPI  extends APIBase implements WebQQAPIInterface,APICallBack{

	protected WebQQUser user;

	/**请求的json
	 * 
	 */
	protected JSONObject requestJson;

	/**响应回的json
	 * 
	 */
	protected JSONObject responseJson;
	
	public CommonJsonAPI(){
		this.setRequestType(REQ_TYPE.POST);
	}
	
	public CommonJsonAPI(String apiName){
		super(apiName);
		this.setRequestType(REQ_TYPE.POST);
	}
	/**第一个参数是get上面的
	 * 第二个参数是post的json的r部分
	 * @param requestGet
	 * @param requestJson
	 */
	abstract public void initParam(QueryParam requestGet,JSONObject requestJson);
	

	public void init(WebQQUser user) {
		this.user=user;
		//这里创建的连接器是服务器会话的拷贝  这样才不会受到线程限制
		hc=(HttpClient) user.getServerContext().getHttpClient().clone();
		hc.setRequestProperty("Referer","http://web2.qq.com/");
		String param="{\"vfwebqq\":\""+user.getVfwebqq()+"\",\"clientid\":\""+user.getClientid()+"\",\"psessionid\":\""+user.getPsessionid()+"\",\"key\":0,\"ids\":[]}";
		setRequestJson(JSONObject.fromObject(param));
		//requestData=json;
	}
	
	@Override
	public void process() throws Exception{
		//在提交前设置参数
		QueryParam requestGet=new QueryParam();
		this.initParam(requestGet,getRequestJson());
		this.setRequestGetString(requestGet.toString()+"&clientid="+user.getClientid()+"&psessionid="+user.getPsessionid());
		if(getRequestPostString()==null){
			setRequestPostString("r="+getRequestJson().toString()+"&clientid="+user.getClientid()+"&psessionid="+user.getPsessionid());
		}else{
			setRequestPostString(getRequestPostString()+"&r="+getRequestJson().toString()+"&clientid="+user.getClientid()+"&psessionid="+user.getPsessionid());
		}
		super.process();
		Log4j.getInstance().debug("Response : " + responseString);
	}
	
	public JSONObject getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(JSONObject requestJson) {
		this.requestJson = requestJson;
	}
	
	public JSONObject getResponseJson(){
		JSONObject json=JSONObject.fromObject(this.getResponseString());
		return json;
	}

	@Override
	public void callback() {
		// TODO Auto-generated method stub
		
	}

}
