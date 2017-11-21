package db;

import java.util.ArrayList;

import db.ResEcomDbContract.Cart;
import db.ResEcomDbContract.Restaurant;

import model.ModelCart;


import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


//this is a class to handle cart. Try to make it static so that we can habe somtheing singletone. dont need to instantiate it
/*
 * there is no way to make upper level static class in java, so mimic this behaviour. taken from accepted question of
 * http://stackoverflow.com/questions/7486012/static-classes-in-java
 */
public final class CartHandler {
	Context context;
	static SQLiteDatabase sqlDatabase;
	static ResEcomDbHelper dbHelper;
	public CartHandler(Context context){
		dbHelper = new ResEcomDbHelper(context);
		sqlDatabase = dbHelper.getWritableDatabase();
	}
	public static void deleteCartData(){
		String query="delete from cart";
		Cursor c1 = null;
		try {

			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();

			}
			sqlDatabase = dbHelper.getWritableDatabase();
			c1 = sqlDatabase.rawQuery(query, null);

		} catch (Exception e) {

			System.out.println("DATABASE ERROR " + e);

		}
	}
	public static boolean isItemExists(int menuId){
		String query="select * from cart where "+Cart.COLUMN_NAME_MENU_ID+"="+menuId;
		Cursor c1 = null;
		try {

			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();

			}
			sqlDatabase = dbHelper.getWritableDatabase();
			c1 = sqlDatabase.rawQuery(query, null);
			if(c1!=null && c1.getCount()>0)return true;
			else return false;

		} catch (Exception e) {

			System.out.println("DATABASE ERROR " + e);

		}
		return false;

	}
	public static void updateCart(int prevQuantity, ModelCart cart){
		sqlDatabase = dbHelper.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(Cart.COLUMN_NAME_QUANTITY, cart.getQuantity()+prevQuantity);

		// Which row to update, based on the ID
		String selection = Cart.COLUMN_NAME_MENU_ID + " = ?";
		String[] selectionArgs = { String.valueOf(cart.getMenuId()) };
		try {
		int count = sqlDatabase.update(
			Cart.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);
		}
		catch(Exception e){
			System.out.println("DATABASE ERROR " + e);
		}
	}
	public static void addToCart(ModelCart cart){
		int menuId=cart.getMenuId();
		//if this menu in already in cart, then add this quantity with precvious quntity, so do update in database
		if(isItemExists(menuId)){
			int prevQuantity;
			String query="select * from cart where "+Cart.COLUMN_NAME_MENU_ID+"="+menuId;
			Cursor c1 = null;
			try {

				if (sqlDatabase.isOpen()) {
					sqlDatabase.close();

				}
				sqlDatabase = dbHelper.getWritableDatabase();
				c1 = sqlDatabase.rawQuery(query, null);
				if(c1!=null && c1.getCount()>0){
					if (c1.moveToFirst()) {
						prevQuantity=c1.getInt(c1.getColumnIndex(Cart.COLUMN_NAME_QUANTITY));
						updateCart(prevQuantity,cart);
					}
				}

			} catch (Exception e) {

				System.out.println("DATABASE ERROR " + e);

			}

		}
		else{
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
		ContentValues values = new ContentValues();
		values.clear();
		values.put(Cart.COLUMN_NAME_MENU_ID, cart.getMenuId());
		values.put(Cart.COLUMN_NAME_PRICE, cart.getPrice());
		values.put(Cart.COLUMN_NAME_QUANTITY, cart.getQuantity());
		values.put(Cart.COLUMN_NAME_DELIVERY_CHARGE,cart.getDeliveryCharge());
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Cart.TABLE_NAME, null, values);
		}
	}
	public static void removeFromCart(ModelCart cart){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String SQL_DELETE_CART_ITEM="delete from "+Cart.TABLE_NAME+" where "+Cart.COLUMN_NAME_MENU_ID+"="+cart.getMenuId(); 
		try{
		db.execSQL(SQL_DELETE_CART_ITEM);
		}
		catch(Exception e){
			System.out.println("DATABASE ERROR " + e);
		}
	}
	public static Cursor getCart(){
		String query="select * from cart";
		Cursor c1 = null;
		try {

			if (sqlDatabase.isOpen()) {
				sqlDatabase.close();

			}
			sqlDatabase = dbHelper.getWritableDatabase();
			c1 = sqlDatabase.rawQuery(query, null);

		} catch (Exception e) {

			System.out.println("DATABASE ERROR " + e);

		}
		return c1;
		
	}
}
