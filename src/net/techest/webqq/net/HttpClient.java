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
 *  Created on : 2010-11-16, 23:19:19
 *  Author     : princehaku
 */
package net.techest.webqq.net;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.techest.util.Log4j;

/**
 * HTTP连接类 带cookie 可以使用GET和POST
 * 
 * @author princehaku
 */
public class HttpClient implements Cloneable{

	public enum REQ_TYPE {

		POST, GET, HEAD
	};

	private HttpURLConnection httpConn = null;
	
	private URL turl;
	
	private REQ_TYPE requestType;
	
	private String postString;

	private Map<String, List<String>> responseHeader;

	private String responseMessage;

	Cookies cookies = new Cookies();

	QueryParam requestParam = new QueryParam();

	QueryParam postParams = new QueryParam();

	private int responseTimerOut=30000;

	public URL getUrl() {
		return this.turl;
	}

	/**
	 * 设置请求的url
	 * 
	 * @param url
	 * @throws MalformedURLException
	 */
	public void setUrl(String url) {
		try {
			turl = new URL(url);
		} catch (MalformedURLException e) {
			Log4j.getInstance().error("错误的URL格式" + e.getMessage());
			return;
		}
		Log4j.getInstance().debug("URL SET :" + url);
	}

	/**
	 * 设置请求类型
	 * 
	 * @param req_type
	 */
	public void setRequestType(REQ_TYPE req_type) {
		this.requestType = req_type;
	}

	public HttpClient() {

	}

	/**
	 * 清空cookie
	 * 
	 */
	public void clearCookie() {
		cookies.clear();
	}

	public void setCookie(String key,String value) {
		cookies.put(key, value);
	}

	public String getCookieString() {
		return cookies.toString();
	}

	public String getRequestType() {
		if (requestType == null) {
			requestType = REQ_TYPE.GET;
		}
		return requestType.toString();
	}
	/**得到post串
	 * 
	 * @return
	 */
	public String getPostString() {
		if (!postString.equals("")) {
			postString = postString + "&";
		}
		postString += this.postParams.toString();
		return postString;
	}

	public void setPostString(String postString) {
		this.setRequestType(REQ_TYPE.POST);
		this.postString = postString;
	}

	public void setRequestProperty(String key, String value) {
		requestParam.put(key, value);
	}

	public void setPostProperty(String key, String value) {
		postParams.put(key, value);
	}
	/**得到所有cookie
	 * 
	 * @return
	 */
	public Cookies getCookies() {
		return cookies;
	}

	/**
	 * 
	 * @param url
	 *            提交地址
	 */
	public byte[] exec() throws Exception {

		ByteArrayOutputStream content = new ByteArrayOutputStream();
		byte[] bufferCache = null;

		try {
			httpConn = (HttpURLConnection) turl.openConnection();
			httpConn.setConnectTimeout(30000);
			httpConn.setReadTimeout(this.responseTimerOut);
			if (getRequestType().equals(REQ_TYPE.GET.toString())) {
				httpConn.setRequestMethod("GET");
			}
			if (getRequestType().equals(REQ_TYPE.POST.toString())) {
				httpConn.setRequestMethod("POST");
			}
			if (getRequestType().equals(REQ_TYPE.HEAD.toString())) {
				httpConn.setRequestMethod("HEAD");
			}
			httpConn.setRequestProperty("Host", turl.getHost());
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
			httpConn.setRequestProperty("Accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			httpConn.setRequestProperty("Accept-Language", "zh-cn,zh;q=0.5");
			httpConn.setRequestProperty("Accept-Charset",
					"gbk,GB2312;q=0.7,*;q=0.7");
			if (!(cookies.toString().equals(""))) {
				// 晕死..
				httpConn.setRequestProperty("Cookie", cookies.toString());
				//System.out.print("发送cookie======="+cookies.toString());
			}
			httpConn.setRequestProperty("Keep-Alive", "off");
			httpConn.setRequestProperty("Cache-Control", "max-age=0");
			// 吧requestParam里面的信息写入
			Iterator<Entry<String, String>> ir = this.requestParam
					.getIterator();
			while (ir.hasNext()) {
				Entry<String, String> obj = ir.next();
				httpConn.setRequestProperty(obj.getKey(), obj.getValue());
			}

			if (this.requestType.equals(REQ_TYPE.POST)) {
				//System.out.println("传递POST值");
				httpConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				httpConn.setRequestProperty("Content-Length",
						String.valueOf(getPostString().length()));
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);
				OutputStreamWriter out = new OutputStreamWriter(
						httpConn.getOutputStream());
				out.write(getPostString());
				out.close();
			}

			int contentLength = httpConn.getContentLength();
			// System.out.println("Content Length:" + contentLength);
			if (contentLength > 0) {
				bufferCache = new byte[contentLength];
			} else {
				bufferCache = new byte[1024];
			}
			InputStream uurl;
			uurl = httpConn.getInputStream();
			// 放到header去
			this.responseHeader = httpConn.getHeaderFields();
			// 放到mseega
			this.responseMessage =  httpConn.getHeaderFieldKey(0);

			if (httpConn.getHeaderField("Set-Cookie") != null) {
				List<String> newCookies = httpConn.getHeaderFields().get("Set-Cookie");
				if(newCookies!=null) {
	                Iterator<String> nit = newCookies.iterator();
	                while(nit.hasNext()) {
	                	String set_Cookie=nit.next();
	    				String cookiestring=set_Cookie.substring(0, set_Cookie.indexOf(";"));
	    				String cookiekey=cookiestring.substring(0,cookiestring.indexOf('='));
	    				String cookievalue=cookiestring.substring(cookiestring.indexOf('=')+1,cookiestring.length());
	    				cookies.put(cookiekey, cookievalue);
	    				//System.out.println("得到cookie :" + cookiekey);
	                }
				}
			}
			//
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(uurl));
			//
			// while (line != null) {
			// line = br.readLine();
			// if (line != null) {
			// content.append(line.toString()+"\n");
			// }
			// }

			int length = -1;
			while ((length = uurl.read(bufferCache)) > 0) {
				content.write(bufferCache, 0, length);
			}

		} catch (Exception e) {
			e.printStackTrace();
			httpConn.disconnect();
			throw e;
		}
		
		httpConn.disconnect();
		return content.toByteArray();
	}

	public Map<String, List<String>> getResponseHeader() {
		return this.responseHeader;
	}

	public String getResponseMessage() {
		return this.responseMessage;
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

	public void setResponseTimeOut(int i) {
		this.responseTimerOut=i;
	}
	
}
