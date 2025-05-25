/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.Storage;

import core.models.Plane;
import java.util.ArrayList;

/**
 *
 * @author juand
 */
public class PlaneStorage {
    private static PlaneStorage instance;

    private PlaneStorage() {
        this.planes = new ArrayList<>();
    }

    private ArrayList<Plane> planes;

    public static PlaneStorage getInstance() {
        if (instance == null) {
            instance = new PlaneStorage();
        }
        return instance;
    }

    public boolean addPlane(Plane plane) {
        for (Plane p : this.planes) {
            if (p.getId().equals(plane.getId())) {
                return false;
            }
        }
        this.planes.add(plane);
        return true;
    }
    
    public Plane getPlane(String id) {
        for (Plane plane : this.planes) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return null;
    }
    
    public boolean delPlane(String id) {
        for (Plane person : this.planes) {
            if (person.getId().equals(id)) {
                this.planes.remove(person);
                return true;
            }
        }
        return false;
    }
}
