package main.java;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The SystemGUI class is responsible for creating and managing the graphical user interface (GUI)
 * for the order management system. It provides buttons for various actions such as starting,
 * completing, and displaying orders, as well as importing and exporting JSONs.
 * There are 5 main buttons on the GUI, each with their own action listeners to handle user interactions.
 * Start Order, Complete Order, and Display Order buttons will open a dropdown menu to select an order from the list of orders.
 * Choosing an order from this dropdown will then display the order information and ask for confirmation to start or complete the order if applicable.
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
        JFrame frame = new JFrame("ICS 372 Group Project - Order System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(8, 1, 10, 10));

        // Create buttons for each action
        JButton startOrderBtn = new JButton("Start Incoming Order");
        JButton completeOrderBtn = new JButton("Complete Order");
        JButton displayOrderBtn = new JButton("Display Order");
        JButton addOrderBtn = new JButton("Add New Order JSON");
        JButton exportBtn = new JButton("Export All Orders to JSON");
        JButton exitBtn = new JButton("Exit");

        // Add action listeners to buttons
        startOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.START));
        completeOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.COMPLETE));
        displayOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.DISPLAY));
        //TODO exportBtn.addActionListener(e -> ()); //need to ask professor a question about this one
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
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No orders available.");
            return;
        }

        String[] orderIDs = new String[orders.size()];
        // Display order ID and status in dropdown
        for (int i = 0; i < orders.size(); i++) {
            orderIDs[i] = "Order #" + orders.get(i).getOrderID() + " - Status: " + orders.get(i).getStatus();
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

        //send selected order to appropriate action below
        if (selected != null) {
            int selectedIndex = java.util.Arrays.asList(orderIDs).indexOf(selected);
            Order selectedOrder = orders.get(selectedIndex);
            handleOrderAction(parentFrame, selectedOrder, action);
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
        //display selected order info. if start or complete is the action, there will be a confirmation message. Otherwise, if display, just show the info.
        String info = driver.displayOrder(order.getOrderID());

        switch (action) {
            case DISPLAY:
                JOptionPane.showMessageDialog(parentFrame, info, "Order Details", JOptionPane.INFORMATION_MESSAGE);
                break;

            case START:
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
