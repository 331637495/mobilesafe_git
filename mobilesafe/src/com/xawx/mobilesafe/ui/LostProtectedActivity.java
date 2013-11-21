package com.xawx.mobilesafe.ui;

import com.xawx.mobilesafe.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class LostProtectedActivity extends Activity {

	private static final String TAG = "LostProtectedActivity";
	private SharedPreferences sp;
	private Dialog dialog;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		// 判断用户是否设置了密码
		if (isPWDSetup()) {
			Log.i(TAG, "设置里密码，正常登陆");
		}else {
			Log.i(TAG, "没有设置密码显示第一次对话框");
			showFirstEntryDialog();
		}
	}

	/**
	 * 第一次进入程序时对话框
	 */
	private void showFirstEntryDialog() {
		dialog = new Dialog(this,R.style.MyDialog);
		dialog.setContentView(R.layout.first_entry_dialog);
		dialog.show();
		
	}

	/**
	 * 检查sharedpreference里面时候有密码的设置
	 * 
	 * @return 有 true 没有 false
	 */
	private boolean isPWDSetup() {
		String password = sp.getString("password", null);
		if (password == null) {
			return false;
		} else {
			if (!"".equals(password)) {
				return false;
			} else {
				return true;
			}
		}
	}

}
