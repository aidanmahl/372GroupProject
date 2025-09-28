package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer's order in the system.
 */
public class Order {
    private int orderId;
    private long date;
    private double totalPrice;
    private String type;
    private String status;
    private ArrayList<FoodItem> foodList;

    /**
     * Creates an empty order with the status set to "INCOMING" and an empty food list (empty constructor).
     */
    public Order() {
        this.foodList = new ArrayList<>();
        this.status = "INCOMING";
    }

    /**
     * Creates a new order with the given ID, type, date, and an initial list of food items.
     *
     * @param orderId   The unique ID of the order
     * @param type      The type of order
     * @param date      The timestamp of the order
     * @param foodList  The initial list of food items
     */
    public Order(int orderId, String type, long date, List<FoodItem> foodList) {
        this.orderId = orderId;
        this.type = type;
        this.date = date;
        if (foodList != null) {
            this.foodList = new ArrayList<>(foodList);
        } else {
            this.foodList = new ArrayList<>();
        }
        this.status = "INCOMING";
        this.totalPrice = sumPrice();
    }

    /**
     * Gets the date when the order was placed.
     *
     * @return The order date
     */
    public long getDate() {
        return date;
    }

    /**
     * Gets the total price of the order.
     *
     * @return The total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Gets the type of the order.
     *
     * @return The order type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the current status of the order.
     *
     * @return The order status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Gets the list of food items in the order.
     *
     * @return The list of food items
     */
    public ArrayList<FoodItem> getFoodList() {
        return foodList;
    }

    /**
     * Gets the unique ID of the order.
     *
     * @return The order ID
     */
    public int getOrderID() {
        return orderId;
    }

    /**
     * Recalculates the total price of the current food list.
     *
     * @return The sum of all food items' prices multiplied by their quantities
     */
    public double sumPrice() {
        double sum = 0.0;

        if (foodList == null) {
            return 0;
        }

        // Calculates the total price of all items in the food list
        for (FoodItem item : foodList) {
            sum = sum + item.getPrice() * item.getQuantity();
        }

        return sum;
    }

    /**
     * Adds a single FoodItem to the order's food list.
     *
     * @param f The FoodItem to add
     * @return  true if the item was added successfully, false if the item is null
     */
    public boolean addFoodItem(FoodItem f) {
        if (f == null) {
            return false;
        }

        if (foodList == null) {
            foodList = new ArrayList<>();
        }

        boolean priceUpdate = foodList.add(f);

        if (priceUpdate) {
            totalPrice = sumPrice();
        }

        return priceUpdate;
    }

    /**
     * Updates the status of the order.
     *
     * @param newStatus The new status
     */
    public void setStatus(String newStatus) {
        if (newStatus.equals("IN PROGRESS") || newStatus.equals("COMPLETED") || newStatus.equals("INCOMING")) {
            status = newStatus;
        }
    }

    /**
     * Returns a formatted string representing the order, including all food items.
     *
     * @return A formatted string of the order
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(FoodItem foodItem: foodList){
            s.append(foodItem.toString());
        }
        return  "-----------\n"+
                "Order ID: " + orderId + "\n" +
                "Date: " + date + "\n" +
                "Status: " + status + '\n' +
                "Type: " + type + '\n' +
                "Items: " +
                s +
                String.format("\n\nTotal Price: $%.2f", totalPrice);
    }
}
