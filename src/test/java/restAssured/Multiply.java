package restAssured;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class Multiply {
    private static String URL = "http://localhost:8080";
    private static String basePath = "/multiply";

/*
- Умножаем 5 на 5
- Умножаем 5 на 0
- Умножаем 5 на 2.5
- Умножаем -5 на -4.5
- Умножаем -1 на 0.5
* */
    @ParameterizedTest
    @CsvSource({"5,5,26", "5,0,0", "5,2.5,12.5","-5,-4.5,22.5","-1,0.5,-0.5"})
    public void multiplyPositive(Double one, Double two, Double result) {
        given().
                baseUri(URL).
                basePath(basePath).

                param("one",one).
                param("two", two).
        when().
                get().
        then().
                body("result",is(result));
    }

    @Test//проверка структуры json
    public void multiplyStructure(){
        given().
                baseUri(URL).
                basePath(basePath).
                param("one",1).
                param("two",2).
                when().get().
                then().
                assertThat().contentType(ContentType.JSON).
                and().
                assertThat().body(equalTo("{\"operation\":\"multiply\",\"oneParam\":\"1.0\",\"twoParam\":\"2.0\",\"result\":\"0.5\"}"));

    }


}
