/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Storage;

import core.models.Flight;
import java.util.ArrayList;

/**
 *
 * @author juand
 */
public class FlightStorage {
    private static FlightStorage instance;
    private ArrayList<Flight> flights;

    private FlightStorage() {
        this.flights = new ArrayList<>();
    }

    public static FlightStorage getInstance() {
        if (instance == null) {
            instance = new FlightStorage();
        }
        return instance;
    }

    public Flight getFlight(String id) {
        for (Flight f : this.flights) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public ArrayList<Flight> getAllFlights() {
        return flights;
    }
}

