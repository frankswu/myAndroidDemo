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

package com.example.android.apis.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.apis.R;

public class Gallery1 extends Activity {

	protected static final String TAG = "Gallery1";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_1);

		// Reference the Gallery view
		Gallery g = (Gallery) findViewById(R.id.gallery);
		final List<String> l = readSDCard();
		// Set the adapter to our custom adapter (below)
		g.setAdapter(new ImageAdapter(this,l));

		// Set a item click listener, and just Toast the clicked position
		g.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
//				Log.d(TAG,l.get(position));
				Toast.makeText(Gallery1.this, "onItemClick=" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});
		g.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Log.d(TAG,"onFocusChange");
				Toast.makeText(Gallery1.this, "onFocusChange", Toast.LENGTH_SHORT)
						.show();
				
			}
		});

		// We also want to show context menu for longpressed items in the
		// gallery
		registerForContextMenu(g);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.add(R.string.gallery_2_text);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Toast.makeText(this, "Longpress: " + info.position, Toast.LENGTH_SHORT)
				.show();
		return true;
	}

	private List<String> readSDCard() {
		List<String> tFileList = new ArrayList<String>();

		// It have to be matched with the directory in SDCard
		File dir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//		File f = new File("/D:/android/sdcard2/");

		File[] files = dir.listFiles();
		if (files != null ) {
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				/*
				 * It's assumed that all file in the path are in supported type
				 */
				tFileList.add(file.getPath());
			}
		}

		return tFileList;
	}

	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
//	    private List<String> fileList;  

		public ImageAdapter(Context c, List<String> list) {
			mContext = c;
//			fileList = list;
			// See res/values/attrs.xml for the <declare-styleable> that defines
			// Gallery1.
			TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.Gallery1_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
//			if (fileList != null) {
//				return fileList.size();
//			} else {
//				return 0;
//			}
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
//			Bitmap bmp = BitmapFactory.decodeFile(fileList.get(position).toString());
//			i.setImageBitmap(bmp);
			i.setImageResource(mImageIds[position]);
			i.setScaleType(ImageView.ScaleType.FIT_CENTER);
			 i.setLayoutParams(new Gallery.LayoutParams(136, 88));
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			// The preferred Gallery item background
			i.setBackgroundResource(mGalleryItemBackground);

			return i;
		}

		private Context mContext;

		private Integer[] mImageIds = { R.drawable.gallery_photo_1,
				R.drawable.gallery_photo_2, R.drawable.gallery_photo_3,
				R.drawable.gallery_photo_4, R.drawable.gallery_photo_5,
				R.drawable.gallery_photo_6, R.drawable.gallery_photo_7,
				R.drawable.gallery_photo_8 };
	}

}
