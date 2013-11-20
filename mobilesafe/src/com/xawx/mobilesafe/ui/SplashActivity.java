package com.xawx.mobilesafe.ui;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xawx.mobilesafe.R;

public class SplashActivity extends Activity {

	private TextView tv_splash_version;
	private LinearLayout ll_splash_main;

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
		tv_splash_version.setText("ver: " + versiontext);
		ll_splash_main = (LinearLayout) this.findViewById(R.id.ll_splash_main);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setStartTime(1000);
		ll_splash_main.startAnimation(aa);
	}

	/**
	 * 获取当前程序版本号
	 * 
	 * @return 版本号
	 */
	private String getVersion() {
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return R.string.notfound + "";
		}

	}

}
