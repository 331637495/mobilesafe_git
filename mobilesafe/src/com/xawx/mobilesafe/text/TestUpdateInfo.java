package com.xawx.mobilesafe.text;

import com.xawx.mobilesafe.R;
import com.xawx.mobilesafe.domain.UpdateInfo;
import com.xawx.mobilesafe.engine.UpdateInfoService;

import android.test.AndroidTestCase;

public class TestUpdateInfo extends AndroidTestCase {

	public void testUpdateInfo() throws Exception {
		UpdateInfoService infoService = new UpdateInfoService(getContext());
		UpdateInfo info = infoService.getUpdateInfo(R.string.updateurl);
		System.out.println(info.getVersion());
		assertEquals("2.0", info.getVersion());
	}
}
