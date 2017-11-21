/*
 * adds to cart
 */
package com.example.res_e_com;

import db.CartHandler;
import db.SqlHandler;
import db.ResEcomDbContract.Restaurant;
import model.ModelCart;
import model.ModelMenu;
import model.ModelRestaurant;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AddCartActivity extends ActionBarActivity {
	private static final int RESULT_SETTINGS = 1;
	SqlHandler sqlHandler;
	boolean offline = true;
	Button decreaseBtn;
	Button increaseBtn;
	Button addToCart;
	TextView menu_quantity_in_selected;
	TextView txt_tot_price;
	TextView basic_price;
	TextView menu_name;
	TextView menu_ingredient;
	//selected menu item information
	ModelMenu resMenu;//the menu item selected
	int id;
	String name;
	String ingredient;
	double price;
	double deliveryCharge;
	int resId;
	int quantity=1;//at first one item is selected 
	double totPrice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_cart);
		sqlHandler = new SqlHandler(this);
		//recieve the menu which was selected and passed through intent
		resMenu=(ModelMenu) getIntent().getSerializableExtra("resMenu");
		//retrieve other infos
		id=resMenu.getId();
		name=resMenu.getName();
		ingredient=resMenu.getIngredient();
		price=resMenu.getPrice();
		resId=resMenu.getRestaurant_id();
		deliveryCharge=getIntent().getExtras().getDouble("deliveryCharge");
		/*
		 * get delivery charge o this restaurant
		 */
		// retrieve data from sqlite
				String query = "SELECT * FROM restaurant where id="+resId;
				Cursor c1 = sqlHandler.selectQuery(query);
				if (c1 != null & c1.getCount() != 0) {
					if (c1.moveToFirst()) {
						do {
							deliveryCharge= c1.getDouble(c1
									.getColumnIndex(Restaurant.COLUMN_NAME_DELIVERY_CHARGE));
							// contactListItems.setName(c1.getString(c1
							// .getColumnIndex("name")));
							// contactListItems.setPhone(c1.getString(c1
							// .getColumnIndex("phone")));
							break;

						} while (c1.moveToNext());
					}
				}
				c1.close();

		//handle button action on the activity
		decreaseBtn=(Button) findViewById(R.id.btn_decrease_cart);
		increaseBtn=(Button) findViewById(R.id.btn_increase_cart);
		addToCart=(Button) findViewById(R.id.btn_add_to_cart);
		//set the data on view
		menu_quantity_in_selected=(TextView) findViewById(R.id.menu_quantity_in_selected);
		menu_quantity_in_selected.setText("Quantity "+quantity);
		
		//calculate price
		txt_tot_price=(TextView) findViewById(R.id.txt_tot_price);
		totPrice=quantity*price;
		txt_tot_price.setText("Tk "+totPrice);
		
		basic_price=(TextView) findViewById(R.id.basic_price);
		basic_price.setText("Tk "+price);
		
		menu_name=(TextView) findViewById(R.id.menu_name);
		menu_name.setText(name);
		
		menu_ingredient=(TextView) findViewById(R.id.menu_ingredient);
		menu_ingredient.setText(ingredient);
		
		//onclick actions
		decreaseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(quantity==1)return;//should choose at least one
				else{
					quantity--;
					calculateTotPrice();
					updateQuantityView();
				}
			}

			
		});
		increaseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					quantity++;
					//update price on view
					calculateTotPrice();
					updateQuantityView();
			}

			
		});
		
		addToCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// intialize context of carthandler
				CartHandler cartHandler=new CartHandler(AddCartActivity.this);
				//add to the cart this menu
				ModelCart cart=new ModelCart(id,quantity,price,deliveryCharge);
				CartHandler.addToCart(cart);
			}
		});
		
	}
	private void updateQuantityView() {
		menu_quantity_in_selected.setText("Quantity "+quantity);
		txt_tot_price.setText("Tk "+totPrice);
		
	}

	private void calculateTotPrice() {
		totPrice=price*quantity;
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
		return super.onOptionsItemSelected(item);
	}
	private void showCart(){
		Intent i = new Intent(AddCartActivity.this, ShowCartActivity.class);
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
