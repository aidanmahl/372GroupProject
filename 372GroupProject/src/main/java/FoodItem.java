
public class FoodItem {
	// Properties
	private String name;
	private double unitPrice;
	private int quantity;
	
	// Getters
	public String getName() {
		return name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}
	
	// Methods
	public double getTotalPrice() {
		return quantity * unitPrice;
	}
}
