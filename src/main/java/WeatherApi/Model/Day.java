package WeatherApi.Model;

import com.fasterxml.jackson.annotation.*;
import java.time.LocalDate;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date;

    @JsonAlias("temp")
    private int temperature;
    private int humidity;
    private int pressure;

    public Day(String date ,int temperature ,int humidity ,int pressure) {
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public Day() {
    }

    @JsonIgnore
    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public void setDate(String date) {
        this.date = date.substring(0,10);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {
        return "Day{" +
                "date='" + date + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Day)) return false;
        Day day = (Day) o;
        return date.equals(day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}

