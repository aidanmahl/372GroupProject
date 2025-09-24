package main.gui;

import main.java.Order;
import main.java.OrderDriver;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Buttons that appear when the user selects "Display Order(s)" from the main menu.
 * Presents options for individual, uncompleted, completed, or all orders,
 * and displays the selected orders.
 */
public class DisplayOrdersDialog {
    /**
     * Constructs and shows the display choice dialog, then displays orders based on user selection.
     * @param parentFrame the parent JFrame
     * @param driver the OrderDriver containing orders
     */
    @SuppressWarnings("unused")
    public DisplayOrdersDialog(JFrame parentFrame, OrderDriver driver) {
        //create panel with grid layout
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        JLabel chooseLabel = new JLabel("Choose display option:");
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(chooseLabel);

        JButton individualBtn = new JButton("Individual Order");
        JButton uncompletedBtn = new JButton("Uncompleted Orders");
        JButton completedBtn = new JButton("Completed Orders");
        JButton allBtn = new JButton("All Orders");

        // add buttons to panel
        panel.add(individualBtn);
        panel.add(uncompletedBtn);
        panel.add(completedBtn);
        panel.add(allBtn);

        JDialog dialog = new JDialog(parentFrame, "Display Order(s)", true);
        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parentFrame);

        individualBtn.addActionListener(e -> { //individual order display. open OrderSelectionDropdown
            dialog.dispose();
            new OrderSelectionDropdown(parentFrame, driver, OrderSelectionDropdown.Action.DISPLAY);
        });
        uncompletedBtn.addActionListener(e -> { //Uncompleted orders display. open list of incoming and in-progress orders
            dialog.dispose();
            showOrders(parentFrame, driver, "UNCOMPLETED");
        });
        completedBtn.addActionListener(e -> { //open list of completed orders
            dialog.dispose();
            showOrders(parentFrame, driver, "COMPLETED");
        });
        allBtn.addActionListener(e -> { //display all orders
            dialog.dispose();
            showOrders(parentFrame, driver, "ALL");
        });

        dialog.setVisible(true);
    }

    /**
     * Displays a list of orders filtered by status.
     * Shows orders sorted by time and, for uncompleted orders, the total price.
     * @param parentFrame the parent JFrame
     * @param driver the OrderDriver containing orders
     * @param status the status filter ("ALL", "UNCOMPLETED", "COMPLETED")
     */
    private void showOrders(JFrame parentFrame, OrderDriver driver, String status) {
        java.util.List<Order> orders = new ArrayList<>(driver.getOrders());
        orders.sort(Comparator.comparingLong(Order::getDate)); //sort from oldest to newest in display window

        StringBuilder result; //place output here
        double totalUncompleted = 0.0;

        // header based on status
        if ("ALL".equals(status)) result = new StringBuilder("All orders \n");
        else if ("UNCOMPLETED".equals(status)) result = new StringBuilder("Uncompleted orders \n");
        else result = new StringBuilder("Completed orders \n");

        result.append("Sorted by time (oldest to newest):\n");

        boolean found = false;
        for (Order order : orders) { //iterate through orders, filter by status
            boolean isUncompleted = "INCOMING".equals(order.getStatus()) || "IN PROGRESS".equals(order.getStatus()); //check if order is uncompleted by status of INCOMING or IN PROGRESS

            //ALL selected:
            if ("ALL".equals(status)) {
                result.append(order).append("\n");
                found = true;

                //UNCOMPLETED selected:
            } else if ("UNCOMPLETED".equals(status) && isUncompleted) { //add only if uncompleted
                totalUncompleted += order.getTotalPrice();
                result.append(order).append("\n");
                found = true;

                //COMPLETED selected:
            } else if ("COMPLETED".equals(status) && "COMPLETED".equals(order.getStatus())) { //add only if completed
                result.append(order).append("\n");
                found = true;
            }
        }
        if (!found) result.append("No orders found.");

        JTextArea textArea = new JTextArea(result.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea); //add scroll pane to text area of fixed dimensions (too many orders was making window huge)
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // add total price label for uncompleted orders at the top of display if UNCOMPLETED selected
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
}