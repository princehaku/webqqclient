/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.techest.webqq.action;

import net.techest.webqq.bean.QQUser;
import net.techest.webqq.bean.VerifyImage;
import net.techest.webqq.sso.AbstractResponseHandle;
import net.techest.webqq.sso.LoginStatu;

/**
 *
 * @author haku
 */
public abstract class AbstractLoginAction {
	
	protected QQUser loginUser;
	
	protected LoginStatu loginStatu;

	protected AbstractResponseHandle resp;
	
	public LoginStatu getStatu(){
		return this.loginStatu;
	}

	public void setUser(QQUser user){
		this.loginUser=user;
	}

	public QQUser getUser(){
		return this.loginUser;
	}
	
	public abstract void auth() throws ActionException;
	
	public abstract VerifyImage getVerifyImage();
	
	public  abstract void loginVerify(String verifyCode) throws Exception;

	public void setLoginStatu(LoginStatu loginStatu) {
		this.loginStatu=loginStatu;
	}
	public void setResponseHandle(AbstractResponseHandle resp) {
		this.resp=resp;
	}
}
