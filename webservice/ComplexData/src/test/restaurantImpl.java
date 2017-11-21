package test;
//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class restaurantImpl {
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/res_ecom";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";

	   Connection conn = null;
	   Statement stmt = null;

	public restaurant getRest(String name){
		restaurant res=new restaurant();
		res.setId(1);
		res.setName("jun");
		res.setAddress("ban");
		return res;
	}
	/*
	 * get order from client
	 */
	/*
	 * call from browser
	 * http://192.168.1.4:8080/ComplexData/services/restaurantImpl/orderToServer?user=%22a||b||a@g||998||dh||ban||11||32||4||fdsafsdf||%22&cart=%221,3,175.0;2,1,200.0;3,2,220.0%22&delivery_type=%22Home%22
	 */
	public String orderToServer(String user, String cart,String delivery_type){
		String trackId="";
		double priceOfMenus = 0;
		int restaurant_id = 0;
		double delivery_charge=0.0;
		String res="Error";
		System.out.println(user);
		System.out.println(cart);
		//tem data
		user="a||b||a@g||998||dh||ban||11||32||4||fdsafsdf||";
		cart="1,3,175.0;2,1,200.0;3,2,220.0";
		//get user info splitting ||
		
		String userDelims="[||]";
		String[] userTokens=user.split(userDelims);
		String first_name=userTokens[0];
		String last_name=userTokens[1];
		String email=userTokens[2];
		String phn_num=userTokens[3];
		String city=userTokens[4];
		String area=userTokens[5];
		String street=userTokens[6];
		String house=userTokens[7];
		String floor=userTokens[8];
		String customer_comment=userTokens[9];
		//get different menus spliting ;
		String cartDelims = "[;]";
		String[] cartTokens = cart.split(cartDelims);
		int cartItems=cartTokens.length;
		if(cartItems==0){//no item in cart, so no order can take place
			res="Error : No item in cart";
			return res;
		}
		//create carts;
		int i;
		List<ModelCart> cartList=new ArrayList<ModelCart>();
		for(i=0;i<cartItems;i++){
			//split the cartTokens with ,
			String[] cartInfo=cartTokens[i].split("[,]");
			ModelCart tmpCart=new ModelCart();
			tmpCart.setMenuId(Integer.parseInt(cartInfo[0]));
			tmpCart.setQuantity(Integer.parseInt(cartInfo[1]));
			tmpCart.setPrice(Double.parseDouble(cartInfo[2]));
			tmpCart.setTotal_price(tmpCart.getPrice()*tmpCart.getQuantity());
			cartList.add(tmpCart);
		}

		
		//buildquery

		
		   try{
			      //STEP 2: Register JDBC driver
			      Class.forName("com.mysql.jdbc.Driver");

			      //STEP 3: Open a connection
			      System.out.println("Connecting to database...");
			      conn = DriverManager.getConnection(DB_URL,USER,PASS);

			      //STEP 4: Execute a query
			      System.out.println("find resid from menu id");
			      stmt = conn.createStatement();
			      
					//find resid from menu id
			      String sql;
			      sql = "SELECT restaurant_id FROM menus where id="+cartList.get(0).getMenuId();
			      System.out.println(sql);
			      ResultSet rs = stmt.executeQuery(sql);
			      
			      //STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			    	  restaurant_id=rs.getInt("restaurant_id");
			    	  break;//just first result will do, because all the cart menu from same restaurant
			    	  
			    	  
//			    	 ModelRestaurant res=new ModelRestaurant(rs.getInt("id"), rs.getString("name"), rs.getString("title"), rs.getDouble("delivery_charge"));
//			         int id  = rs.getInt("id");
//			         String name = rs.getString("resName");
//			         St
//			         //Display values
//			         System.out.print("ID: " + id);
//			         System.out.print(", Age: " + age);
//			    	 myList.add(res);
			      }
			      //STEP 6: Clean-up environment
			      rs.close();
//			      stmt.close();
			      
			    //STEP 4: Execute a query
			      System.out.println("find del charge from res id");
			      stmt = conn.createStatement();
			    //find delivery fee from resid
			      sql = "SELECT delivery_charge FROM restaurant where id="+restaurant_id;
			      System.out.println(sql);
			      rs = stmt.executeQuery(sql);
			      
			      //STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			    	  delivery_charge=rs.getDouble("delivery_charge");
			    	  break;//just first result will do, because all the cart menu from same restaurant
			    	  
			      }
			      
			      //STEP 6: Clean-up environment
			      rs.close();
//			      stmt.close();
			      
			    //STEP 4: Execute a query
			      System.out.println("find del charge from res id");
			      stmt = conn.createStatement();
			    //find delivery fee from resid
			      sql = "SELECT delivery_charge FROM restaurant where id="+restaurant_id;
			      System.out.println(sql);
			      rs = stmt.executeQuery(sql);
			      
			      //STEP 5: Extract data from result set
			      while(rs.next()){
			         //Retrieve by column name
			    	  delivery_charge=rs.getDouble("delivery_charge");
			    	  break;//just first result will do, because all the cart menu from same restaurant
			    	  
			      }
			      
			      //STEP 6: Clean-up environment
			      rs.close();
//			      stmt.close();
			      
			     //calculate price
			      for(i=0;i<cartItems;i++){
			    	  priceOfMenus+=cartList.get(i).getTotal_price();
			      }
			      double vat = (priceOfMenus * 15) / 100.00;
			      double finalPrice = priceOfMenus + vat + delivery_charge;
			    //insert in orders table
					 sql="INSERT INTO `res_ecom`.`orders` (`id`, `is_registered_user`, `user_id`, `delivery_address_id`, "
					 		+ "`phn_num`, `is_home_delive`, `desired_delivery_date`, `desired_delivery_time`, `delivered_date`, "
					 		+ "`created_date`, `created_time`, `delivered_time`, `status_id`, `total_price_befor_offer`, "
					 		+ "`offer_id`, `final_price`, `customer_comment`, `city`, `area`, `street`, `house`, `floor`, "
					 		+ "`first_name`, `last_name`, `email`, `supplier_comment`, `delivery_type`, `status`, "
					 		+ "`order_tracking_id`,`delivery_charge`) "
					 		+ "VALUES (NULL, 'N', NULL, NULL,"
					 		+ "'"+phn_num+"',NULL, NULL, NULL,NULL, "
					 				+ "NOW(),NULL, NULL, NULL, NULL,"
					 				+ "NULL,'" + finalPrice+"','"+customer_comment+"','"+city+"','"+area+"','"+street+"','"+house+"','"+floor+"',"
					 						+ "'"+first_name+"','"+last_name+"','"+email+"',NULL,'"+delivery_type+"','New', NULL,'"+delivery_charge+"');";
					 System.out.println(sql);
/*
 * find the autogenerated key by the server
 * good help http://stackoverflow.com/questions/16864297/get-the-latest-inserted-value-for-an-identity-primary-key
 * http://stackoverflow.com/questions/4246646/mysql-java-get-id-of-the-last-inserted-value-jdbc?lq=1
 */
					 stmt.execute(sql); //query where the server generates a value for
					                                  //an auto incremented column
					 rs = stmt.getGeneratedKeys();
					 int orderId = 0;
					 if (rs.next()) { 
						 orderId= rs.getInt(1);
					 }
					 System.out.println("order id:"+orderId);
					  //STEP 6: Clean-up environment
				      rs.close();
					 //create a tracking id
					 //generate random string http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
					 trackId=Integer.toString(orderId)+"_"+ UUID.randomUUID().toString();//orderid_random. order id is unique, so the tracking ir
					 trackId=trackId.substring(0, 6);//6 digits tracking number
					 //send this tracking id to customer
					 res=trackId;
					 //update the row with track id
					 sql="UPDATE orders SET order_tracking_id='"+trackId+"' WHERE id='"+orderId+"'";
					 System.out.println(sql);
					 stmt.execute(sql);
					 
					 //insert data in ordered_menu table
					 for(i=0;i<cartItems;i++){
						 sql="INSERT INTO `res_ecom`.`ordered_menus` (`id`, `order_id`, `menu_id`, `quantity`, `total_price`, "
						 		+ "`restaurant_id`, `unit_price`) "
						 		+ "VALUES (NULL, '"+orderId+"', '"+cartList.get(i).getMenuId()+"', '"+cartList.get(i).getQuantity()+"',"
						 				+ " '"+cartList.get(i).getTotal_price()+"', '"+restaurant_id+"', '"+cartList.get(i).getPrice()+"')";
				    	 System.out.println(sql);
				    	 stmt.execute(sql);
				      }
					 
				      //STEP 6: Clean-up environment
//				      rs.close();
			      conn.close();
			   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			      res="Error : in server, please try again later";
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			      res="Error : in server, please try again later";
			   }finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            stmt.close();
			      }catch(SQLException se2){
			      }// nothing we can do
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException se){
			         se.printStackTrace();
			      }//end finally try
			   }//end try

		return res;
	}
	public List<restaurant>getRestList(String name){
		List<restaurant> myList = new ArrayList<>();
		restaurant res=new restaurant();
		res.setId(1);
		res.setName("jun");
		res.setAddress("ban");
		myList.add(res);
		
		restaurant res1=new restaurant();
		res1.setId(2);
		res1.setName("jun2");
		res1.setAddress("ban2");
		myList.add(res1);
		return myList;
	}
	
	//receive data from client
	public List<restaurant>cartFromClient(restaurant name){
		System.out.println(name.address);
		System.out.println(name.name);
		System.out.println(name.id);
		
		List<restaurant> myList = new ArrayList<>();
		restaurant res=new restaurant();
		res.setId(1);
		res.setName("jun");
		res.setAddress("ban");
		myList.add(res);
		
		restaurant res1=new restaurant();
		res1.setId(2);
		res1.setName("jun2");
		res1.setAddress("ban2");
		myList.add(res1);
		return myList;
	}
	public List<ModelRestaurant>receiveInfo(String user){
		System.out.println("Service receiveInfo");
		List<ModelRestaurant> myList = new ArrayList<>();
		   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT * FROM restaurant";
	      System.out.println(sql);
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	    	 ModelRestaurant res=new ModelRestaurant(rs.getInt("id"), rs.getString("name"), rs.getString("title"), rs.getDouble("delivery_charge"));
//	         int id  = rs.getInt("id");
//	         String name = rs.getString("resName");
//	         St
//	         //Display values
//	         System.out.print("ID: " + id);
//	         System.out.print(", Age: " + age);
	    	 myList.add(res);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");

//		System.out.println(user);
//		List<ModelRestaurant> myList = new ArrayList<>();
//		ModelRestaurant res=new ModelRestaurant();
//		res.setId(1);
//		res.setResName("jun");
//		res.setTitle("ban");
//		res.setDeliveryCharge(100.00);
//		myList.add(res);
//		
//		ModelRestaurant res1=new ModelRestaurant();
//		res1.setId(2);
//		res1.setResName("jun2");
//		res1.setTitle("ban2");
//		res1.setDeliveryCharge(1020.00);
//		myList.add(res1);
		return myList;
	}
	public String trackInfo(String trackId){
		String res="";
		System.out.println("trackInfo");
//		List<ModelRestaurant> myList = new ArrayList<>();
		   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT status FROM orders where order_tracking_id ='"+trackId+"'";
	      System.out.println(sql);
	      ResultSet rs = stmt.executeQuery(sql);

	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	    	  res=rs.getString("status");
	    	  System.out.println(res);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");

//		System.out.println(user);
//		List<ModelRestaurant> myList = new ArrayList<>();
//		ModelRestaurant res=new ModelRestaurant();
//		res.setId(1);
//		res.setResName("jun");
//		res.setTitle("ban");
//		res.setDeliveryCharge(100.00);
//		myList.add(res);
//		
//		ModelRestaurant res1=new ModelRestaurant();
//		res1.setId(2);
//		res1.setResName("jun2");
//		res1.setTitle("ban2");
//		res1.setDeliveryCharge(1020.00);
//		myList.add(res1);
		return res;
	}

	public List<ModelMenu>sendMenuListToClient(String user){
		List<ModelMenu> menuList = new ArrayList<>();
		   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      System.out.println("Connecting to database...");
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT * FROM menus";
	      System.out.println(sql);
	      ResultSet rs = stmt.executeQuery(sql);
	      
	      //STEP 5: Extract data from result set
	      while(rs.next()){
	         //Retrieve by column name
	    	 ModelMenu res=new ModelMenu(rs.getInt("id"), rs.getString("name"), rs.getString("ingredient"), rs.getDouble("price"),rs.getInt("restaurant_id"),rs.getInt("catagory_id"));;
//	         int id  = rs.getInt("id");
//	         String name = rs.getString("resName");
//	         St
//	         //Display values
//	         System.out.print("ID: " + id);
//	         System.out.print(", Age: " + age);
	    	 menuList.add(res);
	      }
	      //STEP 6: Clean-up environment
	      rs.close();
	      stmt.close();
	      conn.close();
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Goodbye!");

//		System.out.println(user);
//		List<ModelRestaurant> myList = new ArrayList<>();
//		ModelRestaurant res=new ModelRestaurant();
//		res.setId(1);
//		res.setResName("jun");
//		res.setTitle("ban");
//		res.setDeliveryCharge(100.00);
//		myList.add(res);
//		
//		ModelRestaurant res1=new ModelRestaurant();
//		res1.setId(2);
//		res1.setResName("jun2");
//		res1.setTitle("ban2");
//		res1.setDeliveryCharge(1020.00);
//		myList.add(res1);
		return menuList;
	}

}
