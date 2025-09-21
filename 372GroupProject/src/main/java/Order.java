/**
 * Order class.
 */

import java.util.ArrayList;

public class Order {
	// Properties
	private int orderId;
	private long date;
	private float totalPrice;
	private String type;
	private String status;
	private ArrayList<FoodItem> foodList;
	
	// Empty constructor
	public Order() {
		this.foodList = new ArrayList<>();
		this.status = "incoming";
	}

	// Getters
	public int getOrderId() {
		return orderId;
	}

	public long getDate() {
		return date;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public ArrayList<FoodItem> getFoodList() {
		return foodList;
	}

	// Methods
	/**
	 * Calculates the total price of every food item in the list.
	 * 
	 * @return the total sum price of the order
	 */
	private float sumPrice() {
		float sum = 0;
		
		if(foodList == null) {
			return 0;
		}
		
		// Calculates the total price of all items in the food list
		for(int i = 0; i < foodList.size(); i++) {
			FoodItem item = foodList.get(i);
			sum = sum + item.getPrice() * item.getQuantity();
		}
		
		return sum;
	}
	
	/**
	 * Adds a single FoodItem to the order's food list.
	 * Initializes the list if it has not been created yet.
	 * 
	 * @param f the foodItem to add
	 * @return true if the item was added, false if the item is null
	 */
	public boolean addFoodItem(FoodItem f) {
		if(f == null) {
			return false;
		}
		
		if(foodList == null) {
			foodList = new ArrayList<>();
		}
		
		return foodList.add(f);
	}

	/**
	 * Adds a FoodItem to the order's food list.
	 * Initializes the list if it has not been created yet.
	 * 
	 * @param f FoodItem
	 */
	private void buildFoodList(FoodItem f) {
		if(f == null) {
			return;
		}
		
		if (foodList == null) {
			foodList = new ArrayList<>();
		}
		foodList.add(f);
	}
	
	/**
	 * Updates order status.
	 * 
	 * @return true if the status was updated, false if not
	 */
	public boolean updateStatus() {
		if("incoming".equalsIgnoreCase(status)) {
			status = "in progress";
			return true;
		}
		else if("in progress".equalsIgnoreCase(status)) {
			status = "completed";
			return true;
		}
		else {
			return false;
		}
	}
}
