package main.java;

import java.util.List;
import java.util.ArrayList;

public class OrderDriver {
    private List<Order> orders;
    private List<Order> incompleteOrders;
    private List<Order> completeOrders;
    private Order incomingOrder;

    public OrderDriver() {
        orders = new ArrayList<>();
        incompleteOrders = new ArrayList<>();
        completeOrders = new ArrayList<>();
        incomingOrder = null;
    }

    public void startOrder(Order order) {

    }

    public String displayOrder(int orderID) { //given an order ID, convert its fields to a string and return to GUI.
        if (orders.isEmpty()) {
            return "No orders found";
        }
        for (Order order : orders) {
            if (order.getOrderID() == orderID) {
                System.out.println(order);
            }
        }
        return "displayOrder: To be implemented";
    }

    public void completeOrder() {

    }


    public List<Order> getOrders() {
        return orders;
    }

    public boolean addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
            return true;
        }
        return false; //duplicate order
    }

    public void completeAllOrders() {

    }

    public boolean exportOrderToJSON(String filename) {
        return false; //to be implemented
    }

}
