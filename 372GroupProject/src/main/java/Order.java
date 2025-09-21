/**
 * Resources
 * https://www.w3schools.com/java/java_arraylist.asp
 * https://www.w3schools.com/java/java_date.asp
 */

import java.time.LocalDateTime;
import java.util.List; 

public class Order {
	// Properties
	private int orderId;
	private String orderType;
	private LocalDateTime orderTime;
	private List<String> foodItems;
	private String orderStatus;
	
	public Order(int orderId, String orderType, List<String> foodItems, LocalDateTime orderTime) {
		this.orderId = orderId;
		this.orderType = orderType;
		this.foodItems = foodItems;
		this.orderTime = LocalDateTime.now();
		this.orderStatus = "INCOMPLETE";
	}
	
	
	// Getters
	
	public int getOrderId() {
		return orderId;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public List<String> getFoodItems() {
		return foodItems;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	// Methods
	
	public void startOrder() {
		
	}
	
	public void completeOrder() {
		orderStatus = "COMPLETED";
	}

	
//	public double getPrice() {
//		
//	}
	
//	public boolean isCompleted() {
//		
//	}
	
	public void displayOrder() {
		
	}
}
