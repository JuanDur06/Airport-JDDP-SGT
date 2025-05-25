/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Storage;

import core.models.Passenger;
import java.util.ArrayList;

/**
 *
 * @author juand
 */
public class PassengerStorage {

    private static PassengerStorage instance;

    private PassengerStorage() {
        this.passengers = new ArrayList<>();
    }

    private ArrayList<Passenger> passengers;

    public static PassengerStorage getInstance() {
        if (instance == null) {
            instance = new PassengerStorage();
        }
        return instance;
    }

    public ArrayList<Passenger> getAll() {
        return this.passengers;
    }

    public boolean addPassenger(Passenger passenger) {
        for (Passenger p : this.passengers) {
            if (p.getId() == passenger.getId()) {
                return false;
            }
        }
        this.passengers.add(passenger);
        return true;
    }

    public Passenger getPassenger(Long id) {
        for (Passenger p : this.passengers) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public boolean delPassenger(int id) {
        for (Passenger p : this.passengers) {
            if (p.getId() == id) {
                this.passengers.remove(p);
                return true;
            }
        }
        return false;
    }
}
