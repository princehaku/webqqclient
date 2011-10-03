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
 *  Author     : princehaku
 */
package net.techest.webqq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**系统类
 *  方便获取api工厂
 * @author princehaku
 *
 */
public class WebQQSystem {
	
	private ApplicationContext apicontext;
	
	private static class instanceHandle{
		final static WebQQSystem instance=new WebQQSystem();
	}
	
	public static WebQQSystem getInstance(){
		return instanceHandle.instance;
	}
	
	public WebQQSystem(){
		apicontext   =   new ClassPathXmlApplicationContext("webqqapi.xml");
	}
	
	public ApplicationContext getContext(){
		return this.apicontext;
	}
}
