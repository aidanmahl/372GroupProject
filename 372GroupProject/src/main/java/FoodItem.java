/**
 * FoodItem class.
 */
public class FoodItem {
	// Properties
	private String name;
	private int quantity;
	private double price;

	// Empty Constructor
	public FoodItem() {
		this.name = "Null";
		this.quantity = 0;
		this.price = 0.0;
	}
	
	// Constructor
	public FoodItem(String name, int quantity, double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}