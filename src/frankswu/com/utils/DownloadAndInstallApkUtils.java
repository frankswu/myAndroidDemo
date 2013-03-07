package frankswu.com.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * 
 * 
 * @author frankswu
 * 
 */
public class DownloadAndInstallApkUtils {

	private static final String TAG = "DownloadAndInstallApkUtils";

	public static String getDownloadPath() {

		String filePath1 = Environment.getExternalStorageDirectory() + "/";
		File file1 = new File(filePath1 + "download/");
//		File file2 = new File(filePath1 + "ms.file");
		try {
			if (!file1.exists()) {
				file1.mkdir();
			}
//			file2.createNewFile();
			return file1.getPath() + "/";
//			if (file2.exists()) {
//				file2.delete();
//			}
		} catch (Exception e) {
			LogUtils.logException(null, TAG, e);
		}

		return null;
	}

	/**
	 * getFileName
	 * TODO getDownloadApkFileName
	 * @param url
	 * @return
	 */
	public static String getFileName(String url) {
		String file = url.substring(url.lastIndexOf("/") + 1, url.length());
		if (file.endsWith("apk")) {
			return file;
		} else {
			return "download.apk";
		}
	}

	public static void installAPK(String fileName, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}	
	
}
