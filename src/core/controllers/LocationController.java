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
            double lat, lon;

            // Validación del ID: exactamente 3 letras mayúsculas
            if (id.trim().equals("") || id.length() != 3) {
                return new Response("Airport ID must be exactly 3 characters long", Status.BAD_REQUEST);
            }

            for (int i = 0; i < id.length(); i++) {
                char c = id.charAt(i);
                if (!Character.isLetter(c) || !Character.isUpperCase(c)) {
                    return new Response("Each character in ID must be an uppercase letter (A-Z)", Status.BAD_REQUEST);
                }
            }

            // Validaciones de campos no vacíos
            if (name.trim().equals("")) {
                return new Response("Airport name must not be empty", Status.BAD_REQUEST);
            }

            if (city.trim().equals("")) {
                return new Response("Airport city must not be empty", Status.BAD_REQUEST);
            }

            if (country.trim().equals("")) {
                return new Response("Airport country must not be empty", Status.BAD_REQUEST);
            }

            // Validación de latitud
            try {
                lat = Double.parseDouble(latitude);
                if (lat < -90 || lat > 90) {
                    return new Response("Latitude must be between -90 and 90 degrees", Status.BAD_REQUEST);
                }
                if (Math.round(lat * 10000) != lat * 10000) {
                    return new Response("Latitude must have at most 4 decimal places", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Latitude must be a valid number", Status.BAD_REQUEST);
            }

            try {
                lon = Double.parseDouble(longitude);
                if (lon < -180 || lon > 180) {
                    return new Response("Longitude must be between -180 and 180 degrees", Status.BAD_REQUEST);
                }
                if (Math.round(lon * 10000) != lon * 10000) {
                    return new Response("Longitude must have at most 4 decimal places", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Longitude must be a valid number", Status.BAD_REQUEST);
            }

            // Comprobar si ya existe una localización con ese ID
            LocationStorage storage = LocationStorage.getInstance();
            if (storage.getLocation(id) != null){
                return new Response("A location with this ID already exists", Status.BAD_REQUEST);
            }

            // Crear y agregar la nueva localización
            Location newLocation = new Location(id, name.trim(), city.trim(), country.trim(), lat, lon);
            if (!storage.addLocation(newLocation)) {
                return new Response("Could not add the location", Status.BAD_REQUEST);
            }

            return new Response("Location created successfully", Status.CREATED);

        } catch (Exception ex) {
            return new Response("Unexpected error when creating the location", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
