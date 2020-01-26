package WeatherApi.Model;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"weatherReportDetails", "currentWeatherReport", "dailyForecast"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecast {

    @JsonProperty("weatherReportDetails")
    private WeatherReportDetails weatherReportDetails;

    @JsonProperty("currentWeatherReport")
    private CurrentWeatherReport currentWeatherReport;

    private List<Day> dailyForecast;

    public CurrentWeatherReport getCurrentWeatherReport() {
        return currentWeatherReport;
    }

    public void setCurrentWeatherReport(CurrentWeatherReport currentWeatherReport) {
        this.currentWeatherReport = currentWeatherReport;
    }


    public WeatherReportDetails getWeatherReportDetails() {
        return weatherReportDetails;
    }

    public void setWeatherReportDetails(WeatherReportDetails weatherReportDetails) {
        this.weatherReportDetails = weatherReportDetails;
    }


    public void setDailyForecast(List<Day> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }


    public List<Day> getDailyForecast() {
        return dailyForecast;
    }

    public void add(Day day) {
        if (dailyForecast==null){
            dailyForecast= new ArrayList<>();
        }
        dailyForecast.add(day);

    }


    @Override
    public String toString() {
        return "WeatherForecast{" +
                "weatherReportDetails=" + weatherReportDetails +
                ", currentWeatherReport=" + currentWeatherReport +
                ", dailyForecast=" + dailyForecast +
                '}';
    }
}
