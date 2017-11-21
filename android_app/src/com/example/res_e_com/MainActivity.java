/******************************************
 * Final project Restaurant E-com
 * License: Apache License 2.0
 ******************************************/
package com.example.res_e_com;

import db.SqlHandler;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	SqlHandler sqlHandler;
	Button resListBtn;
	private static final int RESULT_SETTINGS = 1;
	boolean offline = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sqlHandler = new SqlHandler(this);
		sqlHandler.createTable();
		resListBtn=(Button) findViewById(R.id.show_res_list);
		resListBtn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*
		 * handle actions of action item
		 */
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;
		}
		if(id==R.id.action_show_cart){
			showCart();
		}
		if(id==R.id.action_show_tracking){
			Intent i = new Intent(MainActivity.this, TrackingActivity.class);
	        startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.show_res_list:
			Intent resListAct=new Intent(MainActivity.this,ResListActivity.class);
			startActivity(resListAct);
			break;

		default:
			break;
		}
		
	}
	private void showCart(){
		Intent i = new Intent(MainActivity.this, ShowCartActivity.class);
        startActivity(i);
	}
}
