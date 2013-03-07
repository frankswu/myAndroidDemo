package frankswu.com.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import frankswu.com.utils.HandlerHelp;
import frankswu.com.utils.LogUtils;



/**
 * 自定义ListActivity
 * ListView的id必须是android自带的android:id="@android:id/list"
 * 
 * @author frankswu
 *
 */
public abstract class SimpleListActivity extends ListActivity {

	private static final String TAG = "SimpleListActivity";

//	private ArrayAdapter<String> mAdapter;
	private ArrayList<String> listData = new ArrayList<String>();

	protected ImageView ivBack = null; // 顶部返回图片按钮
	protected ListView theListView = null;
//	protected ExpandableListAdapter mAdapter = null;
	protected SimpleUIHandler uiHandle = new SimpleUIHandler(this);
	protected AlertDialog dialog = null;
	
//	protected static final String[] GROUP_VIEW_ITEM_TITLE = { "group_title1",
//			"group_title2", "group_title3", };
	protected static final String[] VIEW_ITEM_TITLE = { "item1", "item2",
			"item3", };

	// private static int[] itemIds = {
	// R.id.item_1,R.id.item_2,R.id.item_img,R.id.main_menu_id
	// };
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

	protected List<Map<String, String>> createListByArray(String[] titles,
			String mapKey) {
		List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		if (titles != null) {
			for (String title : titles) {
				Map<String, String> param = new HashMap<String, String>();
				param.put(mapKey, title);
				groupData.add(param);
			}
		}
		return groupData;
	}


	public abstract class SimpleArrayAdapter<T> extends ArrayAdapter {
		private int llyRes;
		private T[] arrayBean;
		private List<T> listBean;
		private boolean isArrayBean = false;
				
		public SimpleArrayAdapter(Context context, int textViewResourceId,T[] objects) {
			super(context, textViewResourceId, objects);
			llyRes = textViewResourceId;
			arrayBean = objects;
			isArrayBean = true;
		}

		public SimpleArrayAdapter(Context context, int textViewResourceId,List<T> objects) {
			super(context, textViewResourceId, objects);
			llyRes = textViewResourceId;
			listBean = objects;
			isArrayBean = false;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LogUtils.logObjectArray(TAG, "getView", position+"",llyRes,convertView,parent);
			View view = null;
			try {
				T bean = null;
				if (isArrayBean) {
					bean = arrayBean[position];
				} else {
					bean = listBean.get(position);
				}
				LogUtils.logObjectArray(TAG, "getView.bean", bean.toString());
				view = doInGetView(bean, llyRes,position, convertView,  parent);
			} catch (Exception e) {
				LogUtils.logException(SimpleListActivity.this, TAG, e);
			}
			LogUtils.logObjectArray(TAG, "getView.view", view);
			
			return view ;
		}
		
		public void setData(T[] objects) {
			arrayBean = objects;
		}

		public void setData(List<T> objects) {
			listBean = objects;
		}

		public abstract View doInGetView(T bean, int llyRes,int position, View convertView,ViewGroup parent); 
		
	}
	

	/**
	 * set back action by back button on top
	 */
	protected abstract void gobackAction();

	protected static class SimpleViewHolder {
		public TextView item1;
		public TextView item2;
		public ImageView img;
		public View layout;

		public SimpleViewHolder() {
		}
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dialog != null ) {
			dialog.dismiss();
		}
	}

	/**
	 * @param msg
	 */
	protected abstract void doMessageInUiHandler(Message msg);

	/**
	 * @param msg
	 */
	protected abstract void doTaskInUiHandler(Message msg);

	protected abstract void initComponent();

	protected abstract void initData();

}
