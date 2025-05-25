/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.Storage.FlightStorage;
import core.models.Storage.LocationStorage;
import core.models.Storage.PlaneStorage;
import java.time.LocalDateTime;

/**
 *
 * @author juand
 */
public class FlightController {

    public static Response createFlight(String id, String planeId, String departureId, String arrivalId, String scaleId, String yearStr, String monthStr, String dayStr, String hourStr, String minuteStr, String durHoursStr, String durMinutesStr, String scaleHoursStr, String scaleMinutesStr ) {
        // Aqui validamos el ID
        if (id.length() != 6) {
            return new Response("The ID must have 6 exactly characters.", Status.BAD_REQUEST);
        }

        String letters = id.substring(0, 3);
        String digits = id.substring(3, 6);
        for (char c : letters.toCharArray()) {
            if (!Character.isUpperCase(c)) {
                return new Response("The first 3 characters of the ID must be highcase letters.", Status.BAD_REQUEST);
            }
        }
        for (char c : digits.toCharArray()) {
            if (!Character.isDigit(c)) {
                return new Response("The last 3 characters of the ID must be numbers.", Status.BAD_REQUEST);
            }
        }

        // Aqui verificamos si el vuelo ya esta creado
        if (FlightStorage.getInstance().getFlight(id) != null) {
            return new Response("Ya existe un vuelo con este ID.", Status.BAD_REQUEST);
        }

        // Aqui buscamos si el avion existe
        Plane plane = PlaneStorage.getInstance().getPlane(planeId);
        if (plane == null) {
            return new Response("El avión especificado no existe.", Status.BAD_REQUEST);
        }

        // Aqui buscamos si la localizacion existe, tanto de llegada como de salida
        Location departure = LocationStorage.getInstance().getLocation(departureId);
        if (departure == null) {
            return new Response("La localización de salida no es válida.", Status.BAD_REQUEST);
        }
        Location arrival = LocationStorage.getInstance().getLocation(arrivalId);
        if (arrival == null) {
            return new Response("La localización de llegada no es válida.", Status.BAD_REQUEST);
        }

        Location scale = LocationStorage.getInstance().getLocation(scaleId); // Puede ser null si no hay escala

        // Validar fecha de salida
        LocalDateTime departureDate;
        try {
            int year = Integer.parseInt(yearStr);
            int month = Integer.parseInt(monthStr);
            int day = Integer.parseInt(dayStr);
            int hour = Integer.parseInt(hourStr);
            int minute = Integer.parseInt(minuteStr);
            departureDate = LocalDateTime.of(year, month, day, hour, minute);
        } catch (Exception e) {
            return new Response("Fecha u hora de salida inválidas.", Status.BAD_REQUEST);
        }

        // Validar duración del vuelo
        int durHours = Integer.parseInt(durHoursStr);
        int durMinutes = Integer.parseInt(durMinutesStr);
        if (durHours < 0 || durMinutes < 0) {
            return new Response("La duración del vuelo no puede tener valores negativos.", Status.BAD_REQUEST);
        }
        if (durHours == 0 && durMinutes == 0) {
            return new Response("La duración del vuelo debe ser mayor a 00:00.", Status.BAD_REQUEST);
        }

        // Validar duración escala
        int scaleHours = 0, scaleMinutes = 0;
        if (scale != null) {
            scaleHours = Integer.parseInt(scaleHoursStr);
            scaleMinutes = Integer.parseInt(scaleMinutesStr);
            if (scaleHours < 0 || scaleMinutes < 0) {
                return new Response("La duración de la escala no puede tener valores negativos.", Status.BAD_REQUEST);
            }
        }

        // Crear y registrar vuelo
        Flight flight;
        if (scale == null) {
            flight = new Flight(id, plane, departure, arrival, departureDate, durHours, durMinutes);
        } else {
            flight = new Flight(id, plane, departure, scale, arrival, departureDate, durHours, durMinutes, scaleHours, scaleMinutes);
        }

        FlightStorage.getInstance().addFlight(flight);
        return new Response("Flight created successfully.", Status.OK);
    }
}
