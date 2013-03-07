package com.example.android.apis.app;


import frankswu.com.utils.LogUtils;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;

public class RepeatingService extends Service {

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
	public class LocalBinder extends Binder {
		RepeatingService getService() {
			return RepeatingService.this;
		}
	}


	private static final String TAG = "RepeatingService";

	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtils.logStringArray(TAG, "onStartCommand", "");
//		checkNetWorkStatus(this);
		return super.onStartCommand(intent, flags, startId);
	}
	
	public static boolean checkNetWorkStatus(Context context) {
		boolean netStatus = false;
		ConnectivityManager netManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netManager.getActiveNetworkInfo();
		if (netManager.getActiveNetworkInfo() != null) {
			netStatus = netManager.getActiveNetworkInfo().isAvailable();
		}
		
//		if (!netStatus && isTips) {
//			Toast.makeText(context, context.getString(R.string.no_available_network)
//					, Toast.LENGTH_SHORT)
//					.show();
//		}
//		LogUtils.logObjectArray2File(TAG, "BDLocation", "checkNetWorkStatus.netStatus", netStatus);
		return netStatus;
	}
	

}
