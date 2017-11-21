package com.example.res_e_com;

/*
 * show the menus
 */

import java.util.ArrayList;



import android.util.Log;
import android.view.Menu;
import db.SqlHandler;
import db.ResEcomDbContract.ResMenu;

import listviewAdapter.MenuListAdapter;
import model.ModelMenu;
import model.ModelRestaurant;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MenuListActivity extends ActionBarActivity {
	private static final int RESULT_SETTINGS = 1;
	ListView menuListView;
	SqlHandler sqlHandler;
	double deliveryCharge=0;
	boolean offline;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_list);
		sqlHandler=new SqlHandler(this);
		//4 steps to create a list view or custom listview
		//1. get the reference of listview
		menuListView=(ListView) findViewById(R.id.menu_list_view);
		//2.get data for listview
		
		//create some data for test
		ArrayList<ModelMenu>resList=new ArrayList<ModelMenu>();
		/*
		 * give some data manually
		 */
		/*
		ModelRestaurant res=new ModelRestaurant();
		res.setResName("Jun");
		resList.add(res);
		
		ModelRestaurant res1=new ModelRestaurant();
		res1.setResName("Jun1");
		resList.add(res1);
		
		ModelRestaurant res2=new ModelRestaurant();
		res2.setResName("Jun2");
		resList.add(res2);
		*/
		
		/*
		 * data from sqlite db
		 */
		//insert data in db
//		sqlHandler.dropTable();
//		sqlHandler.createTable();
//		sqlHandler.insertData();
		
		//get the id of the restaurant selected from intent extra
		int resId=getIntent().getExtras().getInt("resId");
		deliveryCharge=getIntent().getExtras().getDouble("deiveryCharge");
		//retrieve data from sqlite
		String query = "SELECT * FROM res_menu where restaurant_id="+resId;
		  Cursor c1 = sqlHandler.selectQuery(query);
		  if (c1 != null & c1.getCount() != 0) {
		   if (c1.moveToFirst()) {
		    do {
		    	/*
		    	 * create the menus list
		    	 */
		    	ModelMenu res = new ModelMenu();
		    	res.setId(c1.getInt(c1.getColumnIndex(ResMenu.COLUMN_NAME_ID)));
		    	res.setRestaurant_id(c1.getInt(c1.getColumnIndex(ResMenu.COLUMN_NAME_RESTAURANT_ID)));
		    	res.setCatagory_id(c1.getInt(c1.getColumnIndex(ResMenu.COLUMN_NAME_CATEGORY_ID)));
		    	res.setName(c1.getString(c1.getColumnIndex(ResMenu.COLUMN_NAME_NAME)));
		    	res.setIngredient(c1.getString(c1.getColumnIndex(ResMenu.COLUMN_NAME_INGREDIENT)));
		    	res.setPrice(c1.getDouble(c1.getColumnIndex(ResMenu.COLUMN_NAME_PRICE)));
//		     contactListItems.setName(c1.getString(c1
//		       .getColumnIndex("name")));
//		     contactListItems.setPhone(c1.getString(c1
//		       .getColumnIndex("phone")));
		     
		    	resList.add(res);
		 
		    } while (c1.moveToNext());
		   }
		  }
		  c1.close();
		
		//3.create custom adapter class and instantiate it
		MenuListAdapter resListAdapter =new MenuListAdapter(MenuListActivity.this,resList);
		
		//4.Once you have a reference of listview, you can just call setAdapter method by passing the ArrayAdapter to be bound.
		menuListView.setAdapter(resListAdapter);
		
		
		//onclick
		menuListView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> a, View v, int position, long id) { 
        		Object o = menuListView.getItemAtPosition(position);
        		ModelMenu obj_itemDetails = (ModelMenu)o;
        		Toast.makeText(MenuListActivity.this, "You have chosen : " + " " + obj_itemDetails.getId(), Toast.LENGTH_LONG).show();
        		
        		//start another activity to show the menu  of this restaurant
        		Intent addCartAct=new Intent(MenuListActivity.this,AddCartActivity.class);
        		addCartAct.putExtra("resMenu", obj_itemDetails);
        		addCartAct.putExtra("deliveryCharge", deliveryCharge);
        		
    			startActivity(addCartAct);
        	}  
        });
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
//		MenuButtonClickHandler m=new MenuButtonClickHandler(this);
//		m.handler(id);
		if (id == R.id.action_settings) {
			Intent i = new Intent(this, UserSettingActivity.class);
			startActivityForResult(i, RESULT_SETTINGS);
			return true;
		}
		if(id==R.id.action_show_cart){
			showCart();
		}
		if(id==R.id.action_show_tracking){
			Intent i = new Intent(MenuListActivity.this, TrackingActivity.class);
	        startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	private void showCart(){
		Intent i = new Intent(MenuListActivity.this, ShowCartActivity.class);
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
