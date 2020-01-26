package WeatherApi.Exceptions;

public class MissingCityInputException extends Exception {

    public MissingCityInputException(){
        super("City name is missing.");
    }
}
