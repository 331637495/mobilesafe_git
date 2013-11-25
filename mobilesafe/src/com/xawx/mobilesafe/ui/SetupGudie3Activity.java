package com.xawx.mobilesafe.ui;

import com.xawx.mobilesafe.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupGudie3Activity extends Activity implements OnClickListener {

	private Button btn_next, btn_pre, btn_select_contact;
	private EditText editText;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupguide3);
		sp = getSharedPreferences("config", Context.MODE_PRIVATE);
		btn_next = (Button) this.findViewById(R.id.btn_next3);
		btn_pre = (Button) this.findViewById(R.id.btn_previous3);
		btn_select_contact = (Button) this
				.findViewById(R.id.btn_select_contect);
		btn_next.setOnClickListener(this);
		btn_pre.setOnClickListener(this);
		btn_select_contact.setOnClickListener(this);
		editText = (EditText) this.findViewById(R.id.et_setup3_number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next3:
			String number = editText.getText().toString().trim();
			if ("".equals(number)) {
				Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
				return;
			} else {
				Editor editor = sp.edit();
				editor.putString("safenumber", number);
				editor.commit();
			}
			Intent intent4 = new Intent(this, SetupGudie4Activity.class);
			finish();
			startActivity(intent4);
			// 设置activity切换时候的动画效果  左右移动动画
			overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
			break;
		case R.id.btn_previous3:
			Intent intent2 = new Intent(this, SetupGudie2Activity.class);
			finish();
			startActivity(intent2);
			// 设置activity切换时候的动画效果 透明变化
			overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
			break;
		case R.id.btn_select_contect:
			Intent intent = new Intent(SetupGudie3Activity.this,
					SelectContactActivity.class);
			startActivityForResult(intent, 0);

			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			String phone = data.getStringExtra("number");
			editText.setText(phone);
		}
	}

}
