package test;

import java.io.Serializable;

//public class ModelMenu implements Parcelable{//we will pass ModelMenu object from one intent to another
	//we cannot pass object with putExtra. so, we have to use Parcelable
@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class ModelMenu implements Serializable{//we will pass ModelMenu object from one intent to another	
	int id;
	String name;
	String ingredient;
	double price;
	int restaurant_id;
	int catagory_id;
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

}
