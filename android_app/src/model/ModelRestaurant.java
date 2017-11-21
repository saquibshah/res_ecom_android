/*
 * this class contains the information of restaurant, so that it will be used to provide data for the list view of restaurans 
 */
package model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
public class ModelRestaurant implements KvmSerializable {
	int id;
	String resName;
	String title;
	double deliveryCharge;

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

//	@Override
	public Object getProperty(int pid) {
		// TODO Auto-generated method stub
		switch (pid) {
		case 0:
			return this.id;

		case 1:
			return this.resName;

		case 2:
			return this.title;
		case 3:
			return this.deliveryCharge;
		default:
			break;

		}

		return null;
	}

//	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
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
			info.name = "resName";
			break;
		case 2:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "title";
			break;
		case 3:
			info.type = PropertyInfo.STRING_CLASS;
			info.name = "deliveryCharge";
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
			this.resName = value.toString();
			break;
		case 2:
			this.title = value.toString();
			break;
		case 3:
			this.deliveryCharge=Double.parseDouble(value.toString());
			break;
		}

	}

}
