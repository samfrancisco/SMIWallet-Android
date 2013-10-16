package com.voyagerinnovation.smiwallet.interfaces;

import android.content.Intent;

public interface BumpInterface {

	public void registerBumpReceiver();
	
	public void unregisterBumpReceiver();
	
	public void onMatched(Intent intent);
	
	public void onConnected(Intent intent);
	
	public void onNotMatched(Intent intent);
	
	public void onDataReceived(Intent intent);
	
	public void onChannelConfirmed(Intent intent);
	
	public void onDisconnect(Intent intent);
	
}
