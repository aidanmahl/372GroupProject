package main.java;

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
        return false; //to be implemented
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getCompleteOrders() { return completeOrders; }

    public List<Order> getIncompleteOrders() { return incompleteOrders; }

}
