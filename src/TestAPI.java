import java.io.IOException;

import net.techest.webqq.action.WebQQLoginAction;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.WebQQUser;
import net.techest.webqq.client.dialog.ServerDialog;
import net.techest.webqq.sso.LoginResponseHandle;

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

public class TestAPI {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		QQUser u=new WebQQUser();
		u.setUin("389663316");
		u.setPassword("!52dearesta3");
		ServerDialog sd=new ServerDialog(u);
		sd.setLoginAction(new WebQQLoginAction());
		sd.getLoginAction().setResponseHandle(new LoginResponseHandle(sd));
		sd.start();
	}

}
