/*
 * this class is self explanatory
 */
package db;

import db.ResEcomDbContract.Cart;
import db.ResEcomDbContract.ResMenu;
import db.ResEcomDbContract.Restaurant;
import db.ResEcomDbContract.TrackOrder;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ResEcomDbHelper extends SQLiteOpenHelper {

	Context cxt;
	// If you change the database schema, you must increment the database
	// version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "ResEcom.db";

	/*
	 * only these 4 data types supported in sqlite
	 */
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";// floating point value
	private static final String BLOB_TYPE = " BLOB";

	private static final String COMMA_SEP = ",";

	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ Restaurant.TABLE_NAME + " (" + Restaurant.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY," + Restaurant.COLUMN_NAME_NAME + TEXT_TYPE
			+ COMMA_SEP + Restaurant.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_EMAIL + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_PHN_NUM + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_AREA + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_STREET + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_HOUSE_NO + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_FLOOR + TEXT_TYPE + COMMA_SEP
			+ Restaurant.COLUMN_NAME_DELIVERY_CHARGE + REAL_TYPE +

			" )";
	private static final String SQL_CREATE_RES_MENU=
			"CREATE TABLE "
			+ ResMenu.TABLE_NAME + " (" + ResMenu.COLUMN_NAME_ID
			+ " INTEGER PRIMARY KEY," + ResMenu.COLUMN_NAME_NAME + TEXT_TYPE
			+ COMMA_SEP + ResMenu.COLUMN_NAME_INGREDIENT+ TEXT_TYPE + COMMA_SEP
			+ ResMenu.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP
			+ ResMenu.COLUMN_NAME_RESTAURANT_ID+ INTEGER_TYPE+ COMMA_SEP
			+ ResMenu.COLUMN_NAME_CATEGORY_ID+ INTEGER_TYPE+ 
			" )";
	private static final String SQL_CREATE_CART=
			"CREATE TABLE "
			+ Cart.TABLE_NAME + " (" + Cart.COLUMN_NAME_MENU_ID
			+ " INTEGER PRIMARY KEY," + Cart.COLUMN_NAME_PRICE + REAL_TYPE + COMMA_SEP
			+ Cart.COLUMN_NAME_DELIVERY_CHARGE+REAL_TYPE
			+ COMMA_SEP + Cart.COLUMN_NAME_QUANTITY+ INTEGER_TYPE+
			" )";
	private static final String SQL_CREATE_TRACK_ORDER=
			"CREATE TABLE "
			+ TrackOrder.TABLE_NAME + " (" + TrackOrder.COLUMN_NAME_MENU_ID
			+ " TEXT"+
			" )";

	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + Restaurant.TABLE_NAME;
	private static final String SQL_DELETE_RES_MENU ="DROP TABLE IF EXISTS " + ResMenu.TABLE_NAME;
	private static final String SQL_DELETE_CART ="DROP TABLE IF EXISTS " + Cart.TABLE_NAME;
	public ResEcomDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		cxt=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		db.execSQL(SQL_DELETE_ENTRIES);
		dropTable(db);
		createTables(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);

	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

	public void dropTable(SQLiteDatabase db) {
		// (clean start) action query to drop table
		try {
			db.execSQL(SQL_DELETE_ENTRIES);
			db.execSQL(SQL_DELETE_RES_MENU);
			db.execSQL(SQL_DELETE_CART);
//			db.execSQL(" drop table restaurant; ");// no problem if there is no
													// table named myfriend
			Toast.makeText(cxt, "Table dropped", Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			Toast.makeText(cxt, "dropTable()\n" + e.getMessage(), 1).show();
		}
	}// dropTable
	
	public void createTables(SQLiteDatabase db) {
		try{
			db.execSQL(SQL_CREATE_ENTRIES);
		}catch(Exception e){
//			Toast.makeText(cxt, "createTables()\n" + e.getMessage(), 1).show();
		}
		try{
			db.execSQL(SQL_CREATE_RES_MENU);
		}catch(Exception e){
//			Toast.makeText(cxt, "createTables()\n" + e.getMessage(), 1).show();
		}
		try{
			db.execSQL(SQL_CREATE_CART);
		}catch(Exception e){
//			Toast.makeText(cxt, "createTables()\n" + e.getMessage(), 1).show();
		}
		try{
			db.execSQL(SQL_CREATE_TRACK_ORDER);
		}catch(Exception e){
//			Toast.makeText(cxt, "createTables()\n" + e.getMessage(), 1).show();
		}
	}
}
