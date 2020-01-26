package WeatherApi.Exceptions;

public class CityNotFoundException extends Exception {

    public CityNotFoundException(String city){
        super(String.format("Could not get data for %s. Server responded with status code: 404", city));
    }
}
