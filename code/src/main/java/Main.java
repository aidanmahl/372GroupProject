package main.java;

import main.gui.SystemGUI;

/**
 * Launches the program and opens the GUI for user interaction.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            OrderDriver driver = new OrderDriver();
            new SystemGUI(driver);
        });
    }
}