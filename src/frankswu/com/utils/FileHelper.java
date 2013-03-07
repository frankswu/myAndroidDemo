package frankswu.com.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


public class FileHelper {
	private static FileHelper filehelper;
	private static final String tag = "FileHelper";
	private static final String TAG = "FileHelper";

	public static FileHelper getInstance() {
		Object object = new Object();
		synchronized (object) {
			if (filehelper == null) {
				filehelper = new FileHelper();
			}
		}
		return filehelper;
	}

	public static void copyDB2SDCard(String dbfilepath, String sdcarddirStr) {
		File sdfiledir = new File(sdcarddirStr);
		if (!sdfiledir.exists()) {
			sdfiledir.mkdirs();
		}

		int lastslashindex = dbfilepath.lastIndexOf('/');
		String dbfname = dbfilepath.substring(lastslashindex + 1);
		/**
		 * dbfile exist
		 */
		String sdcardfname = sdcarddirStr + dbfname;
		InputStream is = null;
		OutputStream fos = null;
		try {
			File sdcardFile = null;
			if (new File(dbfilepath).exists()) {
				sdcardFile = new File(sdcardfname);
				sdcardFile.delete();
				sdcardFile.createNewFile();
				System.out
						.println("---------------------------------------- start copy ----------------------------------------");
				is = new FileInputStream(dbfilepath);
				fos = new FileOutputStream(sdcardFile);
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				System.out
						.println("---------------------------------------- copy finished ----------------------------------------");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static boolean writefile2another(InputStream istream,
			OutputStream ostream) {
		if (istream == null)
			throw new RuntimeException("could not find inputstream!!!");

		if (ostream == null)
			throw new RuntimeException("could not find outputstream!!!");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(istream);
			bos = new BufferedOutputStream(ostream);
			int len = -1;
			byte[] buffer = new byte[4096];
			while ((len = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeIOStream(bis, bos);
		}
	}

	public static boolean writefile2another(File onefile, File anotherfile) {
		InputStream istream = null;
		OutputStream ostream = null;
		boolean flag = false;
		File anotherdir = anotherfile.getParentFile();
		if (!anotherdir.exists()) {
			anotherdir.mkdirs();
		}
		try {
			if (!anotherfile.exists()) {
				flag = anotherfile.createNewFile();
			} else
				flag = true;
			if (!flag) {
				return flag;
			}
			istream = new FileInputStream(onefile);
			ostream = new FileOutputStream(anotherfile);
			return writefile2another(istream, ostream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		} finally {
			closeIOStream(istream, ostream);
		}
	}

	public static boolean appendContent2file(File file0, String content) {
		File anotherdir = file0.getParentFile();
		OutputStream ostream = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		boolean flag = true;
		if (!anotherdir.exists()) {
			anotherdir.mkdirs();
		}
		try {
			if (!file0.exists()) {
				flag = file0.createNewFile();
			} else
				flag = true;
			if (!flag) {
				return flag;
			}
			fw = new FileWriter(file0, true);
			bw = new BufferedWriter(fw, 8 * 1024);
			if (content != null) {
				bw.write(content);
				bw.write("\r\n");
				bw.flush();
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			closeIOStream(fw, bw);
			closeIOStream(null, ostream);
		}
	}

	public static void closeIOStream(FileWriter fw, BufferedWriter bw) {
		try {
			if (bw != null) {
				bw.close();
			}

			if (fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	protected static void closeIOStream(InputStream istream,
			OutputStream ostream) {
		try {
			if (istream != null) {
				istream.close();
			}

			if (ostream != null) {
				ostream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void close(InputStream istream) {
		try {
			if (istream != null) {
				istream.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
	}

	public static String getSDCardDir() {
		return Environment.getExternalStorageDirectory().getPath();
	}

	public static String getDataDir() {
		return Environment.getDataDirectory().getPath();
	}

	/**
	 * 创建文件目录
	 */

	public void createdir(File parentfile) {
		if (parentfile != null) {
			if (!parentfile.exists()) {
				parentfile.mkdirs();
			}
		}
	}

	/** 文件过滤 **/
	class AudioFileFilter implements FileFilter {
		private String extname;

		@Override
		public boolean accept(File pathname) {
			return pathname.getName().endsWith(extname);
		}

		public AudioFileFilter() {
			this.extname = ".amr";
		}

		/** 包含. **/
		public AudioFileFilter(String extname) {
			this.extname = extname;
		}
	}

	/*** 合并数据库文�?**/
//	public static void mergeDBFile(Context context) {
//		// try {
//		// MySQLiteDBHelper.copyDataBase(context);
//		// } catch (IOException e) {
//		// e.printStackTrace();
//		// }finally{}
//
//		File f = new File(context.getFilesDir().getPath() + "/"
//				+ Constant.DATABASE_NAME);
//		// f.delete();
//		if (!f.exists()) {
//			// 如果不存在就加载数据
//			AssetManager manager = context.getAssets();
//			try {
//				FileOutputStream fps = context.openFileOutput(
//						Constant.DATABASE_NAME, context.MODE_WORLD_WRITEABLE);
//				String fileNames[] = manager.list("db");
//				for (String s : fileNames) {
//					if (s.startsWith("ESSS_DB")) {
//						InputStream is = manager.open("db/" + s);
//						byte[] buf = new byte[is.available()];
//						is.read(buf);
//						fps.write(buf);
//						is.close();
//					}
//				}
//				fps.flush();
//				fps.close();
//				// 将issuecode.csv 文件中的数据 添加到数据库�?
//				/*
//				 * InputStream in = null; BufferedReader br = null; BaseDao dao
//				 * = new BaseDao(LoginActivity.this); in =
//				 * manager.open("issuecode.csv", AssetManager.ACCESS_BUFFER); br
//				 * = new BufferedReader(new InputStreamReader(in, "GBK")); while
//				 * (br.ready()) {
//				 * dao.execSQL("INSERT INTO T_ISSUE_CODE VALUES (" +
//				 * br.readLine().replace("\"", "'").replace(" ", "") + ")"); }
//				 * dao.close(); br.close(); in.close();
//				 */
//				Log.e(tag, "finish mergeDBFile");
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//			}
//		} else {
//			Log.e(tag, "exists DBFile");
//		}
//	}

	public static void copyNotificationFile2SDcard(Context context)
			throws IOException {
		LogUtils.logStringArray(TAG, "copyFile2SDcard", "");
		Log.d(TAG, "copyFile2SDcard is start");
		// File sourceFile = new File(context.getAssets().opne() + "/" +
		// Constant.DATABASE_NAME);
		// Open your local db as the input stream
		// InputStream myInput = new FileInputStream(sourceFile);

		InputStream myInput = context.getAssets().open(
				"notification/new_msg.mp3");
		// Open the empty db as the output stream
		// OutputStream myOutput =
		// context.openFileOutput(Constant.DATABASE_NAME,Context.MODE_WORLD_WRITEABLE);
		File destFile = null;
		OutputStream sdkOutputStream = null;
		destFile = new File(Environment.getExternalStorageDirectory(),
				"new_msg.mp3");
		destFile.createNewFile();
		LogUtils.logStringArray(TAG, "copyDataBase.destFile.exists",
				destFile.exists() + "");
		sdkOutputStream = new FileOutputStream(destFile);
		try {
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				// myOutput.write(buffer, 0, length);
				if (sdkOutputStream != null) {
					sdkOutputStream.write(buffer, 0, length);
				}
			}

			// Close the streams
			if (sdkOutputStream != null) {
				sdkOutputStream.flush();
			}
		} catch (Exception e) {
			if (e != null) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage());
			}
		} finally {
			if (sdkOutputStream != null) {
				sdkOutputStream.close();
			}
			myInput.close();
		}

		Log.d(TAG, "copyFile2SDcard test db file:" + destFile.exists() + "");
		Log.d(TAG, "copyFile2SDcard is end");
	}
}
