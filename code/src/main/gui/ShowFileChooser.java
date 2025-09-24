package main.gui;

import main.java.Order;
import main.java.OrderDriver;
import main.java.Parser;

import javax.swing.*;
import java.io.File;

/**
 * Utility class for showing a file explorer.
 * Followed Oracle's example code for JFileChooser.
 * <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html">...</a>
 */
public class ShowFileChooser {
    /**
     * Shows a file chooser dialog for importing an order JSON file.
     * @param parentFrame the parent JFrame for the dialog
     * @return the selected File, or null if cancelled
     */
    public static File showFileChooser(JFrame parentFrame) {
        JFileChooser fileChooser = new JFileChooser("code/src/main/java/Resources");
        fileChooser.setDialogTitle("Select Order JSON File");
        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    /**
     * Handles importing an order from JSON and displaying the result.
     * @param parentFrame the parent JFrame
     * @param driver the OrderDriver to add the order to
     */
    public static void importOrder(JFrame parentFrame, OrderDriver driver) {
        File selectedFile = showFileChooser(parentFrame);
        if (selectedFile != null) {
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
