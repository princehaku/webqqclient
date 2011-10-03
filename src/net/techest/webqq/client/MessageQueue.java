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

package net.techest.webqq.client;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Observable;

/**消息链 
 * 同时也是一个被观察者
 * 当收到消息的时候会通知观察者
 * @author princehaku
 *
 */
public class MessageQueue extends Observable implements MessageReceiver{
	
	ArrayList<JSONObject> messages=new ArrayList<JSONObject>();
	
	/**添加新的消息会被添加在队列尾部
	 * 
	 * @param jsonObject
	 */
	public synchronized void add(JSONObject jsonObject) {
		messages.add(jsonObject);
		//唤醒进程
		this.notify();
		//通知观察者有新的事件到来了
		this.notifyObservers();
	}
	
	public synchronized JSONObject pull(){
		//当消息区没有消息的时候 暂停线程 等待新的消息
		while(this.messages.size()==0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JSONObject rjson=messages.get(0);
		messages.remove(0);
		return rjson;
	}

}
