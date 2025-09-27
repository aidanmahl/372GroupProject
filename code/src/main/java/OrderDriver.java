package main.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class OrderDriver {
    private List<Order> orders;
    private List<Order> incompleteOrders;
    private List<Order> completeOrders;

    public OrderDriver() {
        orders = new ArrayList<>();
        incompleteOrders = new ArrayList<>();
        completeOrders = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
        incompleteOrders.add(order); // this list maybe not necessary, gui searches by status in for loops
    }

    public void startOrder(Order order) {
        // only start if it's incoming, otherwise do nothing
        if ("INCOMING".equals(order.getStatus())) {
            order.setStatus("IN PROGRESS");
        }
    }

    public void completeOrder(Order order) {
        // only complete if it's in progress, otherwise do nothing
        if ("IN PROGRESS".equals(order.getStatus())) {
            order.setStatus("COMPLETED");
            incompleteOrders.remove(order);
            completeOrders.add(order); // maybe not necessary
        }
    }

    public void completeAllOrders() {
        for (Order order : incompleteOrders) {
            completeOrder(order);
        }
    }

    public String displayOrder(int orderID) { //given an order ID, convert its fields to a string and return to GUI.
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                return order.toString();
            }
        }
        return "Order not found.";
    }

    /**
     * The exportOrdersToJSON will create A JSONArray of the completedOrders list
     * and put them in a file in the directory code/src/main/java/export
     *
     * note: export is not pretty to do that we need libraries GSON or Jackson
     * @param fileName
     * @param orderDriver
     * @return
     */
    public static boolean exportOrdersToJSON(String fileName, OrderDriver orderDriver) {
        boolean exportSuccess;

        JSONArray ordersArray = new JSONArray();
        for (Order order : orderDriver.getCompleteOrders()) {
            JSONObject ordersJSON = new JSONObject();
            ordersJSON.put("orderID", order.getOrderID());
            ordersJSON.put("date", order.getDate());
            ordersJSON.put("type", order.getType());
            ordersJSON.put("completeTime", System.currentTimeMillis()); //should we add a complete time attribute to orders? - Rocky

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

        //The literal file path
        String fileDirectory = "code/src/main/java/Export";
        String filePath = fileDirectory + "/" + fileName;

        //checks if directory exists and if it successfully created the directory
        File fileDir = new File(fileDirectory);
        if (!fileDir.exists()) {
            boolean created = fileDir.mkdir();
            if (!created) {
                System.out.println("Error creating directory: " + fileDirectory);
            } else {
                System.out.println("Directory created: " + fileDirectory);
            }
        }

        //write ordersArray to a file
        try(FileWriter fw = new FileWriter(filePath)) {
            fw.write(ordersArray.toJSONString());
            fw.flush();
            exportSuccess = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return exportSuccess; //to be implemented
    }

    //getters
    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getCompleteOrders() { return completeOrders; }

    public List<Order> getIncompleteOrders() { return incompleteOrders; }

}
