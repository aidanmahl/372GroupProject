package main.java;

/**
 * Represents a food item in an order.
 * Contains the item's name, quantity, and price.
 */
public class FoodItem {
	private String name;
	private int quantity;
	private double price;

    /**
     * Creates a FoodItem with default values (empty constructor).
     */
	public FoodItem() {
		this.name = "Null";
		this.quantity = 0;
		this.price = 0.0;
	}

    /**
     * Creates a FoodItem with the specified name, quantity, and price (non-empty constructor).
     *
     * @param name      String, The name of the food item
     * @param quantity  int, The number of units of the food item
     * @param price     double, The price per unit of the food item
     */
	public FoodItem(String name, int quantity, double price) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

    /**
     * Returns the name of the food item.
     *
     * @return  String, The name of the food item
     */
	public String getName() {
		return name;
	}

    /**
     * Sets the name of the food item.
     *
     * @param name String, The name of the food item
     */
	public void setName(String name) {
		this.name = name;
	}

    /**
     * Returns the quantity of the food item.
     *
     * @return int, The quantity of the food item
     */
	public int getQuantity() {
		return quantity;
	}

    /**
     * Sets the quantity of the food item.
     *
     * @param quantity int, The number of units of the food item
     */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    /**
     * Returns the price per unit of the food item.
     *
     * @return double, The price per unit of the food item
     */
	public double getPrice() {
		return price;
	}

    /**
     * Sets the price per unit of the food item.
     *
     * @param price double, The price per unit of the food item
     */
	public void setPrice(double price) {
		this.price = price;
	}

    /**
     * Returns a formatted string representing the food item.
     * Includes the quantity, name, and price per item.
     *
     * @return Formatted string of the food item
     */
    @Override
    public String toString() {
        return "\n  " + quantity + "x " + name + " - " + String.format("$%.2f", price) + " each";
    }
}