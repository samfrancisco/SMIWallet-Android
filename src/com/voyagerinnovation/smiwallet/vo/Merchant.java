package com.voyagerinnovation.smiwallet.vo;

public class Merchant extends User {

	private static final long serialVersionUID = 7701060387226375293L;

	public Merchant() {
	}
	
	public Merchant(String user) {
		this.user = user;
	}

	@Override
	public UserIdentity getIdentity() {
		return new UserIdentity(this.user, UserIdentity.MERCHANT);
	}
}
