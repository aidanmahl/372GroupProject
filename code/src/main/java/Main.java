package main.java;

import main.gui.SystemGUI;

// Main class to run the order management system. Launches a simple GUI for user interaction.
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            OrderDriver driver = new OrderDriver();
            new SystemGUI(driver);
        });
    }
}