package main.gui;

import main.java.OrderDriver;
import main.java.Parser;

import javax.swing.*;

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
