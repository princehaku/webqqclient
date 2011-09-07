/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
