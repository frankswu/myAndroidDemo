package frankswu.com.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 *  转化json字符到数值工具类
 *  
 * @author franks wu
 *
 */
public class JsonBeanUtils {

	/**
	 * @param json
	 * @param key
	 * @return 如果没有该key，则返回""�?
	 * @throws JSONException
	 */
	public static String getJsonStrValue(JSONObject json, String key) throws JSONException {
		if (json != null) {
			return json.isNull(key)?"":json.getString(key);
		}
		return null;
	}

}
