/*
 * shows the cart
 */
package com.example.res_e_com;

import java.util.ArrayList;

import listviewAdapter.MenuListAdapter;
import listviewAdapter.ShowCartListAdapter;
import model.ModelCart;
import model.ModelMenu;
import db.SqlHandler;
import db.ResEcomDbContract.Cart;
import db.ResEcomDbContract.ResMenu;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ShowCartActivity extends ActionBarActivity {
	private static final int RESULT_SETTINGS = 1;
	boolean offline = true;
	TextView res_info_in_cart;
	ListView cart_listView;
	Button add_product_in_checkout_btn;
	TextView price_subtotal;
	TextView price_vat;
	TextView price_delivery_fee_tk;
	TextView price_total;
	Button place_order;
	TextView menu_info_in_cart;
	SqlHandler sqlHandler;
	private RadioGroup rg = null;
	double priceOfMenus = 0;
	double vat;
	double delCharge;
	double totPrice;
	String deliveryType="Home delivery";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copy_of_activity_show_cart);
		
		rg = (RadioGroup) findViewById(R.id.radioGroup1);
		place_order=(Button) findViewById(R.id.place_order);
		//when click on place order button
		place_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int rad=rg.getCheckedRadioButtonId();
				boolean isDeliver=true;
				if(rad==R.id.radio_deliver){
					isDeliver=true;
					deliveryType="Home delivery";
				}
				if(rad==R.id.radio_pick_up){
					isDeliver=false;
					deliveryType="Pick up";
				}
				RadioButton rb=(RadioButton) findViewById(rad);
				Log.d("rad",(String) rb.getText());
				
				
				Intent order = new Intent(ShowCartActivity.this,
						PlaceOrder.class);
				order.putExtra("isDeliver",isDeliver);
				order.putExtra("deliveryType",deliveryType);
				order.putExtra("total", totPrice);
				startActivity(order);
			}
		});
		
		sqlHandler = new SqlHandler(this);
		res_info_in_cart = (TextView) findViewById(R.id.res_info_in_cart);

//		add_product_in_checkout_btn = (Button) findViewById(R.id.add_product_in_checkout_btn);
		price_subtotal = (TextView) findViewById(R.id.price_subtotal);
		price_vat = (TextView) findViewById(R.id.price_vat);
		price_delivery_fee_tk = (TextView) findViewById(R.id.price_delivery_fee_tk);
		price_total = (TextView) findViewById(R.id.price_total);
		place_order = (Button) findViewById(R.id.place_order);
		menu_info_in_cart = (TextView) findViewById(R.id.menu_info_in_cart);
		// 4 steps to create a list view or custom listview
		// 1. get the reference of listview
		 cart_listView = (ListView) findViewById(R.id.cart_listView);
		// 2.get data for listview

		ArrayList<ModelMenu> menuList = new ArrayList<ModelMenu>();
		ArrayList<ModelCart> cartList = new ArrayList<ModelCart>();

		/*
		 * data from sqlite db
		 */

		String query = "SELECT * FROM " + Cart.TABLE_NAME + " Inner Join "
				+ ResMenu.TABLE_NAME + " ON " + Cart.COLUMN_NAME_MENU_ID
				+ " = " + ResMenu.COLUMN_NAME_ID;
		Cursor c1 = sqlHandler.selectQuery(query);
		if (c1 != null & c1.getCount() != 0) {
			if (c1.moveToFirst()) {
				do {
					/*
					 * create the menu list
					 */
					ModelMenu res = new ModelMenu();

					res.setName(c1.getString(c1
							.getColumnIndex(ResMenu.COLUMN_NAME_NAME)));
					res.setIngredient(c1.getString(c1
							.getColumnIndex(ResMenu.COLUMN_NAME_INGREDIENT)));
					res.setPrice(c1.getDouble(c1
							.getColumnIndex(ResMenu.COLUMN_NAME_PRICE)));
					// contactListItems.setName(c1.getString(c1
					// .getColumnIndex("name")));
					// contactListItems.setPhone(c1.getString(c1
					// .getColumnIndex("phone")));

					menuList.add(res);

					/*
					 * create the cart list
					 */
					ModelCart cart = new ModelCart(
							c1.getInt(c1
									.getColumnIndex(Cart.COLUMN_NAME_MENU_ID)),
							c1.getInt(c1
									.getColumnIndex(Cart.COLUMN_NAME_QUANTITY)),
							c1.getDouble(c1
									.getColumnIndex(Cart.COLUMN_NAME_PRICE)),
							c1.getDouble(c1
									.getColumnIndex(Cart.COLUMN_NAME_DELIVERY_CHARGE)));
					cartList.add(cart);

				} while (c1.moveToNext());
			}
		}
		c1.close();
		int len = cartList.size();
		String list = "";
		if (len > 0) {//we have item in cart, so calculate the price
			for (int i = 0; i < len; i++) {
				double price = cartList.get(i).getQuantity()
						* cartList.get(i).getPrice();
				priceOfMenus += price;
				list += menuList.get(i).getName() + " "
						+ cartList.get(i).getQuantity() + "x"
						+ cartList.get(i).getPrice() + "=" + price + " Tk"
						+ "\n";
			}
			vat = (priceOfMenus * 15) / 100.00;
			delCharge = cartList.get(0).getDeliveryCharge();// all the menus
															// from a restaurant
															// has same del
															// charge
			// so, take the first one. and items in cart are from the same
			// restaurant
			totPrice = priceOfMenus + vat + delCharge;

			price_subtotal.setText("Tk " + priceOfMenus);
			price_total.setText("Tk " + totPrice);
			price_vat.setText("Tk " + vat);
			price_delivery_fee_tk.setText("Tk " + delCharge);
//			menu_info_in_cart.setText(list);
		}
		// menuName.setText(res.getName());
		// TextView priceTxt=(TextView)
		// convertView.findViewById(R.id.menu_price_on_cart);
		// double price=cart.getQuantity()*cart.getPrice();
		// priceTxt.setText(cart.getQuantity()+"x"+cart.getPrice()+"="+price
		// +" Tk");
		// 3.create custom adapter class and instantiate it
		 ShowCartListAdapter showCartListAdapter = new ShowCartListAdapter(
		 ShowCartActivity.this, menuList,cartList);

		// 4.Once you have a reference of listview, you can just call setAdapter
		// method by passing the ArrayAdapter to be bound.
		 cart_listView.setAdapter(showCartListAdapter);

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
		if (id == R.id.action_show_cart) {
			showCart();
		}
		return super.onOptionsItemSelected(item);
	}

	private void showCart() {
		Intent i = new Intent(ShowCartActivity.this, ShowCartActivity.class);
		startActivity(i);
	}

	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		offline = sharedPrefs.getBoolean("prefSendReport", true);

		Log.d("set", "" + offline);
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
