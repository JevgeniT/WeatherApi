package WeatherApi.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileUtil {

    public static Stream<String> readInputFrom(String fileName){
        try{
            return Files.lines(Paths.get("src/main/java/WeatherApi/Input/"+fileName));
        }catch (IOException e ){
            throw new IllegalArgumentException(fileName+" is missing in root path");
        }
    }
}
