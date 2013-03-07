/*
. * Copyright (C) 2007 The Android Open Source Project
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.apis.R;

/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class ExpandableList1 extends ExpandableListActivity {

	private static final String TAG = "ExpandableList1";

	ExpandableListAdapter mAdapter;
	private static final String NAME = "NAME";
	private static final String IS_EVEN = "IS_EVEN";
	List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
	List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
	private EditText ettRemark ;
	// public OnClickListener itemOnClickListerner =

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (int i = 0; i < 5; i++) {
			Map<String, String> curGroupMap = new HashMap<String, String>();
			groupData.add(curGroupMap);
			curGroupMap.put(NAME, "Group " + i);
			curGroupMap.put(IS_EVEN, (i % 2 == 0) ? "This group is even"
					: "This group is odd");

			List<Map<String, String>> children = new ArrayList<Map<String, String>>();
			if (i == 0) {
				Map<String, String> curChildMap = new HashMap<String, String>();
				children.add(curChildMap);
				// curChildMap.put(NAME, "Child " + j);
				// curChildMap.put(IS_EVEN, (j % 2 == 0) ? "This child is even"
				// : "This child is odd");
			} else {
				for (int j = 0; j < 5; j++) {
					Map<String, String> curChildMap = new HashMap<String, String>();
					children.add(curChildMap);
					curChildMap.put(NAME, "Child " + j);
					curChildMap.put(IS_EVEN,
							(j % 2 == 0) ? "This child is even"
									: "This child is odd");
				}
			}
			childData.add(children);
		}

		// Set up our adapter
		mAdapter = new MyExpandableListAdapter();
//		getExpandableListView().get
		getExpandableListView().setGroupIndicator(getResources().getDrawable(R.drawable.my_group_statelis));
		getExpandableListView().setOnItemSelectedListener(
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position == 0) {
							// listView.setItemsCanFocus(true);

							// Use afterDescendants, because I don't want the
							// ListView to steal focus
							parent.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
							ettRemark.requestFocus();
						} else {
							if (!parent.isFocused()) {
								// listView.setItemsCanFocus(false);

								// Use beforeDescendants so that the EditText
								// doesn't re-take focus
								parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
								parent.requestFocus();
							}
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						parent.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

					}
				});
		getExpandableListView().setCacheColorHint(0);
		// getExpandableListView()
		getExpandableListView().setOnChildClickListener(
				new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Toast.makeText(
								ExpandableList1.this,
								"setOnChildClickListener.groupPosition["
										+ groupPosition + "]childPosition["
										+ childPosition + "]",
								Toast.LENGTH_LONG).show();
						Log.d("demo", "parent[" + parent.getId()
								+ "]groupPosition[" + groupPosition
								+ "]childPosition[" + childPosition + "]id["
								+ id + "]");
						return false;
					}
				});
		setListAdapter(mAdapter);
		registerForContextMenu(getExpandableListView());
		// getExpandableListView().expandGroup(0);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Sample menu");
		menu.add(0, 0, 0, R.string.expandable_list_sample_action);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item
				.getMenuInfo();

		String title = ((TextView) info.targetView).getText().toString();

		int type = ExpandableListView
				.getPackedPositionType(info.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView
					.getPackedPositionChild(info.packedPosition);
			Toast.makeText(
					this,
					title + ": Child " + childPos + " clicked in group "
							+ groupPos, Toast.LENGTH_SHORT).show();
			return true;
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);
			Toast.makeText(this, title + ": Group " + groupPos + " clicked",
					Toast.LENGTH_SHORT).show();
			return true;
		}

		return false;
	}

	/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids. Each
	 * photo is displayed as an image. This adapter supports clearing the list
	 * of photos and adding a new photo.
	 * 
	 */
	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
		// Sample data set. children[i] contains the children (String[]) for
		// groups[i].
		// private String[] groups = { "People Names", "Dog Names", "Cat Names",
		// "Fish Names" };
		// private String[][] children = {
		// { "Arnold", "Barry", "Chuck", "David" },
		// { "Ace", "Bandit", "Cha-Cha", "Deuce" },
		// { "Fluffy", "Snuggles" },
		// { "Goldy", "Bubbles" }
		// };

		public Object getChild(int groupPosition, int childPosition) {
			// return children[groupPosition][childPosition];
			return childData.get(groupPosition).get(childPosition).get(NAME);
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			// return children[groupPosition].length;
			return childData.get(groupPosition).size();
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 64);

			TextView textView = new TextView(ExpandableList1.this);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			// Set the text starting position
			textView.setPadding(36, 0, 0, 0);
			return textView;
		}

		public View getChildView(final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				final ViewGroup parent) {
			// // TextView textView = getGenericView();
			// // textView.setText(getChild(groupPosition,
			// childPosition).toString());
			// return textView;
			View childView = null;
			switch (groupPosition) {
			case 0:
				childView = LayoutInflater.from(ExpandableList1.this).inflate(
						R.layout.worktask_assigned2person_item, null);
				ettRemark = (EditText) childView.findViewById(R.id.item_1);
				// final String remark = ettRemark.getText().toString().trim();
				Log.d(TAG, "remark");
//				ettRemark.requestFocus();
				ettRemark.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(View v, int keyCode, KeyEvent event) {
						Log.d(TAG, "remark.onKey");

						if (event.getAction() == KeyEvent.ACTION_DOWN) {
							switch (keyCode) {
							case KeyEvent.KEYCODE_DPAD_CENTER:

							case KeyEvent.KEYCODE_BACK:
							case KeyEvent.KEYCODE_ENTER:
								String remark = ettRemark.getText().toString()
										.trim();
								Log.d(TAG, "remark[" + remark + "]");
								ettRemark.setText(remark);
								ExpandableList1.this.onBackPressed();
								return true;
							}
						}

						return false;
					}
				});
				// ettRemark.setOnFocusChangeListener(new
				// OnFocusChangeListener() {
				//
				// @Override
				// public void onFocusChange(View v, boolean hasFocus) {
				// Log.d(TAG, "onFocusChange.remark[" + remark + "]");
				// ettRemark.setText(remark);
				// }
				// });
				// childView.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// Log.d(TAG, "onclickRemak");
				// ettRemark.setFocusable(true);
				// // ettRemark.setFocusableInTouchMode(true);
				// ettRemark.requestFocus();
				// }
				// });

				break;

			default:
				childView = LayoutInflater.from(ExpandableList1.this).inflate(
						R.layout.unfinish_task_item, null);
				// childView.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// Toast.makeText(ExpandableList1.this,
				// "itemOnClickListener.groupPosition[" + groupPosition +
				// "]childPosition[" + childPosition +
				// "]", Toast.LENGTH_LONG).show();
				// Log.d("demo","parent[" + parent.getId() +
				// "]groupPosition[" + groupPosition +
				// "]childPosition[" + childPosition +
				// // "]id[" + id +
				// "]");
				// }
				// });
				TextView time = (TextView) childView.findViewById(R.id.txtTime);
				time.setText(childData.get(groupPosition).get(childPosition)
						.get(NAME));
				TextView dept = (TextView) childView
						.findViewById(R.id.txtDepartment);
				dept.setText(childData.get(groupPosition).get(childPosition)
						.get(IS_EVEN));
				break;
			}

			return childView;
		}

		public Object getGroup(int groupPosition) {
			// return groups[groupPosition];
			return groupData.get(groupPosition).get(NAME);
		}

		public int getGroupCount() {
			// return groups.length;
			return groupData.size();
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(ExpandableList1.this).inflate(android.R.layout.simple_expandable_list_item_1,
						 null);
			}
//			Log.d(TAG, "getGroupView.class=" +parent.getLayoutParams().getClass());
//			ViewGroup.LayoutParams  layoutParams = parent.getLayoutParams();
//			layoutParams.height = 1;
//			parent.setLayoutParams(layoutParams);
			// if (groupPosition == 1) {
			// convertView.setVisibility(View.INVISIBLE);
			// }
			// View groupView =
			// LayoutInflater.from(ExpandableList1.this).inflate(R.layout.unfinish_task_item,
			// null);
			
//			TextView textView = getGenericView();
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			textView.setVisibility(View.INVISIBLE);
			textView.setText(getGroup(groupPosition).toString());
//			parent.setVisibility(View.INVISIBLE);
			((ExpandableListView) parent).expandGroup(groupPosition);
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}

	}
}
