package com.xawx.mobilesafe.ui;

import com.xawx.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetupGudie3Activity extends Activity implements OnClickListener {

	private Button btn_next, btn_pre, btn_select_contact;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setupguide3);
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

			break;
		case R.id.btn_previous3:

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
