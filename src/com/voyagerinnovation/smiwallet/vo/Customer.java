package com.voyagerinnovation.smiwallet.vo;

public class Customer extends User {

	private static final long serialVersionUID = 7615665873192836239L;

	public Customer() {
	}
	
	public Customer(String user) {
		this.user = user;
	}

	@Override
	public UserIdentity getIdentity() {
		return new UserIdentity(this.user, UserIdentity.CUSTOMER);
	}
}
