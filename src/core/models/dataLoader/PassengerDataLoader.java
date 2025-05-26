package core.models.dataLoader;

import core.models.Passenger;
import core.models.Storage.PassengerStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.time.LocalDate;

public class PassengerDataLoader implements IDataLoader {
    @Override
    public void load() {
        PassengerStorage passengerStorage = PassengerStorage.getInstance();

        InputStream stream = getClass().getResourceAsStream("/json/passengers.json");
        JSONArray array = new JSONArray(new JSONTokener(stream));

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Passenger passenger = new Passenger(
                obj.getLong("id"),
                obj.getString("firstname"),
                obj.getString("lastname"),
                LocalDate.parse(obj.getString("birthDate")),
                obj.getInt("countryPhoneCode"),
                obj.getLong("phone"),
                obj.getString("country")
            );
            passengerStorage.addPassenger(passenger);
        }
    }
}