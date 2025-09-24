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
    private final OrderDriver driver;

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
    @SuppressWarnings("unused")
    private void createAndShowGUI() {
        // setting up the main window and buttons
        JFrame frame = new JFrame("FoodHub Order Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(575, 450);
        frame.setLayout(new GridLayout(6, 1, 10, 10));

        // Create buttons for each action
        JButton startOrderBtn = new JButton("Start Incoming Order");
        JButton completeOrderBtn = new JButton("Complete In-Progress Order");
        JButton displayOrderBtn = new JButton("Display Order(s)");
        JButton addOrderBtn = new JButton("Import Order JSON");
        JButton exportBtn = new JButton("Export All Orders to JSON");
        JButton exitBtn = new JButton("Exit");

        // fancy button colors
        displayOrderBtn.setBackground(new Color(200, 200, 200)); // Display - gray
        displayOrderBtn.setForeground(Color.BLACK);
        startOrderBtn.setBackground(new Color(76, 175, 80)); // Start - light green
        startOrderBtn.setForeground(Color.WHITE);
        completeOrderBtn.setBackground(new Color(27, 94, 32)); // Complete - dark green
        completeOrderBtn.setForeground(Color.WHITE);
        addOrderBtn.setBackground(new Color(33, 150, 243)); // Import - blue
        addOrderBtn.setForeground(Color.WHITE);
        exportBtn.setBackground(new Color(13, 71, 161)); //Export - dark blue
        exportBtn.setForeground(Color.WHITE);
        exitBtn.setBackground(new Color(211, 47, 47)); // Exit - red
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setPreferredSize(new Dimension(80, 30));

        // Add action listeners to buttons
        startOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.START));
        completeOrderBtn.addActionListener(e -> showOrderSelectionMenu(frame, MenuAction.COMPLETE));
        displayOrderBtn.addActionListener(e -> showDisplayChoiceMenu(frame));
        addOrderBtn.addActionListener(e -> showFileChooser(frame));
        exitBtn.addActionListener(e -> frame.dispose());
        exportBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Export not yet implemented.", "Export", JOptionPane.INFORMATION_MESSAGE)
        );

        // Add buttons to the frame
        frame.add(displayOrderBtn);
        frame.add(startOrderBtn);
        frame.add(completeOrderBtn);
        frame.add(addOrderBtn);
        frame.add(exportBtn);
        // put exit button in its own panel so we can make it smaller
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        exitPanel.add(exitBtn);
        frame.add(exitPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Enum representing the different menu actions that can be performed on an order for use with the dropdown menu.
     */
    private enum MenuAction {
        DISPLAY, START, COMPLETE
    }

    /**
     * Appears when Start Order or Complete Order button is clicked, or if Individual Order is selected from the Display Order(s) menu.
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
        // showing order id and status so it's easier to pick an order based on user intent
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
     * Appears when Display Order(s) button is clicked.
     * Prompts user to choose between displaying an individual order or a group of orders by status.
     * Sows 5 buttons: Individual Order, Incoming Orders, In-Progress Orders, Completed Orders, or All Orders.
     * @param parentFrame the parent JFrame for the dialog
     */
    @SuppressWarnings("unused")
    private void showDisplayChoiceMenu(JFrame parentFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel chooseLabel = new JLabel("Choose display option");
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(chooseLabel);

        JButton individualBtn = new JButton("Individual Order");
        JButton uncompletedBtn = new JButton("Uncompleted Orders");
        JButton completedBtn = new JButton("Completed Orders");
        JButton allBtn = new JButton("All Orders");

        panel.add(individualBtn);
        panel.add(uncompletedBtn);
        panel.add(completedBtn);
        panel.add(allBtn);

        JDialog dialog = new JDialog(parentFrame, "Display Order(s)", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        individualBtn.addActionListener(e -> {
            dialog.dispose();
            showOrderSelectionMenu(parentFrame, MenuAction.DISPLAY); });
        uncompletedBtn.addActionListener(e -> {
            dialog.dispose();
            displayOrdersByStatus(parentFrame, "UNCOMPLETED"); });
        completedBtn.addActionListener(e -> {
            dialog.dispose();
            displayOrdersByStatus(parentFrame, "COMPLETED"); });
        allBtn.addActionListener(e -> {
            dialog.dispose();
            displayOrdersByStatus(parentFrame, "ALL"); });

        dialog.setVisible(true);
    }

    /**
     * Displays orders filtered by the given status, or all orders if status is "ALL".
     * Supports "UNCOMPLETED" to show both "INCOMING" and "IN PROGRESS".
     * @param parentFrame the parent JFrame for the dialog
     * @param status the status to filter by ("ALL", "UNCOMPLETED", "COMPLETED")
     */
    private void displayOrdersByStatus(JFrame parentFrame, String status) {
        java.util.List<Order> orders = driver.getOrders();
        orders = new ArrayList<>(orders);
        orders.sort((o1, o2) -> Long.compare(o1.getDate(), o2.getDate()));

        StringBuilder result;
        double totalUncompleted = 0.0;
        if ("ALL".equals(status)) {
            result = new StringBuilder("All orders \n");
        } else if ("UNCOMPLETED".equals(status)) {
            result = new StringBuilder("Uncompleted orders \n");
        } else {
            result = new StringBuilder("Completed orders \n");
        }
        result.append("Sorted by time (oldest to newest):\n");

        boolean found = false;
        for (Order order : orders) {
            // check if the order matches the requested status
            boolean isUncompleted = "INCOMING".equals(order.getStatus()) || "IN PROGRESS".equals(order.getStatus());
            if ("ALL".equals(status)) {
                result.append(order.toString()).append("\n");
                found = true;
            } else if ("UNCOMPLETED".equals(status) && isUncompleted) { // both incoming and in progress
                totalUncompleted += order.getTotalPrice(); // sum up total price of uncompleted orders
                result.append(order.toString()).append("\n");
                found = true;
            } else if ("COMPLETED".equals(status) && "COMPLETED".equals(order.getStatus())) {
                result.append(order.toString()).append("\n");
                found = true;
            }
        }
        if (!found) {
            result.append("No orders found.");
        }

        JTextArea textArea = new JTextArea(result.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // if showing uncompleted orders, also show total price at the top
        if ("UNCOMPLETED".equals(status)) {
            JPanel panel = new JPanel(new BorderLayout());
            JLabel totalLabel = new JLabel(String.format("Total price of uncompleted orders: $%.2f", totalUncompleted));
            totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(totalLabel, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);
            JOptionPane.showMessageDialog(parentFrame, panel, "Order List", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(parentFrame, scrollPane, "Order List", JOptionPane.INFORMATION_MESSAGE);
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
     * Appears when the Import Order JSON button is clicked.
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
                Order importedOrder = Parser.parseJSONOrder(selectedFile);
                driver.addOrder(importedOrder);
                JOptionPane.showMessageDialog(parentFrame, "JSON imported into Order #" + importedOrder.getOrderID());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Failed to parse and add the order.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}