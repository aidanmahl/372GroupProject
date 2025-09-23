package main.java;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The SystemGUI class is responsible for creating and managing the graphical user interface (GUI)
 * for the order management system. It provides buttons for various actions such as starting,
 * completing, and displaying orders, as well as importing and exporting JSONs.
 * There are 5 main buttons on the GUI, each with their own action listeners to handle user interactions.
 * Start Order and Complete Order buttons will open a dropdown menu to select an order from the list of orders.
 * Display Order(s) button will open a choice dialog.
 * Choosing individual order will open a dropdown menu to select an order from the list of orders.
 * Choosing group of orders will open a dropdown menu to select the status of orders to display.
 * The Add New Order JSON button opens a file chooser dialog to select a JSON file to import and send to Parser.
 * The Export All Orders to JSON button is a placeholder for future implementation.
 * The Exit button closes the application.
 */
public class SystemGUI {
    private OrderDriver driver;

    /**
     *
     * @param driver the OrderDriver instance to interact with order data
     */
    public SystemGUI(OrderDriver driver) {
        this.driver = driver;
        createAndShowGUI();
    }

    /**
     * Creates and displays the main GUI window with buttons for different actions.
     */
    private void createAndShowGUI() {
        // setting up the main window and buttons
        JFrame frame = new JFrame("ICS 372 Group Project - Order System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(7, 1, 10, 10));

        // Create buttons for each action
        JButton startOrderBtn = new JButton("Start Incoming Order");
        JButton completeOrderBtn = new JButton("Complete Order");
        JButton displayOrderBtn = new JButton("Display Order(s)");
        JButton addOrderBtn = new JButton("Add New Order JSON");
        JButton exportBtn = new JButton("Export All Orders to JSON");
        JButton exitBtn = new JButton("Exit");

        // Add action listeners to buttons
        startOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.START));
        completeOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.COMPLETE));
        displayOrderBtn.addActionListener(e -> showDisplayChoiceMenu(frame));
        addOrderBtn.addActionListener(e -> showFileChooser(frame));
        exitBtn.addActionListener(e -> frame.dispose());

        // Add buttons to the frame
        frame.add(new JLabel("Select an option:", SwingConstants.CENTER));
        frame.add(startOrderBtn);
        frame.add(displayOrderBtn);
        frame.add(completeOrderBtn);
        frame.add(addOrderBtn);
        frame.add(exportBtn);
        frame.add(exitBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Enum representing the different menu actions that can be performed on an order.
     */
    private enum MenuAction {
        DISPLAY, START, COMPLETE
    }

    /**
     * Displays a dropdown menu for selecting an order and performs the specified action on the selected order.
     * Dropdown menu appears if Display, Start, or Complete Order is selected from the main menu.
     * YET TO BE TESTED UNTIL PARSER IS COMPLETE
     * @param parentFrame the parent JFrame for the dialog
     * @param action      the action to perform (DISPLAY, START, or COMPLETE)
     */
    private void showOrderSelectionMenu(JFrame parentFrame, MenuAction action) {
        java.util.List<Order> orders = driver.getOrders();
        java.util.List<Order> filteredOrders = new ArrayList<>();

        // filter orders based on what the user wants to do
        for (Order order : orders) {
            if (action == MenuAction.START && "INCOMING".equals(order.getStatus())) {
                filteredOrders.add(order);
            } else if (action == MenuAction.COMPLETE && "IN PROGRESS".equals(order.getStatus())) {
                filteredOrders.add(order);
            } else if (action == MenuAction.DISPLAY) {
                filteredOrders.add(order);
            }
        }

        if (filteredOrders.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No orders available for this action.");
            return;
        }

        String[] orderIDs = new String[filteredOrders.size()];
        // showing order id and status so it's easier to pick an order based on user intebnt
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

        // grab the actual order object based on what was picked
        if (selected != null) {
            int selectedIndex = java.util.Arrays.asList(orderIDs).indexOf(selected);
            Order selectedOrder = filteredOrders.get(selectedIndex);
            handleOrderAction(parentFrame, selectedOrder, action);
        }
    }

    /**
     * Prompts user to choose between displaying an individual order or a group of orders by status.
     * @param parentFrame the parent JFrame for the dialog
     */
    private void showDisplayChoiceMenu(JFrame parentFrame) {
        Object[] options = {"Individual Order", "Multiple Orders"};
        // two buttons
        int choice = JOptionPane.showOptionDialog(
                parentFrame,
                "Display an individual order or a group?",
                "Display Order",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == 0) {
            showOrderSelectionMenu(parentFrame, MenuAction.DISPLAY);
        } else if (choice == 1) {
            showDisplayAllMenu(parentFrame);
        }
        // if they close the dialog, do nothing
    }

    /**
     * Displays a menu to choose which type of orders to display (INCOMING, IN PROGRESS, COMPLETED, ALL).
     *
     * @param parentFrame the parent JFrame for the dialog
     */
    private void showDisplayAllMenu(JFrame parentFrame) {
        String[] statuses = {"ALL", "INCOMING", "IN PROGRESS", "COMPLETED"};
        // let the user pick which group to show
        String selectedStatus = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Select the type of orders to display:",
                "Display All Orders",
                JOptionPane.PLAIN_MESSAGE,
                null,
                statuses,
                statuses[0]
        );

        //what group to display?
        if (selectedStatus != null) {
            java.util.List<Order> orders = driver.getOrders();
            StringBuilder result;
            // edit the header based on choice
            if ("ALL".equals(selectedStatus)) {
                result = new StringBuilder("All orders: \n\n");
            } else {
                result = new StringBuilder("Orders with status: " + selectedStatus + "\n\n");
            }
            boolean found = false;

            // loop through and show only the matching ones
            for (Order order : orders) {
                if ("ALL".equals(selectedStatus) || selectedStatus.equals(order.getStatus())) {
                    result.append(order.toString()).append("\n");
                    found = true;
                }
            }

            if (!found) {
                result.append("No orders found.");
            }

            JOptionPane.showMessageDialog(parentFrame, result.toString(), "Order List", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the specified action (DISPLAY, START, or COMPLETE) for the given order.
     *
     * @param parentFrame the parent JFrame for dialogs
     * @param order       the selected order
     * @param action      the action to perform (START, COMPLETE, or DISPLAY)
     */
    private void handleOrderAction(JFrame parentFrame, Order order, MenuAction action) {
        String info = driver.displayOrder(order.getOrderID());

        switch (action) {
            case DISPLAY:
                // show order details
                JOptionPane.showMessageDialog(parentFrame, info, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                break;

            case START:
                // ask for confirmation before starting
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

            case COMPLETE:
                // ask for confirmation before completing
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

    /**
     * Displays a file chooser dialog for selecting a JSON file to import orders.
     * Defaults to the Resources directory, but you may browse to find a JSON elsewhere in your computer.
     * @param parentFrame the parent JFrame for the dialog
     */
    private void showFileChooser(JFrame parentFrame) {
        // opens up a file picker for importing order jsons
        JFileChooser fileChooser = new JFileChooser("code/src/main/java/Resources");
        fileChooser.setDialogTitle("Select Order JSON File");
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                driver.addOrder(Parser.parseJSONOrder(selectedFile, 1));
                JOptionPane.showMessageDialog(parentFrame, "Order successfully parsed and placed into an Order.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Failed to parse and add the order.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
