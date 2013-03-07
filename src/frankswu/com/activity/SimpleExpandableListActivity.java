package frankswu.com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.apis.R;

import frankswu.com.utils.HandlerHelp;
import frankswu.com.utils.LogUtils;

/**
 *	 
 * @author frankswu
 *
 */
public abstract class SimpleExpandableListActivity extends ExpandableListActivity {

	private static final String TAG = "SimpleExpandableListActivity";

	protected ImageView ivBack = null; // 顶部返回图片按钮
	protected ExpandableListView thisView = null;
	protected ExpandableListAdapter mAdapter = null;
	protected SimpleUIHandler uiHandle = new SimpleUIHandler(this);
	
	protected static final String[] GROUP_VIEW_ITEM_TITLE = {
		"group_title1","group_title2","group_title3",
	};
	protected static final String[] CHILD_VIEW_ITEM_TITLE = {
		"item1","item2","item3",
	};
//	private static int[] itemIds =  {
//		R.id.item_1,R.id.item_2,R.id.item_img,R.id.main_menu_id
//	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void createAndStartNewHandle(Context context,String msg) {
		new SimpleUIHandler(context).execute(msg);
	}

	protected void createBackButton(Activity activity) {
//		ivBack = (ImageView) activity.findViewById(R.id.iv_back);
//		ivBack.setVisibility(View.VISIBLE);
//		ivBack.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				gobackAction();
//			}
//		});
	}


	protected List<Map<String, String>> createGroupTitleListByArray(String[] groupTitle){
		List<Map<String, String>> groupData = new ArrayList<Map<String,String>>();
		if (groupTitle != null) {
			for (String title : groupTitle) {
				Map<String,String> param = new HashMap<String, String>();
				param.put(GROUP_VIEW_ITEM_TITLE[0], title);
				groupData.add(param);
			}
		}
		return groupData;
	}
	
	protected List<Map<String, String>> createListByArray(String[] titles,String mapKey){
		List<Map<String, String>> groupData = new ArrayList<Map<String,String>>();
		if (titles != null) {
			for (String title : titles) {
				Map<String,String> param = new HashMap<String, String>();
				param.put(mapKey, title);
				groupData.add(param);
			}
		}
		return groupData;
	}

//	protected List<Map<String, String>> createListByBaseEntityList(List<BaseEntity> entities){
//		List<Map<String, String>> groupData = new ArrayList<Map<String,String>>();
//		if (entities != null) {
//			for (BaseEntity entity : entities) {
//				Map<String,String> param = new HashMap<String, String>();
//				param.put("ID", entity.getId());
//				param.put("USER_NAME", entity.getName());
//				groupData.add(param);
//			}
//		}
//		return groupData;
//	}

	
	public abstract class SimpleExpandableListAdapter extends BaseExpandableListAdapter {

		protected List<Map<String, String>> groupData = null;
		protected List<List<Map<String, String>>> childData = null;
		
		protected int groupViewLayoutRes = 0;//R.layout.main_menu_group_item ;
		protected int childViewLayoutRes = 0;//R.layout.main_menu_item;
		/**
		 * @param groupData2
		 * @param childData2
		 * @param groupPosition
		 * @param childPosition
		 * @param childViewHolder2
		 * @param convertView TODO
		 * @param parent TODO
		 */
		protected abstract View doInChildView(List<Map<String, String>> groupData2,
				List<List<Map<String, String>>> childData2, int groupPosition,
				int childPosition, SimpleViewHolder childViewHolder2, View convertView, ViewGroup parent) ;
		/**
		 * @param groupData
		 * @param groupPosition
		 * View convertView, ViewGroup parent
		 * @param convertView TODO
		 * @param parent TODO
		 */
		protected abstract View doInGroupView(List<Map<String, String>> groupData,
				int groupPosition, SimpleViewHolder groupViewHolder2, View convertView, ViewGroup parent);
		
		public SimpleExpandableListAdapter(int groupViewLayoutRes,List<Map<String, String>> groupData,
				int childViewLayoutRes,List<List<Map<String, String>>> childData ) {
			Log.d("SimpleExpandableListAdapter","SimpleExpandableListAdapter");
			this.childViewLayoutRes = childViewLayoutRes;
			this.groupViewLayoutRes = groupViewLayoutRes;
			this.childData = childData;
			this.groupData = groupData;
		}

		public SimpleExpandableListAdapter(List<Map<String, String>> groupData,
				List<List<Map<String, String>>> childData ) {
			Log.d("SimpleExpandableListAdapter","SimpleExpandableListAdapter");
			this.childData = childData;
			this.groupData = groupData;
		}

		public Object getGroup(int groupPosition) {
			Log.d("SimpleExpandableListAdapter","getGroup");
			if (groupData.get(groupPosition).containsKey(GROUP_VIEW_ITEM_TITLE[0])) {
				return groupData.get(groupPosition).get(GROUP_VIEW_ITEM_TITLE[0]);
			} else {
				return "";
			}
		}

		public int getGroupCount() {
			Log.d("SimpleExpandableListAdapter","getGroupCount");
			return groupData.size();
		}

		public long getGroupId(int groupPosition) {
			Log.d("SimpleExpandableListAdapter","getGroupId");
			return groupPosition;
		}
		
		public Object getChild(int groupPosition, int childPosition) {
			Log.d("SimpleExpandableListAdapter","getChild");
			if (childData.get(groupPosition).get(childPosition).containsKey(CHILD_VIEW_ITEM_TITLE[0])) {
				return childData.get(groupPosition).get(childPosition).get(CHILD_VIEW_ITEM_TITLE[0]);
			} else {
				return "";
			}
		}

		public long getChildId(int groupPosition, int childPosition) {
			Log.d("SimpleExpandableListAdapter","getChildId");
			return childPosition;
		}

		public int getChildrenCount(int groupPosition) {
			Log.d("SimpleExpandableListAdapter","getChildrenCount");
			if (childData != null && childData.get(groupPosition) != null) {
				return childData.get(groupPosition).size();
			}
			return 1;
		}

		public View getChildView(final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			Log.d("SimpleExpandableListAdapter","getChildView");
			View childView = null;
			try {
				childView = doInChildView(groupData,childData,groupPosition,childPosition,null, convertView, parent);
			} catch (Exception e) {
				LogUtils.logException(null, TAG, e);
			}
			Log.d("SimpleExpandableListAdapter","getChildView.end.childView[" + childView +
					"]");
			return childView;
		}
		
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			Log.d("SimpleExpandableListAdapter","getGroupView");
			View groupView = null;
//			((ExpandableListView) parent).expandGroup(groupPosition);
			try {
				groupView = doInGroupView(groupData, groupPosition, null, convertView, parent);
			} catch (Exception e) {
				if (e != null) {
					Log.d(TAG, e.getMessage());
				}
			}
			Log.d("SimpleExpandableListAdapter","getGroupView.groupView=" + groupView);
			return groupView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			Log.d("SimpleExpandableListAdapter","isChildSelectable");
			return true;
		}

		public boolean hasStableIds() {
			Log.d("SimpleExpandableListAdapter","hasStableIds");
			return true;
		}
		}
	
	public abstract class SimpleHolderExpandableListAdapter extends SimpleExpandableListAdapter {

		public SimpleHolderExpandableListAdapter(int groupViewLayoutRes,List<Map<String, String>> groupData,
				int childViewLayoutRes,List<List<Map<String, String>>> childData ) {
			super(groupViewLayoutRes, groupData, childViewLayoutRes, childData);
		}

		public SimpleHolderExpandableListAdapter(List<Map<String, String>> groupData,
				List<List<Map<String, String>>> childData ) {
			super(groupData, childData);
		}

		public View getChildView(final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			SimpleViewHolder childViewHolder;
//			long startTime = System.nanoTime();
			if (convertView == null) {
				convertView = LayoutInflater.from(SimpleExpandableListActivity.this)
					.inflate(this.childViewLayoutRes, null);
				childViewHolder = new SimpleViewHolder();
				childViewHolder.item1 = (TextView) convertView.findViewById(R.id.item_1);
//				childViewHolder.item2 = (TextView) convertView.findViewById(R.id.item_2);
//				childViewHolder.img = (ImageView) convertView.findViewById(R.id.item_img);
				childViewHolder.layout = convertView.findViewById(R.id.main_menu_id);
//				viewHolder.item3 = (TextView) convertView.findViewById(R.id.item_3);
				convertView.setTag(childViewHolder);
			} else {
				childViewHolder = (SimpleViewHolder) convertView.getTag();
			}
			try {
				doInChildView(groupData,childData,groupPosition,childPosition,childViewHolder, convertView, parent);
			} catch (Exception e) {
				if (e != null) {
					Log.d(TAG, e.getMessage());
				}
			}
			View.OnClickListener childViewClickListener = getChildViewItemOnClickListener();
			if (childViewClickListener != null) {
				convertView.setOnClickListener(childViewClickListener);
			}
//			String s = String.format("getconvertView.position[%d]time[%d]", groupPosition,System.nanoTime() - startTime);
//			Log.d("test",s);
			return convertView;
		}
		
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			SimpleViewHolder groupViewHolder;
			if (convertView == null) {
				convertView = LayoutInflater.from(SimpleExpandableListActivity.this)
					.inflate(groupViewLayoutRes, null);
				groupViewHolder = new SimpleViewHolder();
				// TODO 
//				groupViewHolder.item1 = (TextView) convertView.findViewById(R.id.txtGroupTitle);
				convertView.setTag(groupViewHolder);
			} else {
				groupViewHolder = (SimpleViewHolder) convertView.getTag();
			}
			try {
				doInGroupView(groupData,groupPosition,groupViewHolder, convertView, parent);
				((ExpandableListView) parent).expandGroup(groupPosition);
			} catch (Exception e) {
				if (e != null) {
					Log.d(TAG, e.getMessage());
				}
			}
			return convertView;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public boolean hasStableIds() {
			return true;
		}
		

	}

	/**
	 * set back action by back button on top
	 */
	protected abstract void gobackAction() ;
	
	protected static class SimpleViewHolder {
		public TextView item1;
		public TextView item2;
		public ImageView img;
		public View layout;
		public SimpleViewHolder() {}
	}

	/**
	 * @return
	 */
	public OnClickListener getChildViewItemOnClickListener() {
		// you can override or not
		return null;
	}

	public class SimpleUIHandler extends HandlerHelp {

		public SimpleUIHandler(Context context) {
			super(context);
		}

		@Override
		public void doMessage(Message msg) {
			doMessageInUiHandler(msg);
		}

		@Override
		public void doTask(Message msg) throws Exception {
			doTaskInUiHandler(msg);
		}
	}

	/**
	 * @param msg
	 */
	protected abstract void doMessageInUiHandler(Message msg) ;


	/**
	 * @param msg
	 */
	protected abstract void doTaskInUiHandler(Message msg) ;


	protected abstract void initComponent() ;
	
	protected abstract void initData() ;
	
}
