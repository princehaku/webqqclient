/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.webqq.sso;

import net.techest.webqq.action.ActionException;
import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.VerifyImage;

/**虚单点登录基类
 *
 * @author haku
 */
public abstract class AbstractLoginAction implements Action{
	
	protected QQUser loginUser;
	
	protected LoginStatu loginStatu;
	
	public LoginStatu getStatu(){
		return this.loginStatu;
	}

	public void setUser(QQUser user){
		this.loginUser=user;
	}

	public QQUser getUser(){
		return this.loginUser;
	}
	
	public abstract void doit() throws ActionException;
	
	public abstract VerifyImage getVerifyImage();
	
	public abstract  void pullVerifyImage() throws Exception;
	
	public  abstract void loginVerify(String verifyCode) throws Exception;

	public void setLoginStatu(LoginStatu loginStatu) {
		this.loginStatu=loginStatu;
	}
}
