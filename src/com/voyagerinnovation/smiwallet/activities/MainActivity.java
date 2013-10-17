package com.voyagerinnovation.smiwallet.activities;

import java.io.IOException;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bump.api.BumpAPIIntents;
import com.voyagerinnovation.smiwallet.R;
import com.voyagerinnovation.smiwallet.constants.Broadcasts;
import com.voyagerinnovation.smiwallet.interfaces.BumpInterface;
import com.voyagerinnovation.smiwallet.managers.BumpApiManager;
import com.voyagerinnovation.smiwallet.services.BumpReceiver;
import com.voyagerinnovation.smiwallet.services.BumpService;
import com.voyagerinnovation.smiwallet.utilities.BumpUtils;
import com.voyagerinnovation.smiwallet.utilities.Logger;
import com.voyagerinnovation.smiwallet.vo.Customer;
import com.voyagerinnovation.smiwallet.vo.User;

public class MainActivity extends Activity implements BumpInterface {

	private static final String TAG = MainActivity.class.getName();
	
	private BroadcastReceiver apiSetReceiver;
	private BumpReceiver bumpBroadcastReceiver;
	private long channel = -1;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(channel !=  -1) {
					Customer customer = new Customer();
					customer.setUser("user");
					try {
						BumpApiManager.getInstance().getApi().send(channel, BumpUtils.objectToByte(customer));
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}});
		
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
		
		bumpBroadcastReceiver = new BumpReceiver(this); 	
    	IntentFilter bumpFilter = new IntentFilter();  	
    	bumpFilter.addAction(BumpAPIIntents.CHANNEL_CONFIRMED);
    	bumpFilter.addAction(BumpAPIIntents.DATA_RECEIVED);
    	bumpFilter.addAction(BumpAPIIntents.NOT_MATCHED);
    	bumpFilter.addAction(BumpAPIIntents.CONNECTED);
    	bumpFilter.addAction(BumpAPIIntents.MATCHED);      
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
		Logger.i(TAG, "bump matched");
    	try {
			BumpApiManager.getInstance().getApi().confirm(intent.getLongExtra("proposedChannelID", 0), true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onConnected(Intent intent) {
		Logger.d(TAG, "onConnected");
		Logger.i(TAG, "bump connected");
    	try {
			BumpApiManager.getInstance().getApi().enableBumping();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNotMatched(Intent intent) {
		Logger.d(TAG, "onNotMatched");
		
	}

	@Override
	public void onDataReceived(Intent intent) {
		try {
			Logger.i(TAG, "Received data from : " + BumpApiManager.getInstance().getApi().userIDForChannelID(intent.getLongExtra("channelID", 0)));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		try {
			Customer customer = (Customer) BumpUtils.byteToObject(intent.getByteArrayExtra("data"));
			Logger.i(TAG, "Data : " + customer.getUser());
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//Logger.i(TAG, "Data : " + new String(intent.getByteArrayExtra("data")));
		Logger.d(TAG, "onDataReceived");
	}

	@Override
	public void onChannelConfirmed(Intent intent) {
		Logger.d(TAG, "onChannelConfirmed");
		Logger.i(TAG, "bump channel confirmed");
    	channel = intent.getLongExtra("channelID", 0);
		//BumpApiManager.getInstance().getApi().send(intent.getLongExtra("channelID", 0), "Hello, world!".getBytes());
	}

	@Override
	public void onDisconnect(Intent intent) {
		Logger.d(TAG, "onDisconnect");
	}
	
}
