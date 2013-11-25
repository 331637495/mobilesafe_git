package com.xawx.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xawx.mobilesafe.R;

public class SetupGudie4Activity extends Activity implements OnClickListener {

	private Button btn_pre, btn_finish;
	private CheckBox cb_isprotecting;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupguide4);
		btn_finish = (Button) findViewById(R.id.btn_setup_finish);
		btn_pre = (Button) findViewById(R.id.btn_previous4);
		cb_isprotecting = (CheckBox) this.findViewById(R.id.cb_isprotecting);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		// 初始化checkbox的状态
		boolean isprotecting = sp.getBoolean("isprotecting", false);
		if (isprotecting) {
			cb_isprotecting.setText("手机防盗保护中");
			cb_isprotecting.setChecked(true);
		}

		btn_finish.setOnClickListener(this);
		btn_pre.setOnClickListener(this);
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_previous4:
			Intent intent3 = new Intent(this, SetupGudie3Activity.class);
			finish();
			startActivity(intent3);
			// 设置activity切换时候的动画效果 透明变化
			overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
			break;
		case R.id.btn_setup_finish:
			if (cb_isprotecting.isChecked()) {
				finish();
				finishSetup();
			} else {
				AlertDialog.Builder builder = new Builder(
						SetupGudie4Activity.this);
				builder.setTitle("提醒");
				builder.setMessage("强烈建议开启手机防盗保护，是否完成设置？");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
								finishSetup();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				builder.create().show();

				// 有点流氓
				// Toast.makeText(this, "强烈建议开启手机防盗保护",
				// Toast.LENGTH_SHORT).show();
				// return;
			}
			break;
		}

	}

	private void finishSetup() {
		// 设置一个标示 用户已经完成过设置向导
		Editor editor = sp.edit();
		editor.putBoolean("issetupalready", true);
		editor.commit();
	}

}
