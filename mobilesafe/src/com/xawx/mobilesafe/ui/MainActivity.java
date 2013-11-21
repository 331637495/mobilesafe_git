package com.xawx.mobilesafe.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.xawx.mobilesafe.R;
import com.xawx.mobilesafe.adapter.MainUIAdapter;

public class MainActivity extends Activity implements OnItemClickListener {

	private static final String TAG = "MainActivity";
	private GridView gv_main;
	private MainUIAdapter adapter;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscrean);
		sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
		gv_main = (GridView) this.findViewById(R.id.gv_main);
		adapter = new MainUIAdapter(this);
		gv_main.setAdapter(adapter);
		gv_main.setOnItemClickListener(this);
		gv_main.setOnItemLongClickListener(new OnItemLongClickListener() {

			/**
			 * 当gridview的条目被长时间点击后对应的回调函数
			 * 
			 * @param parent
			 *            gridview
			 * @param view
			 *            当前被点击的条目 Linearlayout
			 * @param position
			 *            点击条目对应的位置
			 * @param id
			 *            代表的行号
			 */
			@Override
			public boolean onItemLongClick(AdapterView<?> parent,
					final View view, int position, long id) {
				if (position == 0) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setTitle("设置");
					builder.setMessage("请输入需要修改的名称");
					final EditText et = new EditText(MainActivity.this);
					et.setHint("请输入文本");
					builder.setView(et);
					builder.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							String name = et.getText().toString().trim();
							if ("".equals(name)) {
								Toast.makeText(MainActivity.this, "内容不能为空",
										Toast.LENGTH_SHORT).show();
							} else {
								Editor editor = sp.edit();
								editor.putString("lost_name", name);
								editor.commit();
								TextView tv = (TextView) view
										.findViewById(R.id.tv_mainscreanitem_text);
								tv.setText(name);
							}
						}
					});
					builder.setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					builder.create().show();
				}
				return false;
			}
		});
	}

	/**
	 * 当gridview的条目被点击后对应的回调函数
	 * 
	 * @param parent
	 *            gridview
	 * @param view
	 *            当前被点击的条目 Linearlayout
	 * @param position
	 *            点击条目对应的位置
	 * @param id
	 *            代表的行号
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "点击的位置" + position);
		switch (position) {
		case 0: // 手机防盗
			Log.i(TAG, "进入手机防盗");
			break;
		}
	}
}
