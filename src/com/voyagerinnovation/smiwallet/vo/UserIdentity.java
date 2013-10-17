package com.voyagerinnovation.smiwallet.vo;

import com.voyagerinnovation.smiwallet.interfaces.IBumpSendable;

public class UserIdentity implements IBumpSendable {

	private static final long serialVersionUID = 6018609736979272629L;
	
	public static final int MERCHANT = 1;
	public static final int CUSTOMER = 2;
	
	private String user;
	private int identity;
	
	public UserIdentity(String user, int identity) {
		this.user = user;
		this.identity = identity;
	}
	
	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		this.user = user;
	}
	
	public int getIdentity() {
		return identity;
	}
	
}
