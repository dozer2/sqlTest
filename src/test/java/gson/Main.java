package gson;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class Main {

    private static String URL = "https://restcountries-v1.p.rapidapi.com/all";
    private static String key = "X-RapidAPI-Key";
    private static String value = "2c4a134350mshb29924d67b8e242p1a09b1jsn49e416ee84d1";

    public static void main(String[] args) throws UnirestException, IOException {//throws IOException {

        String path = "https://restcountries-v1.p.rapidapi.com/all"; //This is the base url of the API tested
        java.net.URL url = new URL(path);

        Country[] country= given(). //Rest Assured syntax
                contentType("application/json"). //API content type
                given().header(key, value). //Some API contains headers to run with the API
                when().
                get(url).
                as(Country[].class);

        Country resultCountry=Stream.of(country)
                .filter(cntr->cntr.getAlpha2Code()
                        .equalsIgnoreCase("AF"))
                        .findAny()
                        .orElse(null);

         if(resultCountry!=null){
             System.out.println(resultCountry.getName());
         }
    }

}
