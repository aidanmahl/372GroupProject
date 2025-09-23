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
        incompleteOrders.add(order);
    }

    public void startOrder(Order order) {
        if ("INCOMING".equals(order.getStatus())) {
            order.setStatus("IN PROGRESS");
        }
    }

    public void completeOrder(Order order) {
        if ("IN PROGRESS".equals(order.getStatus())) {
            order.setStatus("COMPLETED");
            incompleteOrders.remove(order);
            completeOrders.add(order);
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
