/**
 * Resources
 * https://www.w3schools.com/java/java_arraylist.asp
 * https://www.w3schools.com/java/java_date.asp
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List; 

public class Order {
	// Properties
	private int orderId;
	private String orderType;
	private LocalDateTime orderTime;
	private List<String> foodItems;
	private String orderStatus;
	
	// Empty Constructor
	
	// Constructor
	
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
	
}
