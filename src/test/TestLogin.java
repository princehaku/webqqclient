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
 *  Author     : princehaku
 */
package test;

import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.HttpClient.REQ_TYPE;
import net.techest.webqq.net.QueryParam;
import net.techest.webqq.sso.SSOConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

public class TestLogin {

	public static void main(String argv[]) throws Exception {

		String qnumber="389663316";
		String qpasswd="!52dearesta3";
		System.out.println(SSOConfig.getInstance().getInt("APP_ID"));
		HttpClient hc = new HttpClient();
		hc.setRequestType(REQ_TYPE.GET);
		QueryParam param=new QueryParam();
		param.put("target", "self");
		param.put("style", "5");
		param.put("appid", "1003903");
		param.put("enable_qlogin", "1");
		param.put("no_verifyimg", "0");
		param.put("s_url", "http%3A%2F%2Fweb.qq.com%2Floginproxy.html");
		param.put("f_url", "loginerroralert");
		param.put("strong_login", "0");
		param.put("login_state", "10");
		param.put("t", "");
		System.out.println(param.toString());
		hc.setUrl("http://ui.ptlogin2.qq.com/cgi-bin/login?"+param.toString());
		byte[] content=hc.exec();
		Document doc = Jsoup.parse(new String(content));
		
		Elements loginforminputs = doc.select("#loginform input");
		QueryParam loginparam=new QueryParam();
		for(int i=0;i<loginforminputs.size();i++){
			Element input=loginforminputs.get(i);
			loginparam.put(input.attr("name"),input.val());
		}
		//监测验证码http://ptlogin2.qq.com/check?uin=349674806&appid=1003903&r=0.6438216955921257
		
		//下载图片
		hc.setUrl("http://captcha.qq.com/getimage?uin="+qnumber+"&aid=1003903&r="+Math.random());
		content=hc.exec();
		
		FileOutputStream fops = new FileOutputStream("img.jpg"); 
		fops.write(content);
		fops.close();

		System.out.print("请输入验证码");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String verCode=br.readLine();
		
		loginparam.put("u", qnumber);
		ScriptEngine se = new ScriptEngineManager().getEngineByName("javascript"); 
		se.eval(new FileReader("common.js"));
    	Invocable in=(Invocable)se; 
		String p= (String)( in.invokeFunction("encrypt", qpasswd,verCode));
		
		loginparam.put("verifycode", verCode);
		loginparam.put("p", p);
		loginparam.put("u1",URLEncoder.encode(loginparam.get("u1"),"utf8"));
		loginparam.put("login2qq", "1");

		hc.setUrl("http://ptlogin2.qq.com/login?"+loginparam.toString());
	    content=hc.exec();
		
		System.out.print(new String(content));
		
//		http://ptlogin2.qq.com/login?u=389663316&p=72A430E88E0B073361AE861EC71C3978&verifycode=wdwa&webqq_type=10&remember_uin=1&login2qq=0&aid=1003903&u1=http%3A%2F%2Fweb.qq.com%2Floginproxy.html%3Flogin2qq%3D0%26webqq_type%3D10&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=3-11-8011&mibao_css=
//		
		content=hc.exec();
//		
//		System.out.print(new String(content));
//		
//		hc.setUrl("http://captcha.qq.com/getimage?aid=1003903&"+Math.random());
//		content=hc.exec();
//		
//		FileOutputStream fops = new FileOutputStream("img.jpg"); 
//		fops.write(content);
//		fops.close();
		
	}
}
