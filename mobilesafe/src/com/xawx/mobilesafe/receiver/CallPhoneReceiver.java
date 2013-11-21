package com.xawx.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xawx.mobilesafe.ui.LostProtectedActivity;

public class CallPhoneReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String number = getResultData();
		if ("2013".equals(number)) {
			Intent intent2 = new Intent(context, LostProtectedActivity.class);
			// 制定要激活的activity在自己的任务栈里运行
			intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent2);
			// 终止掉这个电话 不能通过abortBroadcast()终止
			setResultData(null);
		}
	}

}
