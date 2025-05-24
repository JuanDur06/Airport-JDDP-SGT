/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Storage;

import core.models.Location;
import java.util.ArrayList;

/**
 *
 * @author juand
 */
public class LocationStorage {

    private static LocationStorage instance;

    private LocationStorage() {
        this.locations = new ArrayList<>();
    }

    private ArrayList<Location> locations;

    public static LocationStorage getInstance() {
        if (instance == null) {
            instance = new LocationStorage();
        }
        return instance;
    }

    public boolean addLocation(Location location) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }
        this.locations.add(location);
        return true;
    }
    
    public Location getLocation(int id) {
        for (Location l : this.locations) {
            if (l.getAirportId().equals(id)) {
                return l;
            }
        }
        return null;
    }
    
    public boolean delLocation(int id) {
        for (Location l: this.locations) {
            if (l.getAirportId().equals( id)) {
                this.locations.remove(l);
                return true;
            }
        }
        return false;
    }
}
