package main.java; /**
 * Order class.
 */

import java.util.ArrayList;
import java.util.List; 

public class Order {
    // Properties
    private int orderId;
    private long date;
    private double totalPrice;
    private String type;
    private String status;
    private ArrayList<FoodItem> foodList;

    // Empty constructor
    public Order() {
        this.foodList = new ArrayList<>();
        this.status = "incoming";
    }

    // Constructor
    public Order(int orderId, String type, long date, List<FoodItem> foodList) {
        this.orderId = orderId;
        this.type = type;
        this.date = date;
        if (foodList != null) {
            this.foodList = new ArrayList<>(foodList);
        } else {
            this.foodList = new ArrayList<>();
        }
        this.status = "incoming";
        this.totalPrice = sumPrice();
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public long getDate() {
        return date;
    }

    public double getTotalPrice() {
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

    public int getOrderID() {
        return orderId;
    }

    // Methods

    /**
     * Recalculates the total price of the current food list.
     *
     * @return the total sum price of the order
     */
    public double sumPrice() {
        double sum = 0.0;

        if (foodList == null) {
            return 0;
        }

        // Calculates the total price of all items in the food list
        for (int i = 0; i < foodList.size(); i++) {
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

    //INCOMING, IN PROGRESS, COMPLETED
    public void setSatus(String newStatus) {
        if (!(newStatus.equals("IN PROGRESS") || newStatus.equals("COMPLETED") || newStatus.equals("INCOMING"))){
          status = newStatus;
        }
    }


    /**
     * (Will need to iterate food list, should be implemented as a toString for foodList object or have to handle array to string implementation. - joseph)
     * toString method for Order objects. returns a formatted string containing all info from order object
     *
     * @return String, formatted order
     */
    @Override
    public String toString() {
        return "OrderId: " + orderId + "\n" +
                "date: " + date + "\n" +
                "status: " + status + '\n' +
                "type: " + type + '\n' +
                "foodList: " + foodList +
                "totalPrice: $" + totalPrice + "\n" +
                "==========";
    }
}
