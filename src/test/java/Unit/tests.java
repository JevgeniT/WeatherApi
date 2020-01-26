package Unit;

import WeatherApi.Exceptions.CityNotFoundException;
import WeatherApi.Util.Converter;
import WeatherApi.Controller.DataController;
import WeatherApi.Exceptions.MissingCityInputException;
import WeatherApi.Model.WeatherForecast;
import WeatherApi.Util.FileUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


public class tests {

    private DataController controller;
    private Converter converter;
    private String rawResponse;


    public static final String Tallinn = "Tallinn";


    @Before
    public void setUp(){
        controller = new DataController();
        converter = new Converter();

        try{
            rawResponse = controller.getCity(Tallinn);
        }catch (MissingCityInputException | CityNotFoundException ignored){ }

    }

    @Test
    public void hasDataWithValuesInResponse() throws CityNotFoundException, MissingCityInputException {
        Pattern pattern = Pattern.compile("\"(humidity|temp|pressure)\":(\\d+)");
        Matcher matcher = pattern.matcher(controller.getCity(Tallinn));

        assertTrue(matcher.find());
    }



    @Test
    public void forecastHasSomeDays(){
        WeatherForecast newForecast = converter.fromJsonToObject(rawResponse);

        assertTrue(newForecast.getDailyForecast()
                            .stream()
                            .allMatch(day ->
                                         day.getPressure()>0 &&
                                         day.getHumidity()>0 &&
                                         day.getDate()!=null));

    }

    @Test
    public void shouldHaveThreeDayForecast(){
        WeatherForecast newForecast = converter.fromJsonToObject(rawResponse);


        assertTrue(newForecast.getDailyForecast()
                .stream()
                .allMatch(day ->
                        day.getPressure()>0 &&
                                day.getHumidity()>0 &&
                                day.getDate()!=null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingFileThrowsException(){
        FileUtil.readInputFrom("абв");
    }

    @Test(expected = CityNotFoundException.class)
    public void wrongCityThrowsException() throws MissingCityInputException, CityNotFoundException {
        controller = new DataController();
        controller.getCity("s");

    }

    @Test(expected = MissingCityInputException.class)
    public void missingCityThrowsException() throws MissingCityInputException, CityNotFoundException {
        controller = new DataController();

        controller.getCity(null);
    }
}
