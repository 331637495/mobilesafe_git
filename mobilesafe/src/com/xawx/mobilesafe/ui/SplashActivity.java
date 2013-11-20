package com.xawx.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xawx.mobilesafe.R;
import com.xawx.mobilesafe.domain.UpdateInfo;
import com.xawx.mobilesafe.engine.UpdateInfoService;

public class SplashActivity extends Activity {

	private static final String TAG = "SplashActivity";
	private TextView tv_splash_version;
	private LinearLayout ll_splash_main;
	private UpdateInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		// 完成窗体的全屏显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		String versiontext = getVersion();
		// 判断服务器版本和客户端版本号是否一致
		if (inNeedUpdate(versiontext)) {
			Log.i(TAG, "弹出升级对话框");
			showUpdateDialog();
		}

		tv_splash_version.setText("ver: " + versiontext);
		ll_splash_main = (LinearLayout) this.findViewById(R.id.ll_splash_main);
		// 设置界面从完全透明到完全不透明的显示
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		// 完全显示需要的时间
		aa.setStartTime(1000);
		ll_splash_main.startAnimation(aa);
	}

	/**
	 * 显示对话框，提示用户升级信息
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setIcon(R.drawable.icon2);
		builder.setTitle("升级提醒");
		builder.setMessage(info.getDescription());
		builder.setCancelable(false); // 让用户不能后退取消对话框
		builder.setPositiveButton("现在更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "下载apk文件" + info.getApkurl());

			}
		});
		builder.setNegativeButton("稍后更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "用户取消进入主界面");
			}
		});
		builder.create().show();
	}

	/**
	 * 判断版本号是否一致
	 * 
	 * @param version
	 *            当前客户端版本号信息
	 * @return 是否需要更新
	 */
	private boolean inNeedUpdate(String versiontext) {
		UpdateInfoService service = new UpdateInfoService(this);
		try {
			info = service.getUpdateInfo(R.string.updateurl);
			String version = info.getVersion();
			if (versiontext.equals(version)) {
				Log.i(TAG, "版本相同无需升级，进入主界面");
				return false;
			} else {
				Log.i(TAG, "版本不相同需要升级");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "获取更新信息异常", Toast.LENGTH_SHORT).show();
			Log.i(TAG, "获取更新信息异常，进入主界面");
			return false;
		}
	}

	/**
	 * 获取当前程序版本号
	 * 
	 * @return 版本号
	 */
	private String getVersion() {
		try {
			PackageManager manager = getPackageManager();
			PackageInfo packageInfo = manager.getPackageInfo(getPackageName(),
					0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return R.string.notfound + "";
		}

	}

}
