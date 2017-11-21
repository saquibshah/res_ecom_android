package com.example.res_e_com;

import java.util.ArrayList;



import db.SqlHandler;
import db.ResEcomDbContract.Restaurant;

import listviewAdapter.RestaurantListAdapter;
import model.ModelRestaurant;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ResListActivity extends ActionBarActivity {
	private static final int RESULT_SETTINGS = 1;
	boolean offline = true;
	ListView restListView;
	SqlHandler sqlHandler;
	ProgressBar pg;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_list);
		sqlHandler = new SqlHandler(this);

		/*
		 * get user preference from shared pref
		 */
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);


		offline= sharedPrefs.getBoolean("prefSendReport", true);

		Log.d("set",""+offline);

		if (!offline) {//call service to get data
			sqlHandler.dropTable();
			sqlHandler.createTable();
			// Display progress bar until web service invocation completes
			pg = (ProgressBar) findViewById(R.id.progressBar1);

			AsyncCallWS task = new AsyncCallWS();
			// Call execute
			task.execute();
		}
		else{//shwo list from local sqlite
			generateResListView();
		}

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
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;
		}
		if(id==R.id.action_show_cart){
			showCart();
		}
		if(id==R.id.action_show_tracking){
			Intent i = new Intent(ResListActivity.this, TrackingActivity.class);
	        startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	private void showCart(){
		Intent i = new Intent(ResListActivity.this, ShowCartActivity.class);
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

	

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {

			WebServiceJun.invokeHelloWorldWS("hi", "receiveInfo",
					ResListActivity.this);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pg.setVisibility(View.INVISIBLE);
			generateResListView();
		}

		@Override
		protected void onPreExecute() {
			pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}

	}

	public void generateResListView() {
		// generate the listview
		// 4 steps to create a list view or custom listview
		// 1. get the reference of listview
		restListView = (ListView) findViewById(R.id.restaurant_list_view);
		// 2.get data for listview

		// create some data for test
		ArrayList<ModelRestaurant> resList = new ArrayList<ModelRestaurant>();
		/*
		 * give some data manually
		 */
		/*
		 * ModelRestaurant res=new ModelRestaurant(); res.setResName("Jun");
		 * resList.add(res);
		 * 
		 * ModelRestaurant res1=new ModelRestaurant(); res1.setResName("Jun1");
		 * resList.add(res1);
		 * 
		 * ModelRestaurant res2=new ModelRestaurant(); res2.setResName("Jun2");
		 * resList.add(res2);
		 */

		/*
		 * data from sqlite db
		 */
		// insert data in db

		// sqlHandler.insertData();

		// retrieve data from sqlite
		String query = "SELECT * FROM restaurant";
		Cursor c1 = sqlHandler.selectQuery(query);
		if (c1 != null & c1.getCount() != 0) {
			if (c1.moveToFirst()) {
				do {
					ModelRestaurant res = new ModelRestaurant();
					res.setId(c1.getInt(c1
							.getColumnIndex(Restaurant.COLUMN_NAME_ID)));
					res.setResName(c1.getString(c1
							.getColumnIndex(Restaurant.COLUMN_NAME_NAME)));
					res.setTitle(c1.getString(c1
							.getColumnIndex(Restaurant.COLUMN_NAME_TITLE)));
					res.setDeliveryCharge(c1.getDouble(c1
							.getColumnIndex(Restaurant.COLUMN_NAME_DELIVERY_CHARGE)));
					// contactListItems.setName(c1.getString(c1
					// .getColumnIndex("name")));
					// contactListItems.setPhone(c1.getString(c1
					// .getColumnIndex("phone")));

					resList.add(res);

				} while (c1.moveToNext());
			}
		}
		c1.close();

		// 3.create custom adapter class and instantiate it
		RestaurantListAdapter resListAdapter = new RestaurantListAdapter(
				ResListActivity.this, resList);

		// 4.Once you have a reference of listview, you can just call setAdapter
		// method by passing the ArrayAdapter to be bound.
		restListView.setAdapter(resListAdapter);
		// onclick
		restListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {
				Object o = restListView.getItemAtPosition(position);
				ModelRestaurant obj_itemDetails = (ModelRestaurant) o;
				Toast.makeText(
						ResListActivity.this,
						"You have chosen : " + " "
								+ obj_itemDetails.getResName(),
						Toast.LENGTH_LONG).show();

				// start another activity to show the menu list of this
				// restaurant
				Intent resListAct = new Intent(ResListActivity.this,
						MenuListActivity.class);
				int resId = obj_itemDetails.getId();
				resListAct.putExtra("resId", resId);
				resListAct.putExtra("deiveryCharge",
						obj_itemDetails.getDeliveryCharge());
				startActivity(resListAct);
			}
		});

	}
}
