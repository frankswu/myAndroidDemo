package frankswu.com.utils;

import java.util.Map;

public abstract class BeanUtils {

//	public static void setStringByMap(String str,Map<String,String> params,String mapKey) {
//		if (str != null && params != null && mapKey != null) {
//			if (params.containsKey(mapKey)) {
//				str = params.get(mapKey).toString();
//			}
//		}
//	}

	public static void setStringByMap(String str,Map<String,Object> params,String mapKey) {
		if (str != null && params != null && mapKey != null) {
			if (params.containsKey(mapKey)) {
				if (params.get(mapKey) != null) {
					str = params.get(mapKey).toString();
				}
			}
		}
	}
	public static String getStringByMap(Map<String,Object> params,String mapKey) {
		if (params != null && mapKey != null) {
			if (params.containsKey(mapKey)) {
				return  params.get(mapKey).toString();
			}
		}
		return "";
	}
	
}
