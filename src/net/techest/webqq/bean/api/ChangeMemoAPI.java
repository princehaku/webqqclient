/**
 * Copyright 2012 Etao Inc.
 *
 * Project Name : webqqclient Created on : May 8, 2013, 10:03:22 PM Author :
 * haku
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.webqq.bean.api;

import net.sf.json.JSONObject;
import net.techest.util.StringTools;
import net.techest.webqq.net.HttpClient;
import net.techest.webqq.net.QueryParam;

/**
 *
 * @author haku
 */
public class ChangeMemoAPI extends CommonJsonAPI {

    private String text = "";

    public ChangeMemoAPI() {
        this.setRequestType(HttpClient.REQ_TYPE.POST);
        this.setRequestURI("http://s.web2.qq.com/api/set_long_nick2");
    }

    @Override
    public void initParam(QueryParam requestGet, JSONObject requestJson) {
        JSONObject newj;
        String text = StringTools.addSlashes(StringTools.removeHTMLtags(this.getText()));
        newj = JSONObject.fromObject("{\"nlk\":\"" + text + "\",\"vfwebqq\":\"\"}");
        this.setRequestJson(newj);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
