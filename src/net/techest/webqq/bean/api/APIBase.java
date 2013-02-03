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

import net.techest.util.Log4j;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.HttpClient.REQ_TYPE;

/**
 * API基类 所有的API必须 继承自它
 *
 * @author princehaku
 *
 */
public abstract class APIBase implements Cloneable {

    /**
     * 构造函数
     *
     *
     */
    public APIBase() {
    }

    /**
     * 构造函数
     *
     * @param apiName API名字
     */
    public APIBase(String apiName) {
        this.setApiName(apiName);
    }
    /**
     * API的名字
     *
     */
    protected String apiName = "";

    public String getRequestURI() {
        return requestURI;
    }
    /**
     * 请求的地址
     *
     */
    protected String requestURI;

    /**
     * 设置api的请求地址
     *
     * @param requestURI
     */
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }
    /**
     * 请求的方式 POST|GET 虽然可以用HEAD 但是一般不建议使用
     */
    protected REQ_TYPE requestType = REQ_TYPE.GET;

    /**
     * 设置请求方式
     *
     * @param requestType
     */
    public void setRequestType(REQ_TYPE requestType) {
        this.requestType = requestType;
    }
    /**
     * 请求的Post字符串
     *
     */
    protected String requestPostString;
    /**
     * 请求的GET字符串
     *
     */
    protected String requestGetString;
    /**
     * 返回的字符串
     *
     */
    protected String responseString;
    /**
     * 返回的原始数据
     *
     */
    protected byte[] content;
    /**
     * 底层连接器
     *
     */
    protected HttpClient hc;

    public void process() throws Exception {
        this.hc.setRequestType(requestType);
        //如果getString的串不是空 就设置到url后面
        if (this.getRequestGetString() != null && !this.getRequestGetString().equals("")) {
            this.hc.setUrl(this.requestURI + "?" + this.getRequestGetString());
        } else {
            this.hc.setUrl(this.requestURI);
        }
        //如果是POST类型的 设置好传送正文
        if (this.getRequestType().equals(REQ_TYPE.POST)) {
            this.hc.setPostString(this.getRequestPostString());
        }
        Log4j.getInstance().debug("Request : " + toString());
        content = this.hc.exec();
        responseString = new String(content);
        //Log4j.getInstance().debug("Response : " + responseString);
        this.callback();
    }

    public String toString() {
        return "\nGET: " + this.getRequestGetString()
                + "\nPOST:" + this.getRequestPostString();
    }

    /**
     * 回调函数
     *
     */
    abstract public void callback();

    public String getRequestPostString() {
        return requestPostString;
    }

    public void setRequestPostString(String requestPostString) {
        this.requestPostString = requestPostString;
    }

    public String getRequestGetString() {
        return requestGetString;
    }

    public void setRequestGetString(String requestGetString) {
        this.requestGetString = requestGetString;
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

    /**
     * 得到返回的原始数据
     *
     * @return
     */
    public byte[] getReturnRawContent() {
        return content;
    }

    /**
     * 得到连接器
     *
     * @return
     */
    public HttpClient getHc() {
        return hc;
    }

    /**
     * 设置连接器
     *
     * @param hc
     */
    public void setHc(HttpClient hc) {
        this.hc = hc;
    }

    /**
     * 设置api的名字
     *
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Object clone() {
        try {
            // call clone in Object.
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            return this;
        }
    }
}
