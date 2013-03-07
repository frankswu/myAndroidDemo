package frankswu.com.utils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;


//import com.genertech.framework.bean.ExceptionLogBean;
//import com.genertech.framework.service.ExceptionLogService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


/**
 * 
 * 
 * @author frankswu
 *
 */
public class LogUtils {

	public static void logOnActivityResult(String tag,int requestCode, int resultCode, Intent data){
		Log.d(tag, "onActivityResult[requestCode]" + requestCode
				+ "[resultCode]" + resultCode );
		if (data != null) {
			Bundle bundle = data.getExtras();
			if (bundle != null && !bundle.isEmpty()) {
				StringBuffer result = new StringBuffer();
				Set<String> datakey= bundle.keySet();
				for (String key : datakey) {
					result.append("[" + key + ":").append(bundle.get(key)).append("]");
				}
				Log.d(tag, "onActivityResult[data.getExtras()]" + result.toString());
			}else{
				Log.d(tag, "onActivityResult[data.getExtras()] is null or empty" );
			}
		}else{
			Log.d(tag, "onActivityResult[data] is null or empty" );
		}
	}

	public static void logIntent(String tag,Intent data){
		Log.d(tag, "logIntent");
		if (data != null) {
			Bundle bundle = data.getExtras();
			if (bundle != null && !bundle.isEmpty()) {
				StringBuffer result = new StringBuffer();
				Set<String> datakey= bundle.keySet();
				for (String key : datakey) {
					result.append("[" + key + ":").append(bundle.get(key)).append("]");
				}
				Log.d(tag, "logIntent[data.getExtras()]" + result.toString());
			}else{
				Log.d(tag, "logIntent[data.getExtras()] is null or empty" );
			}
		}else{
			Log.d(tag, "logIntent[data.getExtras()] is null or empty" );
		}
	}
	
	
	public static void logBundle(String tag,Bundle bundle){
		if (bundle != null && !bundle.isEmpty()) {
			StringBuffer result = new StringBuffer();
			Set<String> datakey= bundle.keySet();
			for (String key : datakey) {
				result.append("[" + key + ":").append(bundle.get(key)).append("]");
			}
			Log.d(tag, "[Bundle]" + result.toString());
		}else{
			Log.d(tag, "[Bundle] is null or empty" );
		}
	}
	
	public static void logStringArray(String tag, String method, String...strings ){
		StringBuffer sb = new StringBuffer();
		if (strings != null) {
			for (int i = 0; i < strings.length; i++) {
				sb.append("[").append(i).append(":").append(strings[i]).append("]");
			}
			Log.d(tag,method + sb.toString());
		} else {
			Log.d(tag,method + " strings is null");
		}
	}

	public static void logObjectArray(String tag, String method, Object...objects ){
		StringBuffer sb = new StringBuffer();
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				if (objects != null) {
					sb.append("[").append(i).append(":").append(objects[i]).append("]");
				} else {
					sb.append("[").append(i).append(":null]");
				}
			}
			Log.d(tag,method + sb.toString());
		} else {
			Log.d(tag,method + " strings is null");
		}
	}

	
	public static void logStringList(String tag, String method,List<String> strings ){
		StringBuffer sb = new StringBuffer();
		if (strings != null) {
			for (int i = 0; i < strings.size(); i++) {
				sb.append("[").append(i).append(":").append(strings.get(i)).append("]");
			}
		}
		Log.d(tag,method + sb.toString());
	}
	
	/**
	 *  TODO ADD 增加错误日志保存在本地表中
	 * @param context
	 * 	if context is null , don't have save in db
	 * @param tag
	 * @param e
	 */
	public static void logException(Context context,String tag, Exception e) {
		if (e != null) {
			e.printStackTrace();
			
//			if (context != null && Constant.IS_SAVE_EXCEPTION_LOG) {
//				ExceptionLogBean bean = new ExceptionLogBean();
//				bean.setLevel(ExceptionLogBean.LogLevel.ERROR.name());
//				bean.setTag(tag);
//				if (e.getMessage() != null) {
//					bean.setContext(e.getMessage());
//				}
//				ExceptionLogService.insert(context, bean );
				
//			} else {
				if (e.getMessage() != null) {
					Log.e(tag,e.getMessage());
//				} 
			}
		}
	}

	public static void logExceptionAndOther(Context context,String tag,Exception e, String other) {
		Log.d(tag,other);
		logException(context, tag, e);
	}

	
	public static void logObjectArray2File(String tag, String fileName,String method, Object...objects ){
		String context = getStringFromObjects(method, objects);
		Log.d(tag,context );
		String logFileName = null;
		String dataLocString = new Date().toLocaleString();
		String strData = dataLocString.substring(0,10).replace(" ", "");
		if (!TextUtils.isEmpty(fileName)) {
			logFileName = FileHelper.getSDCardDir() + "/myBaiduDemo/log/"+ fileName + strData +".log";
		} else {
			logFileName = FileHelper.getSDCardDir() + "/myBaiduDemo/log/"+ strData  + ".log";
		}
		FileHelper.appendContent2file(new File(logFileName ), dataLocString+","+context);
//		StringBuffer sb = new StringBuffer();
//		if (objects != null) {
//			for (int i = 0; i < objects.length; i++) {
//				if (objects != null && objects[i] != null) {
//					sb.append("[").append(i).append(":").append(objects[i].toString()).append("]");
//				} else {
//					sb.append("[").append(i).append(":null]");
//				}
//			}
//			Log.d(tag,method + sb.toString());
//		} else {
//			Log.d(tag,method + " strings is null");
//		}
	}

	private static String getStringFromObjects(String method,Object...objects ) {
		StringBuffer sb = new StringBuffer();
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				if (objects != null && objects[i] != null) {
					sb.append("[").append(i).append(":").append(objects[i].toString()).append("]");
				} else {
					sb.append("[").append(i).append(":null]");
				}
			}
		} else {
			sb.append(" strings is null!");
		}
		return method+sb.toString();
	}
	
}
