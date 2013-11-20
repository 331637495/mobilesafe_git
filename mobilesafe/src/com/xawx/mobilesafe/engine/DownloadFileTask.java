package com.xawx.mobilesafe.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;

public class DownloadFileTask {

	/**
	 * 获取文件
	 * 
	 * @param path
	 *            服务器文件路径
	 * @param filepath
	 *            本地文件路径
	 * @param pd
	 *            对话框进度条
	 * @return 本地文件对象
	 * @throws Exception
	 */
	public static File getFile(String path, String filepath, ProgressDialog pd)
			throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			int totle = conn.getContentLength();
			pd.setMax(totle);
			InputStream is = conn.getInputStream();
			File file = new File(filepath);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			int process = 0;
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				process += len;
				pd.setProgress(process);
			}
			fos.flush();
			fos.close();
			is.close();
			return file;
		}
		return null;
	}
}
