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
 *  Created on : 2011-9-5, 下午5:35:15
 *  Author     : princehaku
 */

package net.techest.webqq.bean.api;

import net.techest.webqq.bean.WebQQUser;

/**所有的webqqapi必须实现的接口
 * 
 * @author haku
 *
 */
public interface WebQQAPIInterface {
	/**初始化
	 * 
	 * @param user
	 */
	public void init(WebQQUser user) ;
	/**处理
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception;
	/**回调
	 * 
	 */
	public void callback();
}
