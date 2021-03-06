package maintest;

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
 *  Created on : 2011-9-4, 下午11:00:27
 *  Author     : princehaku
 */


import net.sf.json.JSONObject;
import net.techest.webqq.action.WebQQLoginAction;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.client.dialog.ServerDialog;
import net.techest.webqq.response.WebQQLoginResponseHandle;

import java.io.IOException;
import net.techest.util.Log4j;
import net.techest.webqq.client.OnlineStatu;

public class TestQQ {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        QQUser u = new WebQQUser();
        u.setUin("389663316");
        u.setPassword("!52dearesta3");
        //打开一个新的服务器会话
        ServerDialog sd = new ServerDialog(u);
        sd.setLoginAction(new WebQQLoginAction());
        //设置登录回调
        ((WebQQLoginAction) sd.getLoginAction()).setResponseHandle(new WebQQLoginResponseHandle(sd));
        sd.start();

        while (OnlineStatu.OFFLINE != sd.getOnlineStatu()) {
            // 这个地方会同步
            JSONObject rjson = sd.getMessageQueue().pull();
            Log4j.getInstance().info("消息到来" + rjson);
        }
    }
}
