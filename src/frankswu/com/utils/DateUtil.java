package frankswu.com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 此类是用于操作时�?
 * @author lucky.lan
 * @createDate 2012/07/23
 */
public class DateUtil {

	// 默认日期格式：yyyy-MM
	public static final String DEFAULT_SHORT_DATE_PATTERN = "yyyy-MM";
	// 默认日期格式：yyyy-MM-dd
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	// 默认时间格式：yyyy-MM-dd HH:mm:ss
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	// 默认时间格式：yyyy-MM-dd HH:mm
	public static final String DEFAULT_DATETIME_SPECIAL_PATTERN = "yyyy-MM-dd HH:mm";

	// 默认时间戳格式，到毫秒yyyy-MM-dd HH:mm:ss SSS
	public static final String DEFAULT_DATEDETAIL_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	
	// 默认时间格式：yyyy-MM-dd HH:mm:ss
	public static final String DEFAULT_DATETIME_HM_PATTERN = "HH:mm";

	/**
	 * 取相应格式的时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getFormatTime(String format) {
		TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(timeZone);
		String time = sdf.format(new Date());
		return time;
	}
	
	/**
	 * 当前日期与指定日期进行比�?
	 * data 格式�?HH:mm
	 * 如果参数时间早于当前时间;则返回true.反之则返回false
	 */
	public static boolean compareCurrentDate(String date){
		
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		Date currentDate = new Date();
		String compareDate = sdf.format(currentDate)+ " "+ date;
		sdf = new SimpleDateFormat(DEFAULT_DATETIME_SPECIAL_PATTERN);
		try {
			return currentDate.after(sdf.parse(compareDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static String getCurrentDateByFormatDefaultDatetimeSpecial(){
		return 	new SimpleDateFormat(DEFAULT_DATETIME_SPECIAL_PATTERN).format(new Date());
	}
	public static String getCurrentDateByFormatDefaultShortDate(){
		return 	new SimpleDateFormat(DEFAULT_SHORT_DATE_PATTERN).format(new Date());
	}
	public static String getCurrentDateByFormatDefaultDate(){
		return 	new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(new Date());
	}

	public static String getCurrentDateByFormatDefaultDatetime(){
		return 	new SimpleDateFormat(DEFAULT_DATETIME_PATTERN).format(new Date());
	}
	
}
