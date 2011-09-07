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
 *  Created on : 2011-9-5, 下午5:52:17
 *  Author     : princehaku
 */

package net.techest.webqq.sso;

import net.techest.webqq.action.ActionException;

public interface Action {
	/**执行一个动作
	 * 
	 * @throws ActionException
	 */
	public void doit() throws ActionException;
	/**执行后的回调
	 * 不会自动执行
	 * 请加载到doit后面或者任何你想执行的地方
	 */
	public void callBack();
}
