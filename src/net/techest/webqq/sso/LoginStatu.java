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
 *  Created on : 2011-9-4, 下午11:11:11
 *  Author     : princehaku
 */

package net.techest.webqq.sso;

/**用户的登录状态
 * 
 * @author princehaku
 *
 */
public enum LoginStatu {
	/**连接错误
	 * 
	 */
	CONNECT_ERROR,
	/**初始化完成
	 * 
	 */
	INIT_DONE,
	/**需要验证码
	 * 
	 */
	NEED_VERIFY,
	/**错误的验证码
	 * 
	 */
	VERIFY_ERROR,
	/**错误的UNI
	 * 
	 */
	UIN_ERROR,
	/**密码错误
	 * 
	 */
	PASSWORD_ERROR,
	/**登录超时
	 * 
	 */
	LOGIN_TIMEOUT,
	/**登录成功
	 * 
	 */
	SUCCESS

}
