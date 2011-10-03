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
 *  Created on : 2011-9-5, 上午9:09:16
 *  Author     : princehaku
 */

package net.techest.webqq.response;

import net.techest.util.Log4j;
import net.techest.webqq.bean.api.APIBase;
import net.techest.webqq.bean.api.GetFaceAPI;
import net.techest.webqq.client.dialog.ServerDialog;
import net.techest.webqq.sso.AbstractLoginAction;
import net.techest.webqq.sso.LoginStatu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebQQLoginResponseHandle implements AbstractResponseHandle {

	private ServerDialog sd;
	
	public WebQQLoginResponseHandle(ServerDialog sd) {
		this.sd=sd;
	}

	@Override
	public void handle(Object obj) {

		AbstractLoginAction loginAction =(AbstractLoginAction) obj;
		
		if(loginAction.getStatu().equals(LoginStatu.NEED_VERIFY)){
			Log4j.getInstance().debug("请输入验证码");
			try {
				loginAction.getVerifyImage().saveTo("img.jpg");
			} catch (IOException e1) {
				e1.printStackTrace();
				Log4j.getInstance().error("写入验证码错误");
			}
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			try {
				sd.inputVerify(br.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		APIBase api=this.sd.getWebQQAPI("webqq_get_face");
		try {
			((GetFaceAPI)api).setUin(loginAction.getUser().getUin());
			api.process();
			System.out.println("服务器返回"+api.getResponseString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
