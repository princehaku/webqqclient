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
 *  Created on : 2011-9-4, 下午6:07:27
 *  Author     : princehaku
 */
package net.techest.webqq.bean;

import net.techest.webqq.client.dialog.ServerDialog;

/**qq用户基类
 *
 * @author haku
 */
public class QQUser {
    /**用户唯一标志
     * 一般说来应该是QQ号码
     * 但是实际上只有用户自己的是qq号码
     * 其他的用户均是一个随机的值
     * 会变动
     */
	private String uin;
    /**用户密码
     * 
     */
    private String password;
    /**服务器会话上下文
     * 
     */
	private ServerDialog serverContext;
    
	public String getUin() {
		return uin;
	}
	
	public void setUin(String uin) {
		this.uin = uin;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	/**设置服务器会话上下文
	 * 
	 * @param serverDialog
	 */
	public void setServerContext(ServerDialog serverDialog) {
		this.serverContext=serverDialog;
	}
	/**得到服务器会话上下文
	 * 
	 * @return
	 */
	public ServerDialog getServerContext() {
		return this.serverContext;
	}
	
}
