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
 *  Created on : 2011-9-5, 下午5:07:05
 *  Author     : princehaku
 */

package net.techest.webqq.api;


public class WEBQQAPIFacroty extends APIFacroty{

	public WEBQQAPIFacroty(){
		//注册API
		this.registerApi(new LoginAPI());
		this.registerApi(new GetFriendsAPI());
		this.registerApi(new PullDataAPI());
	}

    private static class InstanceHolder {

        final static WEBQQAPIFacroty instance = new WEBQQAPIFacroty();
    }
    
	public static WEBQQAPIFacroty getInstance(){
		return InstanceHolder.instance;
	}

}
