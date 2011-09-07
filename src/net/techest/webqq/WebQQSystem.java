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
