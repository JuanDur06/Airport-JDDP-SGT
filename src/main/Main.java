/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.models.dataLoader.DataLoader;
import core.views.AirportFrame;
import javax.swing.UIManager;

/**
 *
 * @author juand
 */
public class Main {

    public static void main(String args[]) {
        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        try {
            DataLoader.loadData();
        } catch (Exception e) {
            System.err.println("Error al cargar datos:");
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AirportFrame().setVisible(true);
        });
    }
}
