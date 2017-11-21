/*
 * create the db contract
 */
package db;

import android.provider.BaseColumns;

/*
 * create a companion class, known as a contract class, which explicitly specifies the layout of your schema in a systematic and self-documenting way.

A contract class is a container for constants that define names for URIs, tables, and columns. The contract class allows you to use the same constants across all the other classes in the same package. This lets you change a column name in one place and have it propagate throughout your code.
 */
public final class ResEcomDbContract {
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.

	public ResEcomDbContract(){};
	
	/*
	 * 
	 * A good way to organize a contract class is to put definitions that are global to your whole database in the root level of the class. Then create an inner class for each table that enumerates its columns.
	 */
	
	/* Inner class that defines the table contents */
	
	/*
	 * By implementing the BaseColumns interface, your inner class can inherit a primary key field called _ID that some Android classes such as cursor adaptors will expect it to have. It's not required, but this can help your database work harmoniously with the Android framework.
	 * 
	 */
	public static abstract class  Restaurant implements BaseColumns{
		public static final String TABLE_NAME = "restaurant";
		
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_TITLE = "title";
//        public static final String COLUMN_NAME_PASS= "pass";
        public static final String COLUMN_NAME_PHN_NUM = "phn_num";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_AREA = "area";
        public static final String COLUMN_NAME_STREET= "street";
        public static final String COLUMN_NAME_HOUSE_NO = "house_no";
        public static final String COLUMN_NAME_FLOOR = "floor";
        public static final String COLUMN_NAME_RESTAURANT_PIC_URL = "restaurant_pic_url";
        public static final String COLUMN_NAME_DELIVERY_CHARGE = "delivery_charge";
	}
	
	public static abstract class  ResMenu implements BaseColumns{
		public static final String TABLE_NAME = "res_menu";
		
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_INGREDIENT = "ingredient";
        public static final String COLUMN_NAME_PRICE = "price";
//        public static final String COLUMN_NAME_PASS= "pass";
        public static final String COLUMN_NAME_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_NAME_CATEGORY_ID = "catagory_id";
        
	}
	public static abstract class  Cart implements BaseColumns{
		public static final String TABLE_NAME = "cart";
		
        public static final String COLUMN_NAME_MENU_ID = "menu_id";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_PRICE = "price";        
        public static final String COLUMN_NAME_DELIVERY_CHARGE = "delivery_charge";
	}
	public static abstract class  TrackOrder implements BaseColumns{
		public static final String TABLE_NAME = "track_order";
		
        public static final String COLUMN_NAME_MENU_ID = "track_order_id";
	}
}
