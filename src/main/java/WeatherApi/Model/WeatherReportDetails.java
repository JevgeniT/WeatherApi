package WeatherApi.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherReportDetails {

    @JsonAlias("name")
    private String city;

    private String coordinates;

    @JsonProperty("unit")
    private String unit = "Celsius";

    public WeatherReportDetails(String city ,String coordinates ,String unit) {
        this.city = city;
        this.coordinates = coordinates;
        this.unit = unit;
    }

    public WeatherReportDetails() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "WeatherReportDetails{" +
                "city='" + city + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
