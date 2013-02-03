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
 *  Created on : 2011-9-4, 下午6:24:28
 *  Author     : princehaku
 */
package net.techest.webqq.action;

import net.techest.util.Log4j;
import net.techest.util.StringTools;
import net.techest.webqq.bean.VerifyImage;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.QueryParam;
import net.techest.webqq.sso.AbstractLoginAction;
import net.techest.webqq.sso.Action;
import net.techest.webqq.sso.LoginStatu;
import net.techest.webqq.sso.SSOConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 通过SSO登录
 *
 *
 * @author princehaku
 *
 */
public class SSOLoginAction extends AbstractLoginAction implements Action {

    /**
     * 登录请求参数
     *
     */
    QueryParam loginparam = new QueryParam();
    private HttpClient hc;
    private String verifyCode;
    VerifyImage verifyImage;
    private String uin;
    private ScriptEngine scriptEngine;

    /**
     * 注意他会自动执行loginVerify里面的代码 如果获取到了的话
     *
     * 执行完毕的时候会调用callback
     */
    @Override
    public void doit() throws ActionException {
        if (this.getUser() == null) {
            throw new ActionException("没有指定登录用户");
        }
        hc = this.getUser().getServerContext().getHttpClient();
        // 初始化common.js
        this.scriptEngine = new ScriptEngineManager().getEngineByName("javascript");
        try {
            InputStream fin = Thread.currentThread().getContextClassLoader().getResourceAsStream("common.js");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            this.scriptEngine.eval(sb.toString());
        } catch (Exception ex) {
            throw new ActionException("common.js 不存在或者有错误");
        }
        try {
            this.init();
            this.fetchCode();
            if (!this.loginStatu.equals(LoginStatu.NEED_VERIFY)) {
                //如果不需要验证码 直接登录
                this.loginVerify(this.verifyCode);
            } else {
                //如果需要验证码  则把验证码下载回来 放入
                this.pullVerifyImage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.callBack();
            throw new ActionException("连接失败 " + e.getMessage());
        }
        this.callBack();
    }

    /**
     * 得到验证图片 有可能为null
     *
     * @return
     */
    public VerifyImage getVerifyImage() {
        return this.verifyImage;
    }

    /**
     * 拉取验证码
     *
     * @throws Exception
     *
     */
    public void pullVerifyImage() throws Exception {
        //下载图片
        hc.setUrl(SSOConfig.getInstance().getString("SSO_VERIFY_IMG_URL") + "?uin=" + getUser().getUin() + "&aid=" + SSOConfig.getInstance().getInt("APP_ID") + "&r=" + Math.random());
        byte[] content = hc.exec();
        this.verifyImage = new VerifyImage(content);
        Log4j.getInstance().debug("VERIFY :  验证图片下载完毕");
    }

    /**
     * 登录 需要验证码
     *
     * @throws Exception
     *
     */
    public void loginVerify(String verifyCode) throws Exception {
        loginparam.put("u", this.getUser().getUin());
        Invocable in = (Invocable) this.scriptEngine;
        String p = (String) (in.invokeFunction("encrypt", this.uin, this.getUser().getPassword(), verifyCode));

        loginparam.put("verifycode", verifyCode);
        loginparam.put("p", p);
        loginparam.put("u1", URLEncoder.encode(loginparam.get("u1"), "utf8"));
        loginparam.put("login2qq", "1");
        loginparam.put("from_ui", "1");
        loginparam.put("t", "1");
        loginparam.put("aid", "1003903");
        loginparam.put("g", "1");
        loginparam.put("h", "1");

        hc.setUrl("http://ptlogin2.qq.com/login?" + loginparam.toString());
        byte[] content = hc.exec();
        String res = new String(content);
        Log4j.getInstance().debug("LOGIN_RETURN : " + res);

        this.loginStatu = LoginStatu.LOGIN_TIMEOUT;

        if (res.indexOf("成功") > 0) {
            this.loginStatu = LoginStatu.SUCCESS;
        }
        if (res.indexOf("密码不正确") > 0) {
            this.loginStatu = LoginStatu.PASSWORD_ERROR;
        }
        if (res.indexOf("验证码不正确") > 0) {
            this.loginStatu = LoginStatu.VERIFY_ERROR;
        }
        if (res.indexOf("出现异常") > 0) {
            this.loginStatu = LoginStatu.CONNECT_ERROR;
        }

    }

    /**
     * 获取效验 以确定是否需要验证码
     *
     * @throws Exception
     *
     */
    protected void fetchCode() throws Exception {
        QueryParam param = new QueryParam();
        param.put("uin", this.getUser().getUin());
        param.put("appid", "" + SSOConfig.getInstance().getInt("APP_ID"));
        byte[] content = {};
        try {
            hc.setUrl(SSOConfig.getInstance().getString("SSO_CHECK_URL") + "?" + param.toString());
            content = hc.exec();
        } catch (Exception e) {
            this.loginStatu = LoginStatu.CONNECT_ERROR;
            Log4j.getInstance().error("连接错误" + e.getMessage());
            throw e;
        }
        String res = new String(content);
        Log4j.getInstance().debug("VC_RETURN : " + res);
        String uin = StringTools.findMc(res, "VC\\('.*?','.*?','(.*?)'\\)", 1);
        this.uin = (String) this.scriptEngine.eval(res);
        Log4j.getInstance().debug("VC_RETURN UIN: " + uin);
        String vckey = StringTools.findMc(res, "VC\\('(.*?)'", 1);
        String vcvalue = StringTools.findMc(res, ",'(.*?)'\\)", 1);
        Log4j.getInstance().debug("VC_RETURN KEY: " + vckey + "VALUE: " + vcvalue);
        if (vckey.equals("0")) {
            this.loginStatu = LoginStatu.NEED_VERIFY;
        } else {
            this.verifyCode = vcvalue;
        }
    }

    /**
     * 初始化登录确定aid和其他字段
     *
     * @throws Exception
     *
     */
    protected void init() throws Exception {
        QueryParam param = new QueryParam();
        param.put("target", "self");
        param.put("style", "5");
        param.put("appid", "" + SSOConfig.getInstance().getInt("APP_ID"));
        param.put("enable_qlogin", "1");
        param.put("no_verifyimg", "0");
        param.put("s_url", "http%3A%2F%2Fweb.qq.com%2Floginproxy.html");
        param.put("f_url", "loginerroralert");
        param.put("strong_login", "0");
        param.put("login_state", "10");
        param.put("t", "");
        byte[] content = {};
        try {
            hc.setUrl(SSOConfig.getInstance().getString("SSO_LOGIN_URL") + "?" + param.toString());
            content = hc.exec();
        } catch (Exception e) {
            Log4j.getInstance().error("连接错误" + e.getMessage());
            this.loginStatu = LoginStatu.CONNECT_ERROR;
            throw e;
        }

        Document doc = Jsoup.parse(new String(content));

        Elements loginforminputs = doc.select("#loginform input");

        for (int i = 0; i < loginforminputs.size(); i++) {
            Element input = loginforminputs.get(i);
            loginparam.put(input.attr("name"), input.val());
        }

        this.loginStatu = LoginStatu.INIT_DONE;
        Log4j.getInstance().debug(this.getUser().toString() + "   初始化完成");
    }

    public void callBack() {
        //nothing!
    }
}
