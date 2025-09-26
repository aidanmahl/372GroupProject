package main.java;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.FileWriter;


import java.io.FileWriter;
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

    public boolean exportOrdersToJSON(String filename) {


        //We may need an identifier for the day or time of the export - Rocky Xiong

        JSONArray ordersArray = new JSONArray();
        for (Order order : completeOrders) {
            JSONObject ordersJSON = new JSONObject();
            ordersJSON.put("orderID", order.getOrderID());
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

            ordersArray.add(ordersJSON);
        }

        try(FileWriter fw = new FileWriter(filename)) {
            fw.write(ordersArray.toJSONString());
            fw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false; //to be implemented
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getCompleteOrders() { return completeOrders; }

    public List<Order> getIncompleteOrders() { return incompleteOrders; }

}
