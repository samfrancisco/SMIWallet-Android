package com.voyagerinnovation.smiwallet.services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.bump.api.IBumpAPI;
import com.voyagerinnovation.smiwallet.constants.Broadcasts;
import com.voyagerinnovation.smiwallet.managers.BumpApiManager;
import com.voyagerinnovation.smiwallet.utilities.Logger;

public class BumpService extends Service {

	private static final String TAG = BumpService.class.getName();
	
	private final ServiceConnection connection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName className) {
			Logger.i(TAG, "service disconnected");
		}
		
		@Override
		public void onServiceConnected(ComponentName className, IBinder binder) {
			Logger.i(TAG, "onServiceConnected");
			
			IBumpAPI api = IBumpAPI.Stub.asInterface(binder);
			new BumpApiInitTask(api).execute();
			
			Log.i(TAG, "api : " + api);
			Log.i(TAG, "service connected");
		}

	};
	
	class BumpApiInitTask extends AsyncTask<Void, Void, Void> {
		
		private IBumpAPI api;
		
		public BumpApiInitTask(IBumpAPI api) {
			this.api = api;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				Logger.d(TAG, "api : " + api);
				api.configure(BumpApiManager.BUMP_DEMO_API_KEY, BumpApiManager.BUMP_DEMO_USER);
				BumpApiManager.getInstance().setApi(api);
				
				Logger.d(TAG, "trying to send broadcast BUMP_API_SET");
				
				Intent broadcastIntent = new Intent(Broadcasts.BUMP_API_SET);
				sendBroadcast(broadcastIntent);
			} catch(RemoteException e) {
				Logger.w(TAG, e);
			}
			return null;
		}
		
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		bindService(new Intent(IBumpAPI.class.getName()),
        		connection, 
        		Context.BIND_AUTO_CREATE);
		
		return super.onStartCommand(intent, flags, startId);
	}

}
