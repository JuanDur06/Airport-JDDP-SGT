package core.models.dataLoader;

import core.models.Location;
import core.models.Storage.LocationStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class LocationDataLoader implements IDataLoader {

    @Override
    public void load() {
        LocationStorage locationStorage = LocationStorage.getInstance();

        InputStream stream = getClass().getResourceAsStream("/json/locations.json");
        JSONArray array = new JSONArray(new JSONTokener(stream));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Location location = new Location(
                    obj.getString("airportId"),
                    obj.getString("airportName"),
                    obj.getString("airportCity"),
                    obj.getString("airportCountry"),
                    obj.getDouble("airportLatitude"),
                    obj.getDouble("airportLongitude")
            );
            locationStorage.addLocation(location);
        }
    }
}
