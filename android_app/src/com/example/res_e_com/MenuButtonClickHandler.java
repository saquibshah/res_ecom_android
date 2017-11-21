package com.example.res_e_com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class MenuButtonClickHandler extends Activity{
	boolean offline=true;
	private static final int RESULT_SETTINGS = 1;
	Context ctx;
	public MenuButtonClickHandler(Context C) {
		this.ctx=C;
	}
	public boolean handler(int id){
		if (id == R.id.action_settings) {
			Intent i = new Intent(ctx, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;
		}
		if(id==R.id.action_show_cart){
			showCart();
			return true;
		}
		
return true;
	}
	
	private void showCart(){
		Intent i = new Intent(ctx, ShowCartActivity.class);
        startActivity(i);
	}
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);


		offline= sharedPrefs.getBoolean("prefSendReport", true);

		Log.d("set",""+offline);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case RESULT_SETTINGS:
			showUserSettings();
			break;

		}

	}


}
