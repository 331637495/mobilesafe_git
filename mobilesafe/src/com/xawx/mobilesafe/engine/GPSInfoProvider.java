package com.xawx.mobilesafe.engine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 保证这个类只存在一个实例
 * 
 * @author think
 * 
 */
public class GPSInfoProvider {
	private static GPSInfoProvider mGpsInfoProvider;
	private static Context context;
	private static MyLocationListener listener;
	LocationManager manager;

	// 1.私有化构造方法
	private GPSInfoProvider() {
	}

	// 2.提供一个静态的方法可以返回一个实例
	public static synchronized GPSInfoProvider getInstance(Context context) {
		if (mGpsInfoProvider == null) {
			mGpsInfoProvider = new GPSInfoProvider();
			GPSInfoProvider.context = context;
		}
		return mGpsInfoProvider;
	}

	/**
	 * 获取gps信息
	 * 
	 * @return
	 */
	public String getLocation() {
		manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		String provider = getprovider(manager);
		// 注册位置的监听器
		manager.requestLocationUpdates(provider, 60000, 50, getListener());

		SharedPreferences sp = context.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		String location = sp.getString("location", "");
		return location;
	}

	/**
	 * 停止GPS监听
	 */
	public void stopGPSListener() {
		manager.removeUpdates(getListener());
	}

	private synchronized MyLocationListener getListener() {
		if (listener == null) {
			listener = new MyLocationListener();
		}
		return listener;
	}

	private class MyLocationListener implements LocationListener {

		/**
		 * 当手机位置发生变化时候调用的方法
		 */
		@Override
		public void onLocationChanged(Location location) {
			String longitude = "jingdu: " + location.getLongitude(); // 经度
			String latitude = "weidu: " + location.getLatitude(); // 纬度
			SharedPreferences sp = context.getSharedPreferences("config",
					Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("location", longitude + " - " + latitude);
			editor.commit(); // 最后一次获取到的位置信息存放到SharedPreferences里
		}

		/**
		 * 某一个设备状态发生改变的时候调用 可用-->不可用 不可用-->可用
		 */
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		/**
		 * 某个设备被打开
		 */
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		/**
		 * 某个设备被禁用
		 */
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * 得到当前状态下最好的为之提供者
	 * 
	 * @param manager
	 *            位置管理服务
	 * @return 最好的为之提供者
	 */
	private String getprovider(LocationManager manager) {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);
		return manager.getBestProvider(criteria, true);
	}
}
