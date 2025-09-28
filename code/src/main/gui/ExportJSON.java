package main.gui;

import main.java.OrderDriver;

import javax.swing.*;

/**
 * The ExportJSON method will call on the OrderDriver class to export the JSON
 * then outputs a message if the export was successful or not
 */
public class ExportJSON {
    public static void exportOrders(JFrame parentFrame, OrderDriver orderDriver) {
        // format current time to CST for filename
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("America/Chicago")); // CST
        String formattedTime = sdf.format(new java.util.Date());
        String fileName = "Export_" + formattedTime + "CST.json";
        if (orderDriver.getOrders().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "No orders to export.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (OrderDriver.exportOrdersToJSON(fileName, orderDriver)) {
            JOptionPane.showMessageDialog(parentFrame, "Exported Orders to " + fileName + "\n");
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Failed to export orders.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
