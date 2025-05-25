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

    private FlightStorage() {
        this.flights = new ArrayList<>();
    }

    private ArrayList<Flight> flights;

    public static FlightStorage getInstance() {
        if (instance == null) {
            instance = new FlightStorage();
        }
        return instance;
    }

    public boolean addFlight(Flight flight) {
        for (Flight f : this.flights) {
            if (f.getId().equals(flight.getId())) {
                return false;
            }
        }
        this.flights.add(flight);
        return true;
    }

    public Flight getFlight(String id) {
        for (Flight f : this.flights) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public boolean delFlight(String id) {
        for (Flight f : this.flights) {
            if (f.getId().equals(id)) {
                this.flights.remove(f);
                return true;
            }
        }
        return false;
    }
}
