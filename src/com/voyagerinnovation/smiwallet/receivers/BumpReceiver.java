package com.voyagerinnovation.smiwallet.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bump.api.BumpAPIIntents;
import com.voyagerinnovation.smiwallet.interfaces.BumpInterface;
import com.voyagerinnovation.smiwallet.managers.BumpApiManager;
import com.voyagerinnovation.smiwallet.utilities.Logger;

public class BumpReceiver extends BroadcastReceiver {

	private final static String TAG = BumpReceiver.class.getSimpleName();

	private BumpInterface bump;

	public BumpReceiver(BumpInterface bump) {
		this.bump = bump;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		Logger.i(TAG, "BumpApiManager.api : " + BumpApiManager.getInstance().getApi());
			
		final String action = intent.getAction();
		
		if (action.equals(BumpAPIIntents.DATA_RECEIVED)) {
			bump.onDataReceived(intent);
			
		} else if (action.equals(BumpAPIIntents.MATCHED)) {
			bump.onMatched(intent);
		
		} else if (action.equals(BumpAPIIntents.CONNECTED)) {
			bump.onConnected(intent);
			
		} else if (action.equals(BumpAPIIntents.CHANNEL_CONFIRMED)) {
			bump.onChannelConfirmed(intent);
		}
	}
}
