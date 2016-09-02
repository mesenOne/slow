package com.indulge.freedom.who.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.indulge.freedom.who.AppContext;


public class DummyActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!AppContext.startedApp) {
			Intent intent = new Intent(this, SplashActivity.class);
			startActivity(intent);
		}
		finish();
	}
}
