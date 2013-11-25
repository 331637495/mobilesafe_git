package com.xawx.mobilesafe.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xawx.mobilesafe.R;
import com.xawx.mobilesafe.util.MD5Encoder;

public class LostProtectedActivity extends Activity implements OnClickListener {

	private static final String TAG = "LostProtectedActivity";
	private SharedPreferences sp;
	private Dialog dialog;
	private EditText et_pwd;
	private EditText et_pwd_confirm;
	private EditText et_normal_pwd;
	private TextView tv_lost_protected_number, tv_reentry_setup_guide;
	private CheckBox cb_isprotecting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		isFirstOrSenect();
	}

	/**
	 * 判断是第一次进入还是第二次进入
	 */
	public void isFirstOrSenect() {
		// 判断用户是否设置了密码
		if (isPWDSetup()) {
			Log.i(TAG, "设置了密码，正常登陆");
			showNormalEntryDialog();
		} else {
			Log.i(TAG, "没有设置密码显示第一次对话框");
			showFirstEntryDialog();
		}
	}

	/**
	 * 正常登陆对话框
	 */
	private void showNormalEntryDialog() {
		dialog = new Dialog(LostProtectedActivity.this, R.style.MyDialog);
		View view = View.inflate(LostProtectedActivity.this,
				R.layout.normal_entry_dialog, null);
		et_normal_pwd = (EditText) view.findViewById(R.id.et_normal_entry_pwd);
		Button btn_normal_ok = (Button) view
				.findViewById(R.id.btn_normal_dialog_ok);
		Button btn_normal_cancel = (Button) view
				.findViewById(R.id.btn_normal_dialog_cancel);
		btn_normal_ok.setOnClickListener(this);
		btn_normal_cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.show();
		dialog.setCancelable(false);
	}

	/**
	 * 第一次进入程序时对话框
	 */
	private void showFirstEntryDialog() {
		dialog = new Dialog(LostProtectedActivity.this, R.style.MyDialog);
		// dialog.setContentView(R.layout.first_entry_dialog);
		View view = View.inflate(LostProtectedActivity.this,
				R.layout.first_entry_dialog, null);
		et_pwd = (EditText) view.findViewById(R.id.et_first_entry_pwd);
		et_pwd_confirm = (EditText) view
				.findViewById(R.id.et_first_entry_pwd_comfirm);
		Button btn_ok = (Button) view.findViewById(R.id.btn_first_dialog_ok);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_first_dialog_cancel);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		dialog.setContentView(view);
		dialog.show();
		dialog.setCancelable(false);
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
			if ("".equals(password)) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * dialog按钮点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_first_dialog_ok:
			String pwd = et_pwd.getText().toString().trim();
			String pwd_confirm = et_pwd_confirm.getText().toString().trim();
			if ("".equals(pwd) || "".equals(pwd_confirm)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			} else {
				if (pwd.equals(pwd_confirm)) {
					Editor editor = sp.edit();
					editor.putString("password", MD5Encoder.encode(pwd));
					editor.commit();
				} else {
					Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			dialog.dismiss();
			// 调用正常登陆dialog 让用户输入密码进入
			showNormalEntryDialog();
			break;
		case R.id.btn_first_dialog_cancel:
			dialog.dismiss();
			Intent intent = new Intent(LostProtectedActivity.this,
					MainActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_normal_dialog_ok:
			String password = et_normal_pwd.getText().toString().trim();
			if ("".equals(password)) {
				Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
				return;
			} else {
				String realpwd = sp.getString("password", "");
				if (realpwd.equals(MD5Encoder.encode(password))) {
					if (issetup()) {
						Log.i(TAG, "加载手机防盗主界面");
						setContentView(R.layout.lost_protected);
						tv_lost_protected_number = (TextView) this
								.findViewById(R.id.tv_lost_protected_number);
						tv_reentry_setup_guide = (TextView) this
								.findViewById(R.id.tv_reentry_setup_guide);
						cb_isprotecting = (CheckBox) findViewById(R.id.cb_isprotecting);
						// 初始化控件
						String number = sp.getString("safenumber", "");
						tv_lost_protected_number.setText("安全手机号码：" + number);
						//重新进入设置向导的点击事件
						tv_reentry_setup_guide.setOnClickListener(this);
						// 初始化checkbox的状态
						boolean isprotecting = sp.getBoolean("isprotecting", false);
						if (isprotecting) {
							cb_isprotecting.setText("手机防盗保护中");
							cb_isprotecting.setChecked(true);
						}
						cb_isprotecting
								.setOnCheckedChangeListener(new OnCheckedChangeListener() {

									@Override
									public void onCheckedChanged(CompoundButton buttonView,
											boolean isChecked) {
										if (isChecked) {
											cb_isprotecting.setText("手机防盗保护中");
											Editor editor = sp.edit();
											editor.putBoolean("isprotecting", true);
											editor.commit();
										} else {
											cb_isprotecting.setText("没有开启手机防盗保护");
											Editor editor = sp.edit();
											editor.putBoolean("isprotecting", false);
											editor.commit();
										}
									}
								}); 
					} else {
						Log.i(TAG, "进入设置向导界面");
						finish();
						Intent intent2 = new Intent(LostProtectedActivity.this,
								SetupGudie1Activity.class);
						startActivity(intent2);
					}

				} else {
					Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
					return;
				}
			}
			dialog.dismiss();
			break;
		case R.id.btn_normal_dialog_cancel:
			dialog.dismiss();
			Intent intent2 = new Intent(LostProtectedActivity.this,
					MainActivity.class);
			startActivity(intent2);
			break;
		case R.id.tv_reentry_setup_guide:
			finish();
			Intent intent3 = new Intent(LostProtectedActivity.this,
					SetupGudie1Activity.class);
			startActivity(intent3);
			break;
		}

	}

	/**
	 * 判断是否已经设置过了设置向导
	 * 
	 * @return true 设置过了 false 没有设置
	 */
	private boolean issetup() {
		return sp.getBoolean("issetupalready", false);
	}

}
