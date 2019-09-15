package restAssured;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DivisionTest {
    private static String URL = "http://localhost:8080";
    private static String basePath = "/division";

    @Test//тест на деление
    public void divisionPositiveOnPositive() {
        given().
                param("one", "50").
                param("two", "5").
                when().
                get(URL.concat("/division")).
//                .asString();
//        System.out.println(result);
        then().
                body("result", is("10.0"));
    }

    @ParameterizedTest//деление параметризированный
    @CsvSource({"50,5,10", "-10,5,2", "-10,-2,5"})
    public void divisionPositiveOnPositiveParam(Double one, Double two, Double result) {
        given().
                param("one", one).
                param("two", two).
                when().
                get(URL.concat("/division")).
                then().
                body("result", is(result.toString()));
    }

    @ParameterizedTest//деление параметризированный
    @CsvSource({"10,3,3.3333333333333335", "0,10,0", "-5,2.3,-2.173913043478261", "1,0,Infinity"})
    public void divisionNegotiveOnPositiveParam(Double one, Double two, Double result) {
        given().
                param("one", one).
                param("two", two).
                when().
                get(URL.concat("/division")).
                then().
                body("result", is(result.toString()));
    }

    @Test//деление на ноль
    public void divisionByZero() {
        given().
                param("one", "5").
                param("two", "0").
                when().
                get(URL.concat("/division"))
                .then()
                .body("result", is("Infinity"));
    }

    @Test//проверка структуры Json
    public void divisionToStructure() {
        String actual = given().
                param("one", "5").
                param("two", "0").
                when().
                get(URL.concat("/division"))
                .asString();

        JSONAssert.assertEquals(
                "{\"operation\":\"division\",\"oneParam\":\"5.0\",\"twoParam\":\"0.0\",\"result\":\"Infinity\"}", actual, JSONCompareMode.STRICT);

    }
/*
-Параметры не являются числом
-Не передаются значения в параметры
-Параметры содержат пробел вместо числа
 */
    @ParameterizedTest
    @CsvSource({"x,5,0.2",",,Error", " , ,Error","1,5,Error"})
    public void divisionByChar(String one, Double two, String result) {
        given().
                param("one", one).
                param("two", two).
                get(basePath).
                then().
                assertThat().statusCode(200).
                and().
                body("result", is(result));
    }


}


