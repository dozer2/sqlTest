package restAssured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class UncorrectOperation {
    private static String URL = "http://localhost:8080";
    private static String basePath = "/xxx";
/*Проверка выдачи сообщения об ошибке*/
    @Test
    public void wrongOperation() {
        String result="fail operation";
        given()
                .param("one", 1)
                .param("two", 2)
        .when()
                .get(URL+basePath)
        .then()
                .statusCode(200)
                .and()
                .body("error",is(result));
// или второй способ сравнения
//                .and()
//                .body("error",equalTo(result));
    }
}
