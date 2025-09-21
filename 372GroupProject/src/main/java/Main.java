import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// Main class to run the order management system. Launches a simple GUI for user interaction.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderDriver driver = new OrderDriver();

            JFrame frame = new JFrame("ICS 372 Group Project - Order System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new GridLayout(8, 1, 10, 10));

            JButton startOrderBtn = new JButton("Start Incoming Order");
            JButton displayOrderBtn = new JButton("Display Incoming Order");
            JButton completeOrderBtn = new JButton("Complete Incoming Order");
            JButton displayAllBtn = new JButton("Display All Orders");
            JButton addOrderBtn = new JButton("Add New Order JSON");
            JButton completeAllBtn = new JButton("Complete All Orders");
            JButton exitBtn = new JButton("Exit");

            startOrderBtn.addActionListener(e -> driver.startIncomingOrder());
            displayOrderBtn.addActionListener(e -> driver.displayIncomingOrder());
            completeOrderBtn.addActionListener(e -> driver.completeIncomingOrder());
            displayAllBtn.addActionListener(e -> driver.displayAll());
            addOrderBtn.addActionListener(e -> driver.addOrder());
            completeAllBtn.addActionListener(e -> driver.completeAllOrders());
            exitBtn.addActionListener(e -> frame.dispose());

            frame.add(new JLabel("Select an option:", SwingConstants.CENTER));
            frame.add(startOrderBtn);
            frame.add(displayOrderBtn);
            frame.add(completeOrderBtn);
            frame.add(displayAllBtn);
            frame.add(addOrderBtn);
            frame.add(completeAllBtn);
            frame.add(exitBtn);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}