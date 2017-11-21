package model;

public class ModelCart {
	int menuId;
	int quantity;
	double price;
	static double delivery_charge;//delivery charge will be same for all menu of this restaurant for which we are creating the cart
	
	public ModelCart(int menuId, int quantity, double price, double delCharge) {
//		super();
		this.menuId = menuId;
		this.quantity = quantity;
		this.price = price;
		delivery_charge=delCharge;
	}
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDeliveryCharge(){
		return delivery_charge;
	}
}
