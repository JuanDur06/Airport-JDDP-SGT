/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.formdev.flatlaf.FlatDarkLaf;
import core.views.AirportFrame;
import javax.swing.UIManager;
import core.models.dataLoader.*;
import java.util.List;

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
            DataLoaderManager manager = new DataLoaderManager(List.of(
                    new LocationDataLoader(),
                    new PlaneDataLoader(),
                    new PassengerDataLoader(),
                    new FlightDataLoader() // debe ir de último porque depende de los anteriores
            ));

            manager.loadAll();
        } catch (Exception e) {
            System.err.println("Error al cargar datos:");
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AirportFrame().setVisible(true);
        });
    }
}
