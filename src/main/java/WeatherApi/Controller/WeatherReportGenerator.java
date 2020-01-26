package WeatherApi.Controller;

import WeatherApi.Exceptions.CityNotFoundException;
import WeatherApi.Exceptions.MissingCityInputException;
import WeatherApi.Model.CurrentWeatherReport;
import WeatherApi.Model.Day;
import WeatherApi.Model.WeatherForecast;
import WeatherApi.Util.Converter;
import WeatherApi.Util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherReportGenerator {

   private List<Day> days;
   private DataController controller ;
   private Converter converter = new Converter();
   static final Logger logger = LoggerFactory.getLogger(WeatherReportGenerator.class);

   public WeatherReportGenerator(DataController controller) {
      this.controller = controller;
   }


   public void getWeatherFrom(String fileName){

      FileUtil.readInputFrom(fileName).forEach(cityName -> {
         try {
               processData(cityName);
         } catch (CityNotFoundException | MissingCityInputException e) {
               logger.error(e + "\n" );
         }
      });

   }

   private void processData(String cityName) throws CityNotFoundException, MissingCityInputException {


      WeatherForecast forecast = converter.fromJsonToObject(controller.getCity(cityName));

      days = forecast.getDailyForecast()
              .stream()
              .filter(day -> day.getDate().compareTo(LocalDate.now())<=3 )
              .collect(Collectors.toList());


      forecast.setCurrentWeatherReport(getReportForToday());
      forecast.setDailyForecast(getAverageDays());

      converter.fromObjectToJson(forecast);
   }

   private CurrentWeatherReport getReportForToday(){
      return new CurrentWeatherReport(
                      getAverageTemperature(LocalDate.now()),
                      getAveragePressure(LocalDate.now()),
                      getAverageHumidity(LocalDate.now())
      );
   }



   private List<Day> getAverageDays(){
      return days.stream()
              .distinct()
              .filter(day -> day.getDate().isAfter(LocalDate.now()))
              .peek(day -> {
                 day.setHumidity(getAverageHumidity(day.getDate()));
                 day.setTemperature(getAverageTemperature(day.getDate()));
                 day.setPressure(getAveragePressure(day.getDate()));
              }).collect(Collectors.toList());
   }

   private int getAverageTemperature(LocalDate date){
      return (int) days.stream().filter(day -> day.getDate().isEqual(date)).mapToInt(Day::getTemperature).average().orElse(0);
   }


   private int getAverageHumidity(LocalDate date){
      return (int) days.stream().filter(day -> day.getDate().isEqual(date)).mapToInt(Day::getHumidity).average().orElse(0);
   }


   private int getAveragePressure(LocalDate date){
      return (int) days.stream().filter(day -> day.getDate().isEqual(date)).mapToInt(Day::getPressure).average().orElse(0);
   }


}
