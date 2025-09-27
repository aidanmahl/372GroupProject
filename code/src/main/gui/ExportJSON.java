package main.gui;

import main.java.OrderDriver;

import javax.swing.*;

public class ExportJSON {
    public static void export(JFrame parentFrame, OrderDriver orderDriver) {
        boolean exportSuccess = OrderDriver.exportOrdersToJSON("Orders_" + System.currentTimeMillis() + ".json",  orderDriver);

        if (exportSuccess) {
            JOptionPane.showMessageDialog(parentFrame, "Exported Orders to JSON");
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Failed to Export Orders to JSON");
        }
    }
}
