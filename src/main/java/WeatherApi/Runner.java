package WeatherApi;
import WeatherApi.Controller.DataController;
import WeatherApi.Controller.WeatherReportGenerator;


public class Runner {
    public static void main(String[] args){

         WeatherReportGenerator generator = new WeatherReportGenerator(new DataController());
         generator.getWeatherFrom("request");

    }
}
