package main.gui;

import main.java.OrderDriver;
import main.java.Parser;

import javax.swing.*;

/**
 * The ExportJSON method will call on the Parser class to export the JSON
 * then outputs a message if the export was successful or not
 */
public class ExportJSON {
    public static void exportOrders(JFrame parentFrame, OrderDriver orderDriver) {
        String fileName = "Orders_" + System.currentTimeMillis() + ".json";
        boolean exportSuccess = Parser.exportOrdersToJSON(fileName,  orderDriver);

        if (exportSuccess) {
            JOptionPane.showMessageDialog(parentFrame, "Exported Orders to JSON");
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Failed to Export Orders to JSON");
        }
    }
}
