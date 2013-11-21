package com.xawx.mobilesafe.engine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.xawx.mobilesafe.domain.UpdateInfo;

public class UpdateInfoService {

	private Context context;

	public UpdateInfoService(Context context) {
		this.context = context;
	}

	/**
	 * 获取更新信息
	 * @param urlid 服务器路径String对应的id
	 * @return 更新的信息
	 * @throws MalformedURLException 
	 */
	public UpdateInfo getUpdateInfo(int urlid) throws Exception{
		String path = context.getResources().getString(urlid);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(2000);
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		return UpdateInfoParser.getUpdateInfo(is);	
	}
}
