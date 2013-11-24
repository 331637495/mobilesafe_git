package com.xawx.mobilesafe.ui;

import java.util.List;

import com.xawx.mobilesafe.R;
import com.xawx.mobilesafe.domain.ContactInfo;
import com.xawx.mobilesafe.engine.ContactInfoService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SelectContactActivity extends Activity {
	private ListView listView;
	private List<ContactInfo> infos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_contact);
		ContactInfoService service = new ContactInfoService(this);
		infos = service.getContactInfos();
		listView = (ListView) this.findViewById(R.id.iv_select_contact);
		listView.setAdapter(new SelectContactAdapter());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String phone = infos.get(position).getPhone();
				Intent intent = new Intent();
				intent.putExtra("number", phone);
				setResult(0, intent);
				finish();
			}
		});
	}

	private class SelectContactAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return infos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return infos.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContactInfo info = infos.get(position);
			LinearLayout ll = new LinearLayout(SelectContactActivity.this);
			ll.setOrientation(LinearLayout.VERTICAL);
			TextView tv_name = new TextView(SelectContactActivity.this);
			tv_name.setTextColor(Color.WHITE);
			tv_name.setText("姓名：" + info.getName());
			TextView tv_phone = new TextView(SelectContactActivity.this);
			tv_phone.setTextColor(Color.WHITE);
			tv_phone.setText("电话：" + info.getPhone());
			ll.addView(tv_name);
			ll.addView(tv_phone);
			return ll;
		}

	}
}
