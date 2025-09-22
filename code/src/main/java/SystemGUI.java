import javax.swing.*;
import java.awt.*;
import java.io.File;

//Handler for the GUI components and user interactions. Creates buttons for each action in the OrderDriver.
public class SystemGUI {
    private OrderDriver driver;

    public SystemGUI(OrderDriver driver) {
        this.driver = driver;
        createAndShowGUI();
    }
    //create a GUI using java's abstract window tookit (AWT) and swing libraries
    private void createAndShowGUI() {
        JFrame frame = new JFrame("ICS 372 Group Project - Order System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(8, 1, 10, 10)); 

        //create buttons for each action
        JButton startOrderBtn = new JButton("Start Incoming Order");
        JButton displayOrderBtn = new JButton("Display Incoming Order");
        JButton completeOrderBtn = new JButton("Complete Incoming Order");
        JButton displayAllBtn = new JButton("Display All Orders");
        JButton addOrderBtn = new JButton("Add New Order JSON");
        JButton completeAllBtn = new JButton("Complete All Orders");
        JButton exitBtn = new JButton("Exit");

        //add action listeners to each button to call the appropriate method in OrderDriver
        
        startOrderBtn.addActionListener(e -> driver.startIncomingOrder());

        //displays the information of an order. unimplemented for now
        displayOrderBtn.addActionListener(e -> {
            // display the first order if available, otherwise show a message
            java.util.List<Order> orders = driver.getOrders();
            if (orders.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No orders available.");
            } else {
                int firstOrderId = orders.get(0).getOrderID();
                String info = driver.displayOrder(firstOrderId);
                JOptionPane.showMessageDialog(frame, info, "Order Details", JOptionPane.INFORMATION_MESSAGE); //information comes from displayOrder in OrderDriver
            }
        });

        completeOrderBtn.addActionListener(e -> driver.completeIncomingOrder());

        // displayall button shows a list of all orders and allows selection to view details of said order
        displayAllBtn.addActionListener(e -> showOrdersMenu(frame));

        // add order button opens a file chooser to select a JSON file and adds it to the orders list in OrderDriver
        addOrderBtn.addActionListener(e -> showFileChooser(frame));

        completeAllBtn.addActionListener(e -> driver.completeAllOrders());

        exitBtn.addActionListener(e -> frame.dispose());

        //add buttons to the frame
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
    }
    
    //DONT KNOW IF THIS WORKS YET. Needs displayOrder to be implemented in OrderDriver to test

    // helpers method to show a file chooser dialog and read the selected JSON file. defaults to the /Resources directory in this project
    // file explorer uses JFileChooser from javax.swing- https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
    private void showFileChooser(JFrame parentFrame) {
    JFileChooser fileChooser = new JFileChooser("372GroupProject/code/src/main/java/Resources");
        fileChooser.setDialogTitle("Select Order JSON File");
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // parse json
                boolean success = driver.addOrder(selectedFile.getAbsolutePath());
                if (success) {
                    JOptionPane.showMessageDialog(parentFrame, "Order successfully parsed and placed into an Order.");
                } else {
                    JOptionPane.showMessageDialog(parentFrame, "Failed to parse and add the order.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parentFrame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //again, not sure if this works yet. needs displayOrder in OrderDriver to be implemented to test

    // helper method to show a dialog with a list of orders and allow selection to view details
    // uses JOptionPane from javax.swing - https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html
    private void showOrdersMenu(JFrame parentFrame) {
        java.util.List<Order> orders = driver.getOrders(); // Add a getOrders() method to OrderDriver if not present
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No orders available.");
            return;
        }

        String[] orderIDs = new String[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            orderIDs[i] = "Order #" + orders.get(i).getOrderID();
        }

        String selected = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Select an order to view:",
                "Order List",
                JOptionPane.PLAIN_MESSAGE,
                null,
                orderIDs,
                orderIDs[0]
        );

        if (selected != null) {
            int selectedIndex = java.util.Arrays.asList(orderIDs).indexOf(selected);
            Order selectedOrder = orders.get(selectedIndex);
            //display chosen order info
            String info = driver.displayOrder(selectedOrder.getOrderID());
            JOptionPane.showMessageDialog(parentFrame, info, "Order Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}