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
 *  Created on : 2011-9-5, 上午8:03:57
 *  Author     : princehaku
 */

package net.techest.webqq.bean;
/**这个代表一个webqq登录的用户
 * 登录进webqq的需要有clientid和psessionid
 * 才能进行其他的qq相关操作
 * vfwebqq在执行某些操作的时候属性才需要
 * 会话的clientid 这个是由客户端随机产生一个数字
 * psessionid由服务器获得 之后不变
 * 这三个值会在登录成功后自动注入
 * @author princehaku
 *
 */
public class WebQQUser extends QQUser{
	/**会话的clientid 这个是由客户端随机产生一个数字
	 * 但是之后的效验都需要他
	 * 
	 */
	String clientid="";
	/**session
	 * 由服务器获得 之后不变
	 */
	String psessionid="";
	/**加密token
	 * 由服务器获得 之后不变
	 */
	String vfwebqq="";
	
	public String getVfwebqq() {
		return vfwebqq;
	}

	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}

	public String getClientid() {
		return clientid;
	}
	
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	
	public String getPsessionid() {
		return psessionid;
	}
	
	public void setPsessionid(String psessionid) {
		this.psessionid = psessionid;
	}
}
