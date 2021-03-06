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
 *  Created on : 2011-9-4, 下午6:30:42
 *  Author     : princehaku
 */
package net.techest.webqq.client.dialog;

import net.sf.json.JSONObject;
import net.techest.util.Log4j;
import net.techest.webqq.WebQQSystem;
import net.techest.webqq.action.ActionException;
import net.techest.webqq.action.MessagePullThread;
import net.techest.webqq.action.WebQQLoginAction;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.bean.api.APIBase;
import net.techest.webqq.bean.api.WEBQQAPIFacroty;
import net.techest.webqq.bean.api.WebQQAPIInterface;
import net.techest.webqq.client.MessageQueue;
import net.techest.webqq.client.OnlineStatu;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.sso.AbstractLoginAction;
import net.techest.webqq.sso.LoginStatu;

/**
 * 每一个客户端于服务器交互均会创建一个这个
 *
 * 当有状态消息变更时 更新自身消息接收链
 *
 * @author princehaku
 *
 */
public class ServerDialog extends Thread {

    private QQUser loginUser;
    /**
     * 消息获取器 获取服务器推送的消息
     *
     * 并维持用户在线
     */
    private MessagePullThread msgpulltask;
    /**
     * 登录处理器
     *
     * @param user
     */
    private AbstractLoginAction loginAction;
    /**
     * 在线状态
     */
    private OnlineStatu onlineStatu;
    /**
     * 消息接收链
     *
     */
    private MessageQueue messageQueue;

    private HttpClient hc;
    
    private WebQQSystem system;

    public ServerDialog(QQUser user) {
        this.system = new WebQQSystem();
        this.hc = new HttpClient();
        this.messageQueue = new MessageQueue();
        this.loginUser = user;
        this.loginUser.setServerContext(this);
    }

    /**
     * 设置登录处理器
     *
     * @param loginAction
     */
    public void setLoginAction(AbstractLoginAction loginAction) {
        this.loginAction = loginAction;
        this.loginAction.setUser(this.loginUser);
    }

    public synchronized void run() {
        try {
            //发起验证连接
            this.userAuth();
            //如果需要验证码 则等待
            if (getLoginAction().getStatu().equals(LoginStatu.NEED_VERIFY)) {
                Log4j.getInstance().info("需要验证码");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log4j.getInstance().info("检测登录状态" + getLoginAction().getStatu());
            //如果是登录成功的  阻塞掉进程
            if (getLoginAction().getStatu().equals(LoginStatu.SUCCESS)) {
                this.onlineStatu = OnlineStatu.ONLINE;
                this.msgpulltask = new MessagePullThread(this.loginUser);
                this.msgpulltask.start();
                while (this.onlineStatu != onlineStatu.OFFLINE) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Log4j.getInstance().error("登录错误 " + getLoginAction().getStatu());
            }

        } catch (DialogException e) {
            e.printStackTrace();
        }

        //释放掉其他线程 并结束

    }

    public synchronized void setOnlineStatu(OnlineStatu statu) {
        // 先修改状态
        this.onlineStatu = statu;
        JSONObject jsonObject = JSONObject.fromObject("{status : '',result : {'poll_type' : ''}}");
        jsonObject.put("status", statu);
        // 然后推送消息
        this.loginUser.getServerContext().getMessageQueue().add(jsonObject);
        this.notify();
    }

    /**
     * 验证码请求
     *
     */
    public synchronized void inputVerify(String verifyCode) {
        try {
            getLoginAction().loginVerify(verifyCode);
            getLoginAction().callBack();
        } catch (Exception e) {
            this.loginAction.setLoginStatu(LoginStatu.VERIFY_ERROR);
        }
        //唤醒进程
        this.notify();
    }

    /**
     * 登录处理 默认使用WebQQLoginAction
     *
     * @param loginAction
     */
    public AbstractLoginAction getLoginAction() {
        if (this.loginAction == null) {
            this.loginAction = new WebQQLoginAction();
            this.loginAction.setUser(this.loginUser);
        }
        return this.loginAction;
    }

    /**
     * 验证用户登录
     *
     * @throws DialogException
     *
     */
    public void userAuth() throws DialogException {
        try {
            getLoginAction().doit();
        } catch (ActionException e) {
            Log4j.getInstance().error("登录时错误");
            e.printStackTrace();
            throw new DialogException("登录时错误" + e.getMessage());
        }
    }

    public OnlineStatu getOnlineStatu() {
        return this.onlineStatu;
    }

    /**
     * 得到单例的http连接类 整个会话会一直使用它
     *
     * @return
     */
    public HttpClient getHttpClient() {
        return this.hc;
    }

    /**
     * 通过sping的工厂得到api 都是得到一个clone的api对象 如果不存在会返回空
     *
     * @param apiName
     * @return
     */
    public APIBase getWebQQAPI(String apiName) {
        APIBase api = null;
        WEBQQAPIFacroty apifac = (WEBQQAPIFacroty) this.system.getContext().getBean("apifactory");
        try {
            api = apifac.getApiByName(apiName);
            if (api == null) {
                Log4j.getInstance().error("不存在API ：" + apiName);
                return api;
            }
            ((WebQQAPIInterface) api).init((WebQQUser) this.loginUser);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return api;
    }

    public MessageQueue getMessageQueue() {
        return messageQueue;
    }

    public QQUser getUser() {
        return this.loginUser;
    }
}
