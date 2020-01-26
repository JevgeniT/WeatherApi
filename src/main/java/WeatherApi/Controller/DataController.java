package WeatherApi.Controller;

import WeatherApi.Exceptions.CityNotFoundException;
import WeatherApi.Exceptions.MissingCityInputException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;


public class DataController {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String APPID = "APPID=7a0cb318d080cc43f685ceaac25e3f89";
    static final Logger logger = LoggerFactory.getLogger(DataController.class);

    public String getCity(String cityName) throws MissingCityInputException, CityNotFoundException {
        if (cityIsMissing(cityName)){
            throw new MissingCityInputException();
        }

        logger.info("sending request for "+cityName+"\n");
        return getRequestFor(cityName);
    }

    private String getRequestFor(String cityName) throws CityNotFoundException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(BASE_URL + cityName + "&units=metric&" + APPID);
        String content = "";

        try {

            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode()==404) {
                throw new CityNotFoundException(cityName);
            }

            content = EntityUtils.toString(entity);


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return content;
    }


    public boolean cityIsMissing(String city) {
        return city == null || city.isEmpty();
    }


}