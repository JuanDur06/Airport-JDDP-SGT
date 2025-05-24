/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.Storage.LocationStorage;

/**
 *
 * @author juand
 */
public class LocationController {

    public static Response createLocation(String id, String name, String city, String country, String latitude, String longitude) {
        try {
            int idInt;
            double lat, lon;

            // Validación de ID
            try {
                idInt = Integer.parseInt(id);
                if (idInt < 0) {
                    return new Response("ID must be positive", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("ID must be numeric", Status.BAD_REQUEST);
            }

            // Validaciones de campos no vacíos
            if (name.equals("")) {
                return new Response("Location name must not be empty", Status.BAD_REQUEST);
            }

            if (city.equals("")) {
                return new Response("City name must not be empty", Status.BAD_REQUEST);
            }

            if (country.equals("")) {
                return new Response("Country name must not be empty", Status.BAD_REQUEST);
            }

            // Validación de latitud
            try {
                lat = Double.parseDouble(latitude);
                if (lat < -90 || lat > 90) {
                    return new Response("Latitude must be between -90 and 90 degrees.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Latitude must be a valid number", Status.BAD_REQUEST);
            }

            // Validación de longitud
            try {
                lon = Double.parseDouble(longitude);
                if (lon < -180 || lon > 180) {
                    return new Response("Length should be between -180 and 180 degrees.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Lenght must be a valid number", Status.BAD_REQUEST);
            }

            // Intentar agregar la ubicación al storage
            LocationStorage storage = LocationStorage.getInstance();
                       
            if (!storage.addLocation(new Location(String.valueOf(idInt), name, city, country, lat, lon))) {
                return new Response("A location with this ID already exists", Status.BAD_REQUEST);
            }

            return new Response("Location created succesfully", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error when creating the location", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
