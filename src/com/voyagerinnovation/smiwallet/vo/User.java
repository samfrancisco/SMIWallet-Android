package com.voyagerinnovation.smiwallet.vo;

import com.voyagerinnovation.smiwallet.interfaces.IBumpSendable;

public abstract class User implements IBumpSendable {
	
	private static final long serialVersionUID = 778339529535402176L;
	
	protected String user;
	protected String pin;
	protected String mobile;
	protected String securityAnswer;
	
	public User() {
	}
	
	public User(String user) {
		this.user = user;
	}
	
	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}
	
	public abstract UserIdentity getIdentity();

}
