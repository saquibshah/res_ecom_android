/*
 * seld explanatory class doing the queries
 */
package db;



import java.util.List;

import model.ModelMenu;
import model.ModelRestaurant;
import db.ResEcomDbContract.ResMenu;
import db.ResEcomDbContract.Restaurant;
import db.ResEcomDbContract.TrackOrder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class SqlHandler {
	public static final String DATABASE_NAME = "MY_DATABASE";
	public static final int DATABASE_VERSION = 1;
	Context context;
	SQLiteDatabase sqlDatabase;
	ResEcomDbHelper dbHelper;
	ProgressBar pg;
	public SqlHandler(Context context) {

		dbHelper = new ResEcomDbHelper(context);
		sqlDatabase = dbHelper.getWritableDatabase();
	}
	public void dropTable(){
		dbHelper.dropTable(sqlDatabase);
	}
	public void createTable(){
		dbHelper.createTables(sqlDatabase);
	}
	//download data
	public void downloadData(){
		
	}
	public void insertTrackOrder(String track){
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(TrackOrder.COLUMN_NAME_MENU_ID, track);
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(TrackOrder.TABLE_NAME, null, values);
		
	}
	// insert data fro restaurant table with dummy data for test
	public void insertData() {
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		// Restaurant.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
		// Restaurant.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_CITY+ TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_AREA + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_HOUSE_NO + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_FLOOR + TEXT_TYPE + COMMA_SEP +
		// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
		values.clear();
		values.put(Restaurant.COLUMN_NAME_ID, 1);
		values.put(Restaurant.COLUMN_NAME_NAME, "Live kitchen");
		values.put(Restaurant.COLUMN_NAME_TITLE, "Barger fast food & wraps");
		values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "40.00");
		// Insert the new row, returning the primary key value of the new row
		long newRowId;
		newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
		
		values.clear();
		values.put(Restaurant.COLUMN_NAME_ID, 2);
		values.put(Restaurant.COLUMN_NAME_NAME, "Wow Burger");
		values.put(Restaurant.COLUMN_NAME_TITLE, "Barger fast food");
		values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "20.00");
		// Insert the new row, returning the primary key value of the new row
		
		newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
		
		values.clear();
		values.put(Restaurant.COLUMN_NAME_ID, 3);
		values.put(Restaurant.COLUMN_NAME_NAME, "Tarka");
		values.put(Restaurant.COLUMN_NAME_TITLE, "Barger");
		values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "30.00");
		// Insert the new row, returning the primary key value of the new row
		newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
		
		/*
		 * insert for menu table
		 */
		values.clear();//clear the values,otherwise it will try to insert data in columns of previous table, which will throw error
		values.put(ResMenu.COLUMN_NAME_ID, 1);
		values.put(ResMenu.COLUMN_NAME_NAME, "Biriany");
		values.put(ResMenu.COLUMN_NAME_INGREDIENT, "Rice, beef, oil, onion, garlic, salt");
		values.put(ResMenu.COLUMN_NAME_PRICE, "80.00");
		values.put(ResMenu.COLUMN_NAME_RESTAURANT_ID, "1");
		// Insert the new row, returning the primary key value of the new row
		newRowId = db.insert(ResMenu.TABLE_NAME, null, values);
		
	}

	// insert data
//	insertMenuDownloadedData
		public void insertResDownloadedData(List<ModelRestaurant> list) {
			// Gets the data repository in write mode
			SQLiteDatabase db = dbHelper.getWritableDatabase();

			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			// Restaurant.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
			// Restaurant.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_CITY+ TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_AREA + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_HOUSE_NO + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_FLOOR + TEXT_TYPE + COMMA_SEP +
			// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
			int len=list.size();
			long newRowId;
			for(int i=0;i<len;i++){
				values.clear();
				values.put(Restaurant.COLUMN_NAME_ID, list.get(i).getId());
				values.put(Restaurant.COLUMN_NAME_NAME, list.get(i).getResName());
				values.put(Restaurant.COLUMN_NAME_TITLE, list.get(i).getTitle());
				values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, list.get(i).getDeliveryCharge());
				// Insert the new row, returning the primary key value of the new row
				
				newRowId = db.insert(Restaurant.TABLE_NAME, null, values);	
			}
			
			
//			values.clear();
//			values.put(Restaurant.COLUMN_NAME_ID, 2);
//			values.put(Restaurant.COLUMN_NAME_NAME, "Wow Burger");
//			values.put(Restaurant.COLUMN_NAME_TITLE, "Barger fast food");
//			values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "20.00");
//			// Insert the new row, returning the primary key value of the new row
//			
//			newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
//			
//			values.clear();
//			values.put(Restaurant.COLUMN_NAME_ID, 3);
//			values.put(Restaurant.COLUMN_NAME_NAME, "Tarka");
//			values.put(Restaurant.COLUMN_NAME_TITLE, "Barger");
//			values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "30.00");
//			// Insert the new row, returning the primary key value of the new row
//			newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
//			
//			/*
//			 * insert for menu table
//			 */
//			values.clear();//clear the values,otherwise it will try to insert data in columns of previous table, which will throw error
//			values.put(ResMenu.COLUMN_NAME_ID, 1);
//			values.put(ResMenu.COLUMN_NAME_NAME, "Biriany");
//			values.put(ResMenu.COLUMN_NAME_INGREDIENT, "Rice, beef, oil, onion, garlic, salt");
//			values.put(ResMenu.COLUMN_NAME_PRICE, "80.00");
//			values.put(ResMenu.COLUMN_NAME_RESTAURANT_ID, "1");
//			// Insert the new row, returning the primary key value of the new row
//			newRowId = db.insert(ResMenu.TABLE_NAME, null, values);
			
		}
		// insert data
//		
			public void insertMenuDownloadedData(List<ModelMenu> list) {
				// Gets the data repository in write mode
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				// Create a new map of values, where column names are the keys
				ContentValues values = new ContentValues();
				// Restaurant.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
				// Restaurant.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_CITY+ TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_AREA + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_HOUSE_NO + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_FLOOR + TEXT_TYPE + COMMA_SEP +
				// Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP +
				int len=list.size();
				long newRowId;
				for(int i=0;i<len;i++){
					values.clear();
					values.put(ResMenu.COLUMN_NAME_ID, list.get(i).getId());
					values.put(ResMenu.COLUMN_NAME_NAME, list.get(i).getName());
					values.put(ResMenu.COLUMN_NAME_INGREDIENT, list.get(i).getIngredient());
					values.put(ResMenu.COLUMN_NAME_PRICE, list.get(i).getPrice());
					values.put(ResMenu.COLUMN_NAME_CATEGORY_ID, list.get(i).getCatagory_id());
					values.put(ResMenu.COLUMN_NAME_RESTAURANT_ID, list.get(i).getRestaurant_id());
					// Insert the new row, returning the primary key value of the new row
					
					newRowId = db.insert(ResMenu.TABLE_NAME, null, values);	
				}
				
				
//				values.clear();
//				values.put(Restaurant.COLUMN_NAME_ID, 2);
//				values.put(Restaurant.COLUMN_NAME_NAME, "Wow Burger");
//				values.put(Restaurant.COLUMN_NAME_TITLE, "Barger fast food");
//				values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "20.00");
//				// Insert the new row, returning the primary key value of the new row
//				
//				newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
//				
//				values.clear();
//				values.put(Restaurant.COLUMN_NAME_ID, 3);
//				values.put(Restaurant.COLUMN_NAME_NAME, "Tarka");
//				values.put(Restaurant.COLUMN_NAME_TITLE, "Barger");
//				values.put(Restaurant.COLUMN_NAME_DELIVERY_CHARGE, "30.00");
//				// Insert the new row, returning the primary key value of the new row
//				newRowId = db.insert(Restaurant.TABLE_NAME, null, values);
//				
//				/*
//				 * insert for menu table
//				 */
//				values.clear();//clear the values,otherwise it will try to insert data in columns of previous table, which will throw error
//				values.put(ResMenu.COLUMN_NAME_ID, 1);
//				values.put(ResMenu.COLUMN_NAME_NAME, "Biriany");
//				values.put(ResMenu.COLUMN_NAME_INGREDIENT, "Rice, beef, oil, onion, garlic, salt");
//				values.put(ResMenu.COLUMN_NAME_PRICE, "80.00");
//				values.put(ResMenu.COLUMN_NAME_RESTAURANT_ID, "1");
//				// Insert the new row, returning the primary key value of the new row
//				newRowId = db.insert(ResMenu.TABLE_NAME, null, values);
				
			}

	public Cursor selectQuery(String query) {
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
