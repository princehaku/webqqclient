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
 *  Created on : 2011-9-5, 上午11:56:21
 *  Author     : princehaku
 */

package net.techest.webqq.bean.api;

import net.sf.json.JSONObject;
import net.techest.util.Log4j;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

public abstract class APIBase implements Cloneable,APICallBack{
	/**构造函数
	 * 
	 * 
	 */
	public APIBase(){
	}
	/**构造函数
	 * 
	 * @param apiName API名字
	 */
	public APIBase(String apiName){
		this.setApiName(apiName);
	}
	/**API的名字
	 * 
	 */
	protected String apiName="";
	
	public String getRequestURI() {
		return requestURI;
	}
	/**请求的地址
	 * 
	 */
	protected String requestURI;
	/**设置api的请求地址
	 * 
	 * @param requestURI
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	/**请求的方式
	 * POST|GET
	 * 虽然可以用HEAD 但是一般不建议使用
	 */
	protected REQ_TYPE requestType=REQ_TYPE.GET;
	/**设置请求方式
	 * 
	 * @param requestType
	 */
	public void setRequestType(REQ_TYPE requestType) {
		this.requestType = requestType;
	}
	/**请求的json
	 * 
	 */
	protected JSONObject requestJson;
	/**响应回的json
	 * 
	 */
	protected JSONObject responseJson;
	/**请求的字符串
	 * 
	 */
	protected String requestString;
	/**返回的字符串
	 * 
	 */
	protected String responseString;
	/**返回的原始数据
	 * 
	 */
	protected byte[] content;
	/**底层连接器
	 * 
	 */
	protected HttpClient hc;
	
	public void process() throws Exception{
		this.hc.setUrl(this.requestURI+"?"+this.getPostString());
		this.hc.setRequestType(requestType);
		this.hc.setPostString(getPostString());
		content=this.hc.exec();
		responseString=new String(content);
		Log4j.getInstance().debug(responseString);
		this.callback();
	}
	
	abstract public void callback();
	
	/**得到传送字符串 优先使用json解析的
	 * 如果有json 则忽略requeststring
	 * 
	 * @return
	 */
	public String getPostString() {
		if(requestJson!=null){
			return requestJson.toString();
		}else{
			Log4j.getInstance().debug(this.requestString);
			return this.requestString;
		}
	}

	public void setRequestJson(JSONObject requestJson) {
		this.requestJson = requestJson;
	}

	public void setRequestString(String request) {
		this.requestString = request;
	}
	
	public JSONObject getResponseJson() {
		responseJson = JSONObject.fromObject(getResponseString());
		return responseJson;
	}
	
	public String getResponseString() {
		return responseString;
	}
	
	public String getApiName() {
		return this.apiName;
	}
	public REQ_TYPE getRequestType() {
		return requestType;
	}
	public JSONObject getRequestJson() {
		return requestJson;
	}
	public String getRequestString() {
		return requestString;
	}
	public byte[] getContent() {
		return content;
	}
	public HttpClient getHc() {
		return hc;
	}
	public void setHc(HttpClient  hc) {
		this.hc=hc;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	public Object clone() {
		try {
			// call clone in Object.
			return super.clone();
			} catch(CloneNotSupportedException e) {
			System.out.println("Cloning not allowed.");
			return this;
			} 
	}
}
