package frankswu.com.utils;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


/**
 * 
 * @author frankswu
 *
 */
public class ViewUtils {

	
	private static final String TAG = "ViewUtils";

	/**
	 * @param parent
	 * @param params
	 * @param viewIds
	 * @param mapKeys
	 */
	public static void setViewTextFromMap(View parent ,Map<String,String> params,int[] viewIds, String[] mapKeys){
		if (parent != null && params != null ) {
			try {
				for (int i = 0; i < viewIds.length ; i++ ) {
					TextView v = (TextView)parent.findViewById(viewIds[i]);
					if (v != null) {
						String text = "";
						if (i < mapKeys.length && params.containsKey(mapKeys[i])) {
							text = params.get(mapKeys[i]);
						}
						v.setText(text);
					}
				}
			} catch (Exception e) {
				LogUtils.logException(null, TAG, e);
			}
		}
	}

	/**
	 * @param parent
	 * @param params
	 * @param viewIds
	 * @param mapKeys
	 */
	public static void setViewTextFromMap(Activity parent ,Map<String,String> params,int[] viewIds, String[] mapKeys){
		if (parent != null && params != null ) {
			try {
				for (int i = 0; i < viewIds.length ; i++ ) {
					TextView v = (TextView)parent.findViewById(viewIds[i]);
					if (v != null) {
						String text = "";
						if (i < mapKeys.length && params.containsKey(mapKeys[i])) {
							text = params.get(mapKeys[i]);
						}
						v.setText(text);
						v.setVisibility(View.VISIBLE);
					}
				}
			} catch (Exception e) {
				LogUtils.logException(null, TAG, e);
			}
		}
	}
	
	public static TextView[] getTextViewArrayFromIds(View parent ,int[] viewIds){
		TextView[] texts = null;
		if (parent != null && viewIds != null ) {
			texts = new TextView[viewIds.length];
			try {
				for (int i = 0; i < viewIds.length ; i++ ) {
					TextView v = (TextView)parent.findViewById(viewIds[i]);
					if (v != null) {
						texts[i] = v;
					}
				}
			} catch (Exception e) {
				LogUtils.logException(null, TAG, e);
			}
		}
		return texts;
	}
	
	public static TextView[] getTextViewArrayFromIds(Activity parent ,int[] viewIds){
		TextView[] texts = null;
		if (parent != null && viewIds != null ) {
			texts = new TextView[viewIds.length];
			try {
				for (int i = 0; i < viewIds.length ; i++ ) {
					TextView v = (TextView)parent.findViewById(viewIds[i]);
					if (v != null) {
						texts[i] = v;
					}
				}
			} catch (Exception e) {
				LogUtils.logException(null, TAG, e);
			}
		}
		return texts;
	}

	public static void setTextViewsVisible(TextView[] textViews,int visible ) {
		if (textViews != null) {
			for (TextView textView : textViews) {
				if (textView != null) {
					textView.setVisibility(visible);
				}
			}
		}
	}
	
	public static void setTextViewsVisible(Activity parent ,int[] viewIds,int visible) {
		TextView[] textViews = getTextViewArrayFromIds(parent, viewIds);
		setTextViewsVisible(textViews, visible);
	}

	public static void setEditTextViewsEditableIsFalse(EditText[] editTexts) {
		if (editTexts != null) {
			for (EditText editText : editTexts) {
				lockUnlock(editText, true);
			}
		}
	}

	public static void setEditTextViewsEditableIsTrue(EditText[] editTexts) {
		if (editTexts != null) {
			for (EditText editText : editTexts) {
				lockUnlock(editText, false);
			}
		}
	}
	
	/**
	 * 配合setViewHolderTextViewsByMap
	 * 
	 * @param context
	 * @param convertView
	 * @param childViewLayoutRes
	 * @param viewIds
	 * @return
	 */
	public static ViewHolder createViewHolderByIds(Context context,View convertView, int childViewLayoutRes,int[] viewIds ) {
		LogUtils.logStringArray(TAG, "createViewHolderByIds", "");
		ViewHolder viewHolder = new ViewUtils.ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(childViewLayoutRes, null);
			Log.d(TAG,"createViewHolderByIds.convertView[" + convertView +
					"]");
			viewHolder.convertView = convertView;
			if (viewIds != null) {
				viewHolder.holdTextViews = new TextView[viewIds.length];
				for (int i = 0; i < viewIds.length; i++) {
					viewHolder.holdTextViews[i] = (TextView) convertView
							.findViewById(viewIds[i]);
				}
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return viewHolder;
	}
	
	/**
	 * 配合createViewHolderByIds
	 * 
	 * @param viewHolder
	 * @param params
	 * @param mapKeys
	 */
	public static  void setViewHolderTextViewsByMap(ViewHolder viewHolder,Map<String,String> params, String[] mapKeys) {
		if (viewHolder != null && viewHolder.holdTextViews != null && mapKeys != null) {
			for (int i = 0; i < viewHolder.holdTextViews.length; i++) {
				String text = "";
				if (i < mapKeys.length && params.containsKey(mapKeys[i])) {
					text = params.get(mapKeys[i]);
				}
				viewHolder.holdTextViews[i].setText(text);
			}
		}

	}
	
    /**  
     * Method which locks and unlocks editText component  
     * @param value our boolean value which using in or if operator  
     */    
    private static void lockUnlock(EditText editText,final boolean value) {    
    	if (editText != null) {
          editText.setFilters(new InputFilter[] { new InputFilter() {
            @Override    
            public CharSequence filter(CharSequence source, int start,    
                    int end, Spanned dest, int dstart, int dend) {    
					if (!value) {
						return source.length() < 1 ? dest.subSequence(dstart, dend) : "";
					} else {
						return null;
					}
            }    
          }});    

    	}
    }

	
	public static class ViewHolder {
		public ViewHolder() {
		}
		public View convertView;
		TextView[] holdTextViews ;
	}
}
