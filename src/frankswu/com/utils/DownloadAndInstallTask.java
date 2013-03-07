package frankswu.com.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

/**
 * @author frankswu
 *
 */
public class DownloadAndInstallTask extends AsyncTask<URL, String, Boolean> {
	private static final String TAG = "DownloadAndInstallTask";
	private ProgressDialog pd;
	private Activity context ;
	private static final String LOCAL_FILE_NAME = "installSSS.apk";
	private static final String UPDATE_MESSAGE = "�?��的安监版�?";
	
	 
	public DownloadAndInstallTask(Activity parent) {
		context = parent;
		try {
			pd = new ProgressDialog(context);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage(UPDATE_MESSAGE +"，请稍等...");
			pd.setIndeterminate(false);
			pd.setCancelable(false);
			pd.show();
		} catch (Exception e) {
			LogUtils.logException(null, TAG, e);
		}
	}
	
//	protected void onPreExecute() {
//	}

	@Override
	protected Boolean doInBackground(URL... url) {
		DefaultHttpClient httpClent = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url[0].toString());
		InputStream input = null;
		try {
			HttpResponse response = httpClent.execute(httpGet);
			File sdcard = Environment.getExternalStorageDirectory();
			
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				publishProgress(new String[] {"0", "下载 " +UPDATE_MESSAGE });
				Log.d(TAG,"httpGetClientConection is ok");
				HttpEntity entity = response.getEntity();
				if (entity != null) {
                	LogUtils.logStringArray(TAG, "doInBackground.httpEntity is not null", "");
					long fileSize = entity.getContentLength();
					pd.setMax((int) (fileSize /1024));
//					Log.i("download size", ""+fileSize);
                	LogUtils.logStringArray(TAG, "doInBackground.httpEntity is not null", entity.getContentLength()+"",entity.getContentType()+"",entity.isStreaming() + "");
//					Log.d(TAG,entity.getContentLength() + "");
//					Log.d(TAG,entity.getContentType() + "");
//					Log.d(TAG,entity.isStreaming() + "");
	                //设置本地保存的文�? 
	                File storeFile = new File(sdcard,LOCAL_FILE_NAME);    
	                if (storeFile.exists()) {
	                	LogUtils.logStringArray(TAG, "doInBackground.hasSameApkAndDeleteIt", "");
						storeFile.delete();
					}
	                storeFile.createNewFile();
	                FileOutputStream output = new FileOutputStream(storeFile);  
	                //得到网络资源并写入文�? 
	                input = entity.getContent();  
					byte[] buffer = new byte[1024];
					int len1 = 0;
					int iteration = 0;
					int onePercent = (int) (fileSize / 100);
					int doneSoFar = 0;
					int progress = 1;
					while ( (len1 = input.read(buffer)) != -1 ) {
						output.write(buffer,0, len1);
						doneSoFar = doneSoFar + 1024;
						iteration++;
						if (doneSoFar > (onePercent * progress) ) {
							if ( ( (doneSoFar/1024) % 10 ) == 0 ) {
								publishProgress(new String[] {String.valueOf(doneSoFar/1024), UPDATE_MESSAGE +"下载 �?.."});
							}
							progress++;
						}
//						Log.w("iteration", "I:"+iteration+", size: "+1024*iteration);
					}    
					output.close();
				} else {
                	LogUtils.logStringArray(TAG, "doInBackground.httpEntity is null", "");
					Toast.makeText(context, "http请求过程中出�?" + response.getStatusLine().getStatusCode() + " 错误�?", Toast.LENGTH_LONG).show();
					return false;
				}
				
	            if (entity != null) {  
	                entity.consumeContent();  
	            }  				
			}
			
		} catch (ClientProtocolException e) {
			LogUtils.logException(context, TAG, e);
			return false;
		} catch (IOException e) {
			LogUtils.logException(context, TAG, e);
			return false;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				LogUtils.logException(context, TAG, e);
				return false;
			}
		}
			
		publishProgress(new String[] {"100", "下载成功!"});
		return true;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onProgressUpdate(String... values) {
		super.onProgressUpdate(values);
		pd.setProgress(Integer.parseInt(values[0]));
		// TODO �?��报错说是没有在主线程中更新UI
//		pd.setMessage(values[1]);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		Log.d(TAG,"onPostExecute.result[" + result + "]");
		pd.dismiss();
		if (result) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),LOCAL_FILE_NAME)), "application/vnd.android.package-archive");
			context.startActivity(intent);
		} else {
			Toast.makeText(context, "安装文件没有下载成功�?", Toast.LENGTH_LONG).show();
		}
		context.finish();
	}
}
