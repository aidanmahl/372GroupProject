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

    public void startIncomingOrder() {
        
    }

    public String displayOrder(int orderID) { //given an order ID, convert its fields to a string and return to GUI.
       if(orders.isEmpty()) {
           return "No orders found";
       }
        for(Order order : orders) {
           if(order.getOrderID() == orderID) {
               System.out.println(order);
           }
       }
        return "displayOrder: To be implemented";
    }

    public void completeIncomingOrder() {
        
    }

    public void displayAll() {
       
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean addOrder(String orderJSON) {
        try {
            Order newOrder = Parser.parseJSONOrder(orderJSON, orders.size() + 1); //creates a new order with the next available ID, starting at 1
            orders.add(newOrder);
            incompleteOrders.add(newOrder);
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return false;
        } 
        return true;   
    }

    public void completeAllOrders() {
        
    }
}
