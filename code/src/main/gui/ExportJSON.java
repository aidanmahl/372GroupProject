package main.gui;

import main.java.OrderDriver;

import javax.swing.*;

/**
 * The ExportJSON method will call on the OrderDriver class to export the JSON
 * then outputs a message if the export was successful or not
 */
public class ExportJSON {
    public static void exportOrders(JFrame parentFrame, OrderDriver orderDriver) {
        String fileName = OrderDriver.exportOrdersToJSON(orderDriver);

        JOptionPane.showMessageDialog(parentFrame, "Exported Orders to " + fileName + "\n");
        }
    }

