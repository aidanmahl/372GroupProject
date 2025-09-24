package main.gui;

import main.java.Order;
import main.java.OrderDriver;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Dropdown for selecting an order for display, start, or complete actions.
 * Appears if the user selects start or complete order from the main menu - Also if they select individual order display in DisplayChoiceDialog.
 * Filters available orders based on the action.
 */
public class OrderSelectionDropdown {

    public enum Action { DISPLAY, START, COMPLETE } // Different actions that can be performed on the selected order

    /**
     * Constructs and shows the order selection dialog.
     * @param parentFrame the parent JFrame
     * @param driver the OrderDriver containing orders
     * @param action the action to perform (DISPLAY, START, COMPLETE)
     */
    public OrderSelectionDropdown(JFrame parentFrame, OrderDriver driver, Action action) {
        java.util.List<Order> orders = driver.getOrders();
        java.util.List<Order> filteredOrders = new ArrayList<>();

        for (Order order : orders) {
            // only start INCOMING orders, only complete IN PROGRESS orders, display all orders
            if (action == Action.START && "INCOMING".equals(order.getStatus())) {
                filteredOrders.add(order);
            } else if (action == Action.COMPLETE && "IN PROGRESS".equals(order.getStatus())) {
                filteredOrders.add(order);
            } else if (action == Action.DISPLAY) {
                filteredOrders.add(order);
            }
        }

        //appears if you try to complete an order and none have been started, for example
        if (filteredOrders.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No orders available for this action.");
            return;
        }

        String[] orderIDs = new String[filteredOrders.size()];
        //order array for dropdown menu
        for (int i = 0; i < filteredOrders.size(); i++) {
            orderIDs[i] = "Order #" + filteredOrders.get(i).getOrderID() + " - Status: " + filteredOrders.get(i).getStatus();
        }

        String selected = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Select an order:",
                "Order Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                orderIDs,
                orderIDs[0]
        );

        if (selected != null) {
            int selectedIndex = java.util.Arrays.asList(orderIDs).indexOf(selected);
            Order selectedOrder = filteredOrders.get(selectedIndex);
            handleOrderAction(parentFrame, driver, selectedOrder, action); //once order is selected, handle the action chosen from main menu in method below
        }
    }

    /**
     * Handles the selected order based on the specified action.
     * @param parentFrame the parent JFrame
     * @param driver the OrderDriver
     * @param order the selected Order
     * @param action the action to perform
     */
    private void handleOrderAction(JFrame parentFrame, OrderDriver driver, Order order, Action action) {
        String info = driver.displayOrder(order.getOrderID());
        switch (action) {
            case DISPLAY: //just display order info
                JOptionPane.showMessageDialog(parentFrame, info, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                break;
            case START: //display order info with confirmation button to start it or cancel
                int startConfirm = JOptionPane.showOptionDialog(
                        parentFrame,
                        info,
                        "Start Order?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Start Order", "Cancel"},
                        "Start Order"
                );
                if (startConfirm == JOptionPane.YES_OPTION) {
                    driver.startOrder(order);
                    JOptionPane.showMessageDialog(parentFrame, "Order #" + order.getOrderID() + " has been started.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case COMPLETE: // display order info with confirmation button to complete it or cancel
                int completeConfirm = JOptionPane.showOptionDialog(
                        parentFrame,
                        info,
                        "Complete Order?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Complete Order", "Cancel"},
                        "Complete Order"
                );
                if (completeConfirm == JOptionPane.YES_OPTION) {
                    driver.completeOrder(order);
                    JOptionPane.showMessageDialog(parentFrame, "Order #" + order.getOrderID() + " has been completed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
        }
    }
}
