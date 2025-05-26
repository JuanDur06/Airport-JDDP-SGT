package core.models.dataLoader;

import core.models.*;
import core.models.Storage.FlightStorage;
import core.models.Storage.LocationStorage;
import core.models.Storage.PlaneStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.time.LocalDateTime;

public class FlightDataLoader implements IDataLoader {

    @Override
    public void load() {
        FlightStorage flightStorage = FlightStorage.getInstance();
        LocationStorage locationStorage = LocationStorage.getInstance();
        PlaneStorage planeStorage = PlaneStorage.getInstance();

        InputStream stream = getClass().getResourceAsStream("/json/flights.json");
        JSONArray array = new JSONArray(new JSONTokener(stream));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String id = obj.getString("id");
            Plane plane = planeStorage.getPlane(obj.getString("plane"));
            Location departure = locationStorage.getLocation(obj.getString("departureLocation"));
            Location arrival = locationStorage.getLocation(obj.getString("arrivalLocation"));

            Location scale = null;
            if (!obj.isNull("scaleLocation")) {
                scale = locationStorage.getLocation(obj.getString("scaleLocation"));
            }

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
    }
}
