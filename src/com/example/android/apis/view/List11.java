/*
 * Copyright (C) 2008 The Android Open Source Project
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

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import frankswu.com.activity.SimpleListActivity;
import frankswu.com.utils.LogUtils;

/**
 * This example shows how to use choice mode on a list. This list is 
 * in CHOICE_MODE_MULTIPLE mode, which means the items behave like
 * checkboxes.
 */
public class List11 extends SimpleListActivity {
//extends ListActivity {
	private static final String TAG = "List11" ;

    @SuppressWarnings("unchecked")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, GENRES));
//        setListAdapter(new ArrayAdapter<String>(this,R.layout.worktaskcheck_item_view, GENRES));
        setListAdapter(new List11Adapter(this, android.R.layout.simple_list_item_multiple_choice, GENRES));        
//		<String><String>(this,android.R.layout.simple_list_item_multiple_choice, GENRES));
        final ListView listView = getListView();

        listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }


    private static final String[] GENRES = new String[] {
        "Action", "Adventure", "Animation", "Children", "Comedy", "Documentary", "Drama",
        "Foreign", "History", "Independent", "Romance", "Sci-Fi", "Television", "Thriller"
    };

    class List11Adapter extends  SimpleListActivity.SimpleArrayAdapter<String> {

    	public List11Adapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View doInGetView(String t2, int llyRes, int position,
				View convertView, ViewGroup parent) {
			LogUtils.logObjectArray(TAG, "List11Adapter.doInGetView", position+"",convertView,parent,t2);
			View view ;
			if (convertView == null) {
				view = LayoutInflater.from(List11.this).inflate(llyRes, parent, false);
			} else {
				view = convertView;
			}
			TextView textView = (TextView)view;
			textView.setText(t2.toString());
			return view;
		}
    	
    }

	@Override
	protected void gobackAction() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void doMessageInUiHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void doTaskInUiHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initComponent() {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}
	
	class SimpleArrayAdapter extends ArrayAdapter {
		
		private int llyRes;
		public SimpleArrayAdapter(Context context, int textViewResourceId,
				Object[] objects) {
			super(context, textViewResourceId, objects);
			llyRes = textViewResourceId;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(List11.this).inflate(llyRes, parent, false);
			
			return convertView;
		}
		
	}
}
