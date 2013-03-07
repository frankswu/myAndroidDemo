package com.example.android.apis;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * @author frankswu
 * 
 */
public class AppUtils {

	private static final String TAG = "AppUtils";

	public static PackageInfo getPakcageInfo(Context context) {
		String pkgName = context.getPackageName();
		try {
			return context.getPackageManager().getPackageInfo(pkgName, 0);
		} catch (NameNotFoundException e) {
			if (e != null) {
				Log.d(TAG,e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
	}

}
