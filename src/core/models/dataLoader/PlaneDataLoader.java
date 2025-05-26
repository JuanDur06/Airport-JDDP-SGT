package core.models.dataLoader;

import core.models.Plane;
import core.models.Storage.PlaneStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class PlaneDataLoader implements IDataLoader {
    @Override
    public void load() {
        PlaneStorage planeStorage = PlaneStorage.getInstance();

        InputStream stream = getClass().getResourceAsStream("/json/planes.json");
        JSONArray array = new JSONArray(new JSONTokener(stream));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Plane plane = new Plane(
                obj.getString("id"),
                obj.getString("brand"),
                obj.getString("model"),
                obj.getInt("maxCapacity"),
                obj.getString("airline")
            );
            planeStorage.addPlane(plane);
        }
    }
}