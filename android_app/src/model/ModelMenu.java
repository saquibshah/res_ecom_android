package model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
//public class ModelMenu implements Parcelable{//we will pass ModelMenu object from one intent to another
	//we cannot pass object with putExtra. so, we have to use Parcelable
@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class ModelMenu implements Serializable,KvmSerializable{//we will pass ModelMenu object from one intent to another	
	int id;
	String name;
	String ingredient;
	double price;
	int restaurant_id;
	int catagory_id;
	public ModelMenu(){
		
	}
	public ModelMenu(int id, String name, String ingredient, double price,
			int restaurant_id, int catagory_id) {
		super();
		this.id = id;
		this.name = name;
		this.ingredient = ingredient;
		this.price = price;
		this.restaurant_id = restaurant_id;
		this.catagory_id = catagory_id;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public int getCatagory_id() {
		return catagory_id;
	}
	public void setCatagory_id(int catagory_id) {
		this.catagory_id = catagory_id;
	}

//	@Override
	public Object getProperty(int pid) {
		// TODO Auto-generated method stub
		switch (pid) {
		case 0:
			return this.id;

		case 1:
			return this.name;

		case 2:
			return this.ingredient;
		case 3:
			return this.price;
		case 4:
			return this.restaurant_id;
		case 5:
			return this.catagory_id;

		
		default:
			break;

		}

		return null;
	}

//	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}

//	@Override
	public void getPropertyInfo(int index, Hashtable htable, PropertyInfo info) {
		// TODO Auto-generated method stub

		switch (index) {
		case 0:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "id";
			break;
		case 1:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "name";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "ingredient";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "price";
			break;
		case 4:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "restaurant_id";
			break;
		case 5:
			info.type = PropertyInfo.INTEGER_CLASS;
			info.name = "catagory_id";
			break;
		}
		

	}

	

//	@Override
	public void setProperty(int index, Object value) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.id = Integer.parseInt(value.toString());
			break;
		case 1:
			this.name = value.toString();
			break;
		case 2:
			this.ingredient = value.toString();
			break;
		case 3:
			this.price=Double.parseDouble(value.toString());
			break;
		case 4:
			this.restaurant_id = Integer.parseInt(value.toString());
			break;
		case 5:
			this.catagory_id= Integer.parseInt(value.toString());
			break;
		}

	}

}
