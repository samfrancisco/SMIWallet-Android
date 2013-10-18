package com.voyagerinnovation.smiwallet.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.bump.api.IBumpAPI;
import com.voyagerinnovation.smiwallet.utilities.BumpConfiguratorTask;
import com.voyagerinnovation.smiwallet.utilities.Logger;

public class BumpService extends Service {

	private static final String TAG = BumpService.class.getName();
	private static IBumpAPI api;
	public static Context bumpServiceContext;
	
	private final ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Logger.i(TAG, "service disconnected");
		}
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			Logger.i(TAG, "onServiceConnected");
			
			api = IBumpAPI.Stub.asInterface(binder);
			configureBumpApi();
			
			Log.i(TAG, "api : " + api);
			Log.i(TAG, "service connected");
		}
	};
	
	public static void configureBumpApi() {
		new BumpConfiguratorTask(bumpServiceContext).execute(api);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		bumpServiceContext = this;
		
		bindService(new Intent(IBumpAPI.class.getName()),
        		connection, 
        		Context.BIND_AUTO_CREATE);
		
		return START_STICKY;
	}

}
