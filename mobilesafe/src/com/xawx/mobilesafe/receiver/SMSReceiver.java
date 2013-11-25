package com.xawx.mobilesafe.receiver;

import com.xawx.mobilesafe.engine.GPSInfoProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// 获取短信内容
		// #*location*#1111
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
			String content = sms.getMessageBody();
			Log.i(TAG, "短信内容" + content);
			String sender = sms.getOriginatingAddress();
			if ("#*location*#".equals(content)) {
				// 终止广播
				abortBroadcast();
				GPSInfoProvider provider = GPSInfoProvider.getInstance(context);
				String location = provider.getLocation();
				SmsManager smsmanager = SmsManager.getDefault();
				if ("".equals(location)) {

				} else {
					smsmanager.sendTextMessage(sender, null, location, null,
							null);
				}
			}
		}
	}

}
