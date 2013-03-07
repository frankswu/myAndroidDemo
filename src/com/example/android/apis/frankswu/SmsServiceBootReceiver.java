package com.example.android.apis.frankswu;

import com.example.android.apis.app.LocalService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 
 * test for boot receiver
 * @author frankswu
 *
 */
public class SmsServiceBootReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsServiceBootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG,"onReceive");
		context.startService(new Intent(context,LocalService.class));
	}

}
