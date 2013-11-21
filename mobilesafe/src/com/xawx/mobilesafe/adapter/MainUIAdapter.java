package com.xawx.mobilesafe.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xawx.mobilesafe.R;

public class MainUIAdapter extends BaseAdapter {

	// private static final String TAG = "MainUIAdapter";

	private LayoutInflater inflater;
	private static ImageView imageView = null;
	private static TextView textView = null;
	private SharedPreferences sp;

	private static String[] names = { "手机防盗", "通讯卫士", "软件管理", "任务管理", "流量管理",
			"手机杀毒", "系统优化", "高级工具", "设置中心" };

	private static int[] icons = { R.drawable.widget05, R.drawable.widget02,
			R.drawable.widget01, R.drawable.widget07, R.drawable.widget05,
			R.drawable.widget04, R.drawable.widget06, R.drawable.widget03,
			R.drawable.widget08 };

	public MainUIAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
	}

	@Override
	public int getCount() {
		return names.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.i(TAG, "getview" + position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.mainscrean_item, null);
			imageView = (ImageView) convertView
					.findViewById(R.id.iv_maincreanitem_image);
			textView = (TextView) convertView
					.findViewById(R.id.tv_mainscreanitem_text);
			// 将找到的控件放到缓存中
			convertView.setTag(new DataWrapper(imageView, textView));
			// Log.i(TAG, "zhaokongjian" + position);
		} else {
			// 从缓存中找到控件
			DataWrapper data = (DataWrapper) convertView.getTag();
			imageView = data.imageView;
			textView = data.textView;
			// Log.i(TAG, "qukongjina" + position);
		}
		imageView.setImageResource(icons[position]);
		textView.setText(names[position]);
		if (position == 0) {
			String name = sp.getString("lost_name", null);
			if (name != null) {
				textView.setText(name);
			}
		}
		return convertView;
	}

	/**
	 * 缓存 imageview 和 textview
	 * 
	 * @author think
	 * 
	 */
	private final class DataWrapper {
		public ImageView imageView;
		public TextView textView;

		public DataWrapper(ImageView imageView, TextView textView) {
			this.imageView = imageView;
			this.textView = textView;
		}
	}

}
