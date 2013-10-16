package com.voyagerinnovation.smiwallet.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;

import com.bump.api.BumpAPIIntents;
import com.voyagerinnovation.smiwallet.R;
import com.voyagerinnovation.smiwallet.constants.Broadcasts;
import com.voyagerinnovation.smiwallet.interfaces.BumpInterface;
import com.voyagerinnovation.smiwallet.managers.BumpApiManager;
import com.voyagerinnovation.smiwallet.services.BumpService;
import com.voyagerinnovation.smiwallet.utilities.Logger;

public class MainActivity extends Activity implements BumpInterface {

	private static final String TAG = MainActivity.class.getName();
	
	private BroadcastReceiver apiSetReceiver;
	private BroadcastReceiver bumpBroadcastReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Intent intent = new Intent(this, BumpService.class);
		startService(intent);
	}
	
	@Override
	protected void onResume() {
		registerApiSetReceiver();
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unregisterBumpReceiver();
		
		super.onPause();
	}
	
	private void registerApiSetReceiver() {
		apiSetReceiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				Logger.i(TAG, "apiSetReceiver.onReceive");
				registerBumpReceiver();
			}
		};
		
		IntentFilter apiSetIntentFilter = new IntentFilter(Broadcasts.BUMP_API_SET);
    	registerReceiver(apiSetReceiver, apiSetIntentFilter);
	}
	
	@Override
	public void registerBumpReceiver() {
		Logger.d(TAG, "setupBumpReceiver");
		
		bumpBroadcastReceiver = new BroadcastReceiver() {
    		
    		@Override
    		public void onReceive(Context context, Intent intent) {
    			final String action = intent.getAction();
    			
    			Logger.i(TAG, "BumpApiManager.api : " + BumpApiManager.getInstance().getApi());
    			
    			try {
    				if ( action.equals(BumpAPIIntents.DATA_RECEIVED) ) {
    					Logger.i(TAG, "Received data from : " + BumpApiManager.getInstance().getApi().userIDForChannelID(intent.getLongExtra("channelID", 0)));
    					Logger.i(TAG, "Data : " + new String(intent.getByteArrayExtra("data")));
    	            } else if (action.equals(BumpAPIIntents.MATCHED)) {
    	            	Logger.i(TAG, "bump matched");
    	            	BumpApiManager.getInstance().getApi().confirm(intent.getLongExtra("proposedChannelID", 0), true);
    	            } else if (action.equals(BumpAPIIntents.CHANNEL_CONFIRMED)) {
    	            	Logger.i(TAG, "bump channel confirmed");
    	            	BumpApiManager.getInstance().getApi().send(intent.getLongExtra("channelID", 0), "Hello, world!".getBytes());
    	            } else if (action.equals(BumpAPIIntents.CONNECTED)) {
    	            	Logger.i(TAG, "bump connected");
    	            	BumpApiManager.getInstance().getApi().enableBumping();
    	            }
    	        } catch (RemoteException e) {
    	        	e.printStackTrace();
    	        }
    			
    		}
    	};
    	
    	IntentFilter bumpFilter = new IntentFilter();
    	bumpFilter.addAction(BumpAPIIntents.CHANNEL_CONFIRMED);
    	bumpFilter.addAction(BumpAPIIntents.DATA_RECEIVED);
    	bumpFilter.addAction(BumpAPIIntents.NOT_MATCHED);
        bumpFilter.addAction(BumpAPIIntents.MATCHED);
        bumpFilter.addAction(BumpAPIIntents.CONNECTED);
        registerReceiver(bumpBroadcastReceiver, bumpFilter);
	}
	
	@Override
	public void unregisterBumpReceiver() {
		if ( apiSetReceiver != null ) {
			unregisterReceiver(apiSetReceiver);
		}
		
		if ( bumpBroadcastReceiver != null ) {
			unregisterReceiver(bumpBroadcastReceiver);
		}
	}

	@Override
	public void onMatched(Intent intent) {
		Logger.d(TAG, "onMatched");
	}

	@Override
	public void onConnected(Intent intent) {
		Logger.d(TAG, "onConnected");
	}

	@Override
	public void onNotMatched(Intent intent) {
		Logger.d(TAG, "onNotMatched");
	}

	@Override
	public void onDataReceived(Intent intent) {
		Logger.d(TAG, "onDataReceived");
	}

	@Override
	public void onChannelConfirmed(Intent intent) {
		Logger.d(TAG, "onChannelConfirmed");
	}

	@Override
	public void onDisconnect(Intent intent) {
		Logger.d(TAG, "onDisconnect");
	}
	
}
