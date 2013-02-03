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
 *  Created on : 2011-9-5, 下午12:22:45
 *  Author     : princehaku
 */
package net.techest.webqq.action;

import net.sf.json.JSONObject;
import net.techest.util.Log4j;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.bean.api.PullDataAPI;
import net.techest.webqq.bean.api.WebQQAPIInterface;
import net.techest.webqq.client.OnlineStatu;
import net.techest.webqq.client.dialog.ServerDialog;
import net.techest.webqq.sso.Action;

/**
 * 获取服务器返回给客户机的消息
 *
 * @author princehaku
 *
 */
public class MessagePullThread extends Thread implements Action {

    /**
     * 登录的用户上下文
     *
     */
    QQUser loginUser;
    /**
     * 抓取数据的api
     *
     */
    PullDataAPI api;
    private boolean canEnd;
    private int failedTimes = 0;

    public void setToEnd(boolean canEnd) {
        this.canEnd = true;
    }

    public MessagePullThread(QQUser user) {
        this.loginUser = user;
    }

    @Override
    public void run() {

        Log4j.getInstance().info("正在获取消息..");

        api = (PullDataAPI) loginUser.getServerContext().getWebQQAPI("webqq_pulldata");

        try {
            this.doit();
        } catch (ActionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.loginUser.getServerContext().setOffline();
    }

    @Override
    public synchronized void doit() throws ActionException {
        while (!this.canEnd) {
            try {
                ((WebQQAPIInterface) api).init((WebQQUser) this.loginUser);
                api.process();
                JSONObject jsonObject = api.getResponseJson();
                this.failedTimes = 0;
                Log4j.getInstance().debug(jsonObject.toString());
//					
//					if(jsonObject.getLong("retcode")!=0&&jsonObject.getLong("retcode")!=102){
//						this.setToEnd(true);
//					}
                this.callBack();
            } catch (Exception e1) {
                //这里有可能是超时了  所以再根据messagecode细分一下进行处理
                Log4j.getInstance().error(e1.getMessage());
                if (e1.getMessage().indexOf("timed out") >= 0) {
                    this.failedTimes++;
                    // read的不算
                    if (e1.getMessage().indexOf("read") >= 0) {
                        this.failedTimes = 0;
                    }
                    if (failedTimes >= 3) {
                        this.setToEnd(true);
                    }
                } else {
                    e1.printStackTrace();
                    this.setToEnd(true);
                }
            }
        }
    }

    /**
     * 推送到消息队列
     *
     * @param jsonObject
     */
    private void pushToMessage(JSONObject jsonObject) {
        this.loginUser.getServerContext().getMessageQueue().add(jsonObject);
    }

    @Override
    public void callBack() {
        pushToMessage(api.getResponseJson());
    }
}
