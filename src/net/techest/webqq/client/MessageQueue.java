package net.techest.webqq.client;

import java.util.ArrayList;

import net.sf.json.JSONObject;

public class MessageQueue implements MessageReceiver{
	
	ArrayList<JSONObject> messages=new ArrayList<JSONObject>();
	
	/**会被添加在队列尾部
	 * 
	 * @param jsonObject
	 */
	public synchronized void add(JSONObject jsonObject) {
		messages.add(jsonObject);
		this.notify();
	}
	
	public synchronized JSONObject pull(){
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
