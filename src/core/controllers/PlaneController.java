/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Plane;
import core.models.Storage.PlaneStorage;

/**
 *
 * @author juand
 */
public class PlaneController {
    public static Response createPlane(String planeId, String planeBrand, String planeModel, String planeMaxCapacity, String planeAirline) {
        try {
            int idInt, MaxCapInt;
            boolean genderB;

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
                MaxCapInt = Integer.parseInt(planeMaxCapacity);
                if (MaxCapInt < 0) {
                    return new Response("Max capacity must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Max capacity must be numeric", Status.BAD_REQUEST);
            }
            
            if (planeAirline.equals("")) {
                return new Response("Plane Airline must be not empty", Status.BAD_REQUEST);
            }

            PlaneStorage storage = PlaneStorage.getInstance();
            if (!storage.addPlane(new Plane(String.valueOf(idInt), planeBrand, planeModel, MaxCapInt, planeAirline))) {
                return new Response("A plane with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Plane created successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
