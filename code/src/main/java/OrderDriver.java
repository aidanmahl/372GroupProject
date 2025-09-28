package main.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages a collection of orders in the system.
 * Provides methods to add, start, complete, display, and export orders.
 */
public class OrderDriver {
    private List<Order> orders;
    private List<Order> incompleteOrders;
    private List<Order> completeOrders;

    /**
     * Constructs a new OrderDriver with empty lists for all orders, incomplete orders, and completed orders (empty constructor).
     */
    public OrderDriver() {
        orders = new ArrayList<>();
        incompleteOrders = new ArrayList<>();
        completeOrders = new ArrayList<>();
    }

    /**
     * Adds a new order to the system.
     * The order is added to both the list of all orders and the list of incomplete orders.
     *
     * @param order The order to add
     */
    public void addOrder(Order order) {
        orders.add(order);
        incompleteOrders.add(order); // this list maybe not necessary, gui searches by status in for loops
    }

    /**
     * Starts an order if its status is "INCOMING".
     * Changes the status of the order to "IN PROGRESS".
     *
     * @param order The order to start
     */
    public void startOrder(Order order) {
        // only start if it's incoming, otherwise do nothing
        if ("INCOMING".equals(order.getStatus())) {
            order.setStatus("IN PROGRESS");
        }
    }

    /**
     * Completes an order if its status is "IN PROGRESS".
     * Changes the status to "COMPLETED", removes it from incompleteOrders, and adds it to completeOrders.
     *
     * @param order The order to complete
     */
    public void completeOrder(Order order) {
        // only complete if it's in progress, otherwise do nothing
        if ("IN PROGRESS".equals(order.getStatus())) {
            order.setStatus("COMPLETED");
            incompleteOrders.remove(order);
            completeOrders.add(order); // maybe not necessary
        }
    }


    /**
     * Finds an order by its ID and returns its details as a formatted string.
     *
     * @param orderID   The unique ID of the order
     * @return          A string representation of the order, or "Order not found" if no order matches the ID
     */
    public String displayOrder(int orderID) {
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order.toString();
            }
        }
        return "Order not found.";
    }

    /**
     * Creates a JSONArray of the Orders list
     * and puts them in a file in the directory code/src/main/java/export.
     * note: export is not pretty to do that we need libraries GSON or Jackson
     *
     * @param fileName      The name of the file to export to
     * @param orderDriver   The OrderDriver instance containing all orders
     * @return              true if the export succeeds, false otherwise
     */
    public static boolean exportOrdersToJSON(String fileName, OrderDriver orderDriver) {

        JSONArray ordersArray = new JSONArray();
        if (orderDriver.getOrders().isEmpty()) {
            return false; // no orders to export
        }
        for (Order order : orderDriver.getOrders()) {
            JSONObject ordersJSON = new JSONObject();
            ordersJSON.put("orderID", order.getOrderID());
            ordersJSON.put("status", order.getStatus());
            ordersJSON.put("totalPrice", String.format("%.2f", order.getTotalPrice()));
            ordersJSON.put("date", order.getDate());
            ordersJSON.put("type", order.getType());
            ordersJSON.put("completeTime", System.currentTimeMillis());

            JSONArray orderFoodsList = new JSONArray();
            for (FoodItem food : order.getFoodList()){
                JSONObject foodJSON = new JSONObject();
                foodJSON.put("name", food.getName());
                foodJSON.put("quantity", food.getQuantity());
                foodJSON.put("price", food.getPrice());
                orderFoodsList.add(foodJSON);
            }

            ordersJSON.put("foodList", orderFoodsList);

            ordersArray.add(ordersJSON);
        }

        String fileDirectory = "code/src/main/java/Export";
        String filePath = fileDirectory + "/" + fileName;

        File fileDir = new File(fileDirectory);
        if (!fileDir.exists()) {
            boolean created = fileDir.mkdir();
            if (!created) {
                System.out.println("Error creating directory: " + fileDirectory);
                return false;
            } else {
                System.out.println("Directory created: " + fileDirectory);
            }
        }

        // write ordersArray to a file as a single JSON array, with newlines between objects
        try(FileWriter fw = new FileWriter(filePath)) {
            fw.write("[\n");
            for (int i = 0; i < ordersArray.size(); i++) {
                fw.write(ordersArray.get(i).toString());
                if (i < ordersArray.size() - 1) {
                    fw.write(",\n");
                }
            }
            fw.write("\n]");
            fw.flush();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns a list of all orders in the system.
     *
     * @return List of all orders
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Returns a list of completed orders.
     *
     * @return List of completed orders
     */
    public List<Order> getCompleteOrders() { return completeOrders; }

    /**
     * Returns a list of incomplete orders.
     *
     * @return List of incomplete orders
     */
    public List<Order> getIncompleteOrders() { return incompleteOrders; }
}
