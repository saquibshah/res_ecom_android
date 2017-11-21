package test;

/*
 * this class contains the information of restaurant, so that it will be used to provide data for the list view of restaurans 
 */

import java.util.Hashtable;

public class ModelRestaurant{
	int id;
	String resName;
	String title;
	double deliveryCharge;

	public ModelRestaurant(int id, String resName, String title,
			double deliveryCharge) {
		super();
		this.id = id;
		this.resName = resName;
		this.title = title;
		this.deliveryCharge = deliveryCharge;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public double getDeliveryCharge() {
		return deliveryCharge;
	}
	public void setDeliveryCharge(double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

}
