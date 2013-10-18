package com.voyagerinnovation.smiwallet.utilities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.bump.api.IBumpAPI;
import com.voyagerinnovation.smiwallet.constants.Broadcasts;
import com.voyagerinnovation.smiwallet.managers.BumpApiManager;

public class BumpConfiguratorTask extends AsyncTask<IBumpAPI, Void, Void> {

	private static final String TAG = BumpConfiguratorTask.class.getName();

	private Context context;
	
	public BumpConfiguratorTask(Context context) {
		this.context = context;
	}

	
	@Override
	protected Void doInBackground(IBumpAPI... params) {
		for (IBumpAPI iBumpAPI : params) {
			Logger.d(TAG, "api : " + iBumpAPI);
			try {
				iBumpAPI.configure(BumpApiManager.BUMP_DEMO_API_KEY, BumpApiManager.BUMP_DEMO_USER);
				BumpApiManager.getInstance().setApi(iBumpAPI);
				
				Logger.d(TAG, "trying to send broadcast BUMP_API_SET");
				
				Intent broadcastIntent = new Intent(Broadcasts.BUMP_API_SET);
				context.sendBroadcast(broadcastIntent);
			} catch (RemoteException e) {
				Logger.w(TAG, e);
			}
		}
		
		return null;
	}

}
