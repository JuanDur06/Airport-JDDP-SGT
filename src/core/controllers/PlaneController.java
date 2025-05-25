/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Plane;
import core.models.Storage.FlightStorage;
import core.models.Storage.PlaneStorage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author juand
 */
public class PlaneController {

    public static Response createPlane(String planeId, String planeBrand, String planeModel, String planeMaxCapacity, String planeAirline) {
        try {
            int idInt, intMaxCap;

            try {
                idInt = Integer.parseInt(planeId);
                if (idInt < 0) {
                    return new Response(" Airplane id must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Airplane id must be numeric", Status.BAD_REQUEST);
            }

            if (planeBrand.equals("")) {
                return new Response("Airplane brand must be not empty", Status.BAD_REQUEST);
            }

            if (planeModel.equals("")) {
                return new Response("Airplane Model must be not empty", Status.BAD_REQUEST);
            }

            try {
                intMaxCap = Integer.parseInt(planeMaxCapacity);
                if (intMaxCap < 0) {
                    return new Response("Max capacity must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Max capacity must be numeric", Status.BAD_REQUEST);
            }

            if (planeAirline.equals("")) {
                return new Response("Plane Airline must be not empty", Status.BAD_REQUEST);
            }

            PlaneStorage storage = PlaneStorage.getInstance();
            if (!storage.addPlane(new Plane(String.valueOf(idInt), planeBrand, planeModel, intMaxCap, planeAirline))) {
                return new Response("A plane with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Plane created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response refreshPlanesTable() {
        try {
            ArrayList<Plane> planes = PlaneStorage.getInstance().getAll();
            if (planes == null) {
                return new Response("Ther is no planes", Status.BAD_REQUEST);
            }
            String[][] aviones = new String[planes.size()][6];
            int i = 0;
            for (Plane plane : planes) {
                aviones[i][0] = plane.getId();
                aviones[i][1] = plane.getBrand();
                aviones[i][2] = plane.getModel();
                aviones[i][3] = String.valueOf(plane.getMaxCapacity());
                aviones[i][4] = plane.getAirline();
                aviones[i][5] = String.valueOf(plane.getNumFlights());

               
                i++;
            }
            Arrays.sort(aviones, (a, b) -> Long.compare(Long.parseLong(a[0]), Long.parseLong(b[0])));
            return new Response("Refresh succesfully", Status.CREATED, aviones);
        } catch (Exception e) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
