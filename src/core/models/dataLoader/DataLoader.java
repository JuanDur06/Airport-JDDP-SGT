/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.dataLoader;

/**
 *
 * @author juand
 */
import core.models.*;
import core.models.Storage.FlightStorage;
import core.models.Storage.LocationStorage;
import core.models.Storage.PassengerStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class DataLoader {

    public static void loadData() {
        FlightStorage flightStorage = FlightStorage.getInstance();
        LocationStorage locationStorage = LocationStorage.getInstance();
        PassengerStorage passengerStorage = PassengerStorage.getInstance();

        HashMap<String, Location> locationMap = new HashMap<>();
        HashMap<String, Plane> planeMap = new HashMap<>();

        // --- CARGAR LOCATIONS ---
        InputStream locationStream = DataLoader.class.getResourceAsStream("/json/locations.json");
        JSONArray locationArray = new JSONArray(new JSONTokener(locationStream));

        for (int i = 0; i < locationArray.length(); i++) {
            JSONObject obj = locationArray.getJSONObject(i);
            String id = obj.getString("airportId");
            String name = obj.getString("airportName");
            String city = obj.getString("airportCity");
            String country = obj.getString("airportCountry");
            double lat = obj.getDouble("airportLatitude");
            double lon = obj.getDouble("airportLongitude");

            Location location = new Location(id, name, city, country, lat, lon);
            locationMap.put(id, location);
            locationStorage.addLocation(location);
        }

        // --- CARGAR PLANES ---
        InputStream planeStream = DataLoader.class.getResourceAsStream("/json/planes.json");
        JSONArray planeArray = new JSONArray(new JSONTokener(planeStream));

        for (int i = 0; i < planeArray.length(); i++) {
            JSONObject obj = planeArray.getJSONObject(i);
            String id = obj.getString("id");
            String brand = obj.getString("brand");
            String model = obj.getString("model");
            int maxCapacity = obj.getInt("maxCapacity");
            String airline = obj.getString("airline");

            Plane plane = new Plane(id, brand, model, maxCapacity, airline);
            planeMap.put(id, plane);
        }

        // --- CARGAR FLIGHTS ---
        InputStream flightStream = DataLoader.class.getResourceAsStream("/json/flights.json");
        JSONArray flightArray = new JSONArray(new JSONTokener(flightStream));

        for (int i = 0; i < flightArray.length(); i++) {
            JSONObject obj = flightArray.getJSONObject(i);
            String id = obj.getString("id");
            String planeId = obj.getString("plane");
            Plane plane = planeMap.get(planeId); // Ya se cargó previamente

            Location departure = locationMap.get(obj.getString("departureLocation"));
            Location arrival = locationMap.get(obj.getString("arrivalLocation"));
            String scaleId = obj.isNull("scaleLocation") ? null : obj.getString("scaleLocation");
            Location scale = scaleId == null ? null : locationMap.get(scaleId);

            LocalDateTime departureDate = LocalDateTime.parse(obj.getString("departureDate"));
            int hArr = obj.getInt("hoursDurationArrival");
            int mArr = obj.getInt("minutesDurationArrival");
            int hScale = obj.getInt("hoursDurationScale");
            int mScale = obj.getInt("minutesDurationScale");

            Flight flight;
            if (scale == null) {
                flight = new Flight(id, plane, departure, arrival, departureDate, hArr, mArr);
            } else {
                flight = new Flight(id, plane, departure, scale, arrival, departureDate, hArr, mArr, hScale, mScale);
            }

            flightStorage.addFlight(flight);
        }

        // --- CARGAR PASSENGERS ---
        InputStream passengerStream = DataLoader.class.getResourceAsStream("/json/passengers.json");
        JSONArray passengerArray = new JSONArray(new JSONTokener(passengerStream));

        for (int i = 0; i < passengerArray.length(); i++) {
            JSONObject obj = passengerArray.getJSONObject(i);

            long id = obj.getLong("id");
            String firstname = obj.getString("firstname");
            String lastname = obj.getString("lastname");
            LocalDate birthDate = LocalDate.parse(obj.getString("birthDate"));
            int countryPhoneCode = obj.getInt("countryPhoneCode");
            long phone = obj.getLong("phone");
            String country = obj.getString("country");

            Passenger passenger = new Passenger(id, firstname, lastname, birthDate, countryPhoneCode, phone, country);
            passengerStorage.addPassenger(passenger);
        }
    }
}




