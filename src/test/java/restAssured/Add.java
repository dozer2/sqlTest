package restAssured;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Add {
    private static String URL = "http://localhost:8080";
    private static String basePath = "/add";

    /*
    Складываем 5 и 5
 б. Складываем -1 и 2
 ц. Складываем -1 и -1
 д. Складываем -1 и 1.5
    * */
    @ParameterizedTest
    @CsvSource({"5,5,10", "-1,2,1", "-1,-1,-2","-1,1.5,0.5"})
    public void addPositive(Double one, Double two, Double result) {
        given().
                baseUri(URL).
                basePath(basePath).

                param("one",one).
                param("two", two).
                when().
                get(URL+basePath).
//                then().
//                body("result",is(result.toString()));
        then().assertThat()
                //.body("result", hasItems(result.toString()));
                .body("result", equalTo(result));

    }


    @Test//проверка структуры json
    public void addStructure(){
        given().
                baseUri(URL).
                basePath(basePath).
                param("one",1).
                param("two",2).
                when().get(URL+basePath).
                then().
                contentType(ContentType.JSON).
                and().
                assertThat().body(equalTo("{\"operation\":\"Addition\",\"oneParam\":\"1.0\",\"twoParam\":\"2.0\",\"result\":\"3.0\"}"));

    }

    @ParameterizedTest
    @CsvSource({"q,w,Error",",,Error"," , ,Error"})
    public void wrongParametrs(String one,String two, String result){

        given().baseUri(URL).basePath(basePath).
                param("one", one).
                param("two",two).
                when().
                get(URL+basePath).
                then().
                statusCode(200);

}

}

