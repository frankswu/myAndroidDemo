/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.app;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.
import com.example.android.apis.R;

import frankswu.com.utils.LogUtils;

import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm
 * that should occur once.
 */
public class RepeatingAlarm extends BroadcastReceiver {
	private static final String TAG = "RepeatingAlarm";

	@Override
	public void onReceive(Context context, Intent intent) {
		LogUtils.logStringArray(TAG, "onReceive", "");

		context.startService(new Intent(context, RepeatingService.class));
		LogUtils.logObjectArray2File(TAG, "RepeatingAlarm", "onReceive", R.string.repeating_received);
		Toast.makeText(context, R.string.repeating_received, Toast.LENGTH_SHORT)
				.show();
	}
	
	
}
