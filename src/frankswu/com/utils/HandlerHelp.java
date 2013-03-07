package frankswu.com.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;


/**
 * �?android平台中的handler thread looper 封装在一个类�? 便于activity调用和UI更新 使作方法:
 * 1.在activity继承 HandlerHelp 
 * 2.在activity中实现doTask方法.处理耗时的数�?
 * 3.实现 doMessage,此方法主要处理message,执行更新UI数据
 * 4.调用execute方法.执行任务
 * 
 */
public abstract class HandlerHelp {

	private Context context;
	private String customMsg ;
	protected ProgressDialog Dialog = null;

	private HandlerHelp() {

	}

	protected HandlerHelp(Context context) {
		this.context = context;
	}

	public boolean execute(String promtp) {
		if(checkNetWorkStatus()){
			Dialog = ProgressDialog.show(context, "提示", promtp+"...");
			Dialog.setIndeterminate(true);
			Dialog.setCancelable(true);
			t.start();
			return true;
		}else{
			return false;
		}
	}

	Thread t = new Thread() {
		@Override
		public void run() {

			Looper.prepare();
			Message msg = new Message();
			// 设置默认�?
			msg.what = Constant.MSG_SUCCESSED;
			try {
				doTask(msg);
			} catch (Exception e) {
				msg.what = Constant.MSG_ERROR;
				customMsg = e.getMessage() == null ? "it's error":e.getMessage();
				e.printStackTrace();
			} finally {
				if(msg.getTarget() == null)
				{
					tHandler.sendMessage(msg);
				}
				else
				{
					msg.sendToTarget();
				}
			}

			Looper.loop();
		}
	};
	Handler tHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			try {
				doMessage(msg);
				switch (msg.what) {
				case Constant.MSG_ERROR:
					Toast.makeText(context, customMsg, Toast.LENGTH_SHORT).show();
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

				if (Dialog != null) {
					Dialog.dismiss();
				}
				tHandler.removeMessages(msg.what);
				tHandler.removeCallbacks(t);
			}
		}
	};

	/**
	 * 处理doTask发�?的messag
	 * 
	 * @param msg
	 */
	public abstract void doMessage(Message msg);

	/**
	 * 执行任务
	 * 
	 * @param msg
	 */
	public abstract void doTask(Message msg) throws Exception;

	/**
	 * �?��手机是否联网
	 */
	public boolean checkNetWorkStatus() {
		boolean netStatus = false;
		ConnectivityManager netManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netManager.getActiveNetworkInfo();
		if (netManager.getActiveNetworkInfo() != null) {
			netStatus = netManager.getActiveNetworkInfo().isAvailable();
		}
		if (!netStatus) {
			Toast.makeText(context, "没有可用的网�?", Toast.LENGTH_SHORT).show();
			/*
			Builder b = new AlertDialog.Builder(context).setTitle("没有可用的网�?).setMessage("是否对网络进行设置？");
			b.setPositiveButton("�?, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent mIntent = new Intent("/");
					ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings");
					mIntent.setComponent(comp);
					mIntent.setAction("android.intent.action.VIEW");
					context.startActivity(mIntent);
				}
			}).setNeutralButton("�?, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.cancel();
				}
			}).show();
		*/}
		return netStatus;
	}
	
	public void dealwith(Message msg)
	{}

	public ProgressDialog getProgessDialog() {
		return Dialog;
	}
}
