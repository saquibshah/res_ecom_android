package com.example.res_e_com;


import model.ModelCart;
import model.ModelMenu;
import db.CartHandler;
import db.SqlHandler;
import db.ResEcomDbContract.Cart;
import db.ResEcomDbContract.ResMenu;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

public class PlaceOrder extends ActionBarActivity {
	private static final int RESULT_SETTINGS = 1;
	boolean offline;
	SqlHandler sqlHandler;
	EditText editText1;
	EditText editText2;
	EditText editText3;
	EditText editText4;
	EditText editText5;
	EditText editText6;
	EditText editText7;
	EditText editText8;
	EditText editText9;
	EditText editText10;
	Button orderBtn;

	String userInfo="";
	String cartList="";
	String serviceResult=null;
	ProgressBar pg;
	
	String deliveryType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_order);
		deliveryType=getIntent().getExtras().getString(deliveryType);
		sqlHandler = new SqlHandler(this);
		pg = (ProgressBar) findViewById(R.id.progressBar2);
		editText1=(EditText) findViewById(R.id.editText1);
		editText2=(EditText) findViewById(R.id.editText2);
		editText3=(EditText) findViewById(R.id.editText3);
		editText4=(EditText) findViewById(R.id.editText4);
		editText5=(EditText) findViewById(R.id.editText5);
		editText6=(EditText) findViewById(R.id.editText6);
		editText7=(EditText) findViewById(R.id.editText7);
		editText8=(EditText) findViewById(R.id.editText8);
		editText9=(EditText) findViewById(R.id.editText9);
		editText10=(EditText) findViewById(R.id.editText10);
		orderBtn=(Button) findViewById(R.id.order_button);
		
		orderBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//send data to web service
				//get the cart
				
				String query = "SELECT * FROM " + Cart.TABLE_NAME;
				Cursor c1 = sqlHandler.selectQuery(query);
				int count=0;
				if (c1 != null & c1.getCount() != 0) {
					if (c1.moveToFirst()) {
						do {
					if(count>0)cartList+=";";		
							cartList+=Integer.toString(c1.getInt(c1
											.getColumnIndex(Cart.COLUMN_NAME_MENU_ID)))+",";
							cartList+=Integer.toString(c1.getInt(c1
											.getColumnIndex(Cart.COLUMN_NAME_QUANTITY)))+",";
							cartList+=Double.toString(c1.getDouble(c1
											.getColumnIndex(Cart.COLUMN_NAME_PRICE)))+",";
							cartList+=Double.toString(c1.getDouble(c1
											.getColumnIndex(Cart.COLUMN_NAME_DELIVERY_CHARGE)));
							

						} while (c1.moveToNext());
					}

				//get the user info
				
				userInfo+=editText1.getText()+"||";//tokenize the values of user using || so that we can parse acccordingly in service
				userInfo+=editText2.getText()+"||";
				userInfo+=editText3.getText()+"||";
				userInfo+=editText4.getText()+"||";
				userInfo+=editText5.getText()+"||";
				userInfo+=editText6.getText()+"||";
				userInfo+=editText7.getText()+"||";
				userInfo+=editText8.getText()+"||";
				userInfo+=editText9.getText()+"||";
				userInfo+=editText10.getText();
				
				AsyncCallWS task = new AsyncCallWS();
				// Call web service
				task.execute();
			}
			}});
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {

			serviceResult= WebServiceSendOrder.invokeHelloWorldWS(userInfo,cartList,deliveryType, "orderToServer",
					PlaceOrder.this);
			// displayText =
			// WebService.invokeHelloWorldWS(editText,"celsiusToFerenheit");
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pg.setVisibility(View.INVISIBLE);
			/*
			 * if there is error in service, it returns a message which starts with "error"
			 */
			String tmp=serviceResult.substring(0, 4);
			if(tmp.equalsIgnoreCase("Error")){
				showErroeDialog();
			}
			else{
				showConfirmDialog();	
			}
			
//			generateResListView();
		}
/*
 * call after error from service
 */
		private void showErroeDialog() {
			AlertDialog.Builder alert = new AlertDialog.Builder(PlaceOrder.this);
			alert.setTitle("Server Error");
			alert.setMessage("Your order can not be sent now, please try again later.");

			alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
					//close this activity, go to the activity
					}
					});

					alert.show();

					
				}

		private void showConfirmDialog() {
			AlertDialog.Builder alert = new AlertDialog.Builder(PlaceOrder.this);
			alert.setTitle("Order sent");
			alert.setMessage("Your order tracking id is "+serviceResult+"\n Supplier will contact with you soon.");
			//delete the cart after giving the order
			CartHandler cartHandler=new CartHandler(PlaceOrder.this);
			cartHandler.deleteCartData();
			//insert this tracking number in db
			sqlHandler.insertTrackOrder(serviceResult);
			alert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
					//close this activity, go to the activity
					}
					});

					alert.show();

					
				}
		@Override
		protected void onPreExecute() {
			pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
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
			Intent i = new Intent(PlaceOrder.this, TrackingActivity.class);
	        startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
	private void showCart(){
		Intent i = new Intent(PlaceOrder.this, ShowCartActivity.class);
        startActivity(i);
	}
	private void showUserSettings() {
		SharedPreferences sharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		offline= sharedPrefs.getBoolean("prefSendReport", true);

		Log.d("set",""+offline);
	}

}
