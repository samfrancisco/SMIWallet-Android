package com.voyagerinnovation.smiwallet.managers;

import com.bump.api.IBumpAPI;

public class BumpApiManager {

	public static final String BUMP_DEMO_API_KEY = "547c0d243b73439d966f2bcd3673514a";
	public static final String BUMP_DEMO_USER = "Voyager Innovations";
	
	private static BumpApiManager instance;
	private IBumpAPI api;
	
	private BumpApiManager() {}
	
	public static BumpApiManager getInstance() {
		if ( instance == null ) {
			instance = new BumpApiManager();
		}
		
		return instance;
	}

	public IBumpAPI getApi() {
		return api;
	}

	public void setApi(IBumpAPI api) {
		this.api = api;
	}
	
}
