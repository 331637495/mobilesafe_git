package com.xawx.mobilesafe.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.xawx.mobilesafe.domain.ContactInfo;

public class ContactInfoService {

	private Context context;

	public ContactInfoService(Context context) {
		this.context = context;
	}

	public List<ContactInfo> getContactInfos() {
		ContentResolver resolver = context.getContentResolver();
		List<ContactInfo> infos = new ArrayList<ContactInfo>();
		ContactInfo info;
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri data = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			info = new ContactInfo();
			String id = cursor.getString(cursor.getColumnIndex("_id"));
			// System.out.println("联系人id" + id);
			String name = cursor.getString(cursor
					.getColumnIndex("display_name"));
			// System.out.println("姓名：" + name);
			info.setName(name);
			Cursor dataCursor = resolver.query(data, null,
					"raw_contact_id = ?", new String[] { id }, null);
			while (dataCursor.moveToNext()) {

				String type = dataCursor.getString(dataCursor
						.getColumnIndex("mimetype"));
				if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					String number = dataCursor.getString(dataCursor
							.getColumnIndex("data1"));
					// System.out.println("电话：" + number);
					info.setPhone(number);
				}
				// System.out.println("类型：" + type);
				// System.out.println("-----------------------");
			}
			dataCursor.close();
			infos.add(info);
			info = null;
		}
		cursor.close();
		return infos;
	}
}
