package WeatherApi.Util;

import WeatherApi.Model.Day;
import WeatherApi.Model.WeatherForecast;
import WeatherApi.Model.WeatherReportDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;



public class Converter {

    private WeatherForecast forecast ;
    private ObjectMapper objectMapper = new ObjectMapper();
    static final Logger logger = LoggerFactory.getLogger(Converter.class);

    public WeatherForecast fromJsonToObject(String request) {
       forecast = new WeatherForecast();
        try {
            setCity(request);
            setDays(request);
        } catch (JSONException | IOException e) {
            logger.error(e.getMessage());
        }
        return forecast;
    }

    public void fromObjectToJson(WeatherForecast forecast){
        String output  = String.format("src/main/java/WeatherApi/Output/%s.json", forecast.getWeatherReportDetails().getCity());

        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(output),forecast);
            logger.info("writing file for "+forecast.getWeatherReportDetails().getCity()+"\n");

        } catch (IOException e) {
            logger.error(e.getMessage());

        }
    }

    private void setDays(String request) throws JSONException, IOException {

        JSONArray jsonarray = new JSONObject(request).getJSONArray("list");
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject dayString = jsonarray.getJSONObject(i).getJSONObject("main");
            dayString.put("date", jsonarray.getJSONObject(i).getString("dt_txt"));
            Day day = objectMapper.readValue(dayString.toString() ,Day.class);
            forecast.add(day);
        }
    }

    private void setCity(String request) throws JSONException, IOException {

        JSONObject jsonCity = new JSONObject(request).getJSONObject("city");
        WeatherReportDetails details = objectMapper.readValue(jsonCity.toString(), WeatherReportDetails.class);

        JSONObject jsonCoord = jsonCity.getJSONObject("coord");
        String coordinates = String.format("%s,%s", jsonCoord.getString("lat"), jsonCoord.getString("lon"));
        details.setCoordinates(coordinates);
        forecast.setWeatherReportDetails(details);
    }

}
