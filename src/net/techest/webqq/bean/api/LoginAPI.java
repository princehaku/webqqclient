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
 *  Created on : 2011-9-5, 下午4:54:33
 *  Author     : princehaku
 */
package net.techest.webqq.bean.api;

import net.sf.json.JSONObject;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.client.OnlineStatu;
import net.techest.webqq.net.HttpClient.REQ_TYPE;
import org.springframework.stereotype.Component;

/**
 * 登录的api 这个和其他的api不同 所以没有继承自commonapi 它不需要clientid和psessionid
 * 它的作用是获取clientid和psessionid供之后的api使用
 *
 * @author haku
 *
 */
@Component
public class LoginAPI extends APIBase implements WebQQAPIInterface {

    private WebQQUser user;
    private OnlineStatu onlinestatu = OnlineStatu.ONLINE;

    public LoginAPI() {
        this.setRequestType(REQ_TYPE.POST);
        this.setRequestURI("http://d.web2.qq.com/channel/login2");
    }

    public void init(WebQQUser user) {
        this.user = user;
        hc = user.getServerContext().getHttpClient();
        hc.setRequestProperty("Referer", "http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");
        String ptwebqq = hc.getCookies().get("ptwebqq");
        String ptuserinfo = hc.getCookies().get("ptuserinfo");
        String param = "r={\"status\":\"" + onlinestatu.toString().toLowerCase() + "\",\"ptwebqq\":\"" + ptwebqq + "\",\"passwd_sig\":\"\",\"clientid\":\"" + ptuserinfo + "\",\"psessionid\":null}&clientid=" + ptuserinfo + "&psessionid=null";
        this.setRequestPostString(param);
    }

    @Override
    public void callback() {
        JSONObject jsonObject = this.getResponseJson();
        if (jsonObject.getLong("retcode") == 0) {
            jsonObject = jsonObject.getJSONObject("result");
            this.user.setClientid(hc.getCookies().get("ptuserinfo"));
            this.user.setPsessionid(jsonObject.getString("psessionid"));
            this.user.setVfwebqq(jsonObject.getString("vfwebqq"));
        }
    }

    /**
     *
     * @return
     */
    public JSONObject getResponseJson() {
        JSONObject json = JSONObject.fromObject(this.getResponseString());
        return json;
    }

    public OnlineStatu getStatu() {
        return onlinestatu;
    }

    public void setStatu(OnlineStatu statu) {
        this.onlinestatu = statu;
    }
}
