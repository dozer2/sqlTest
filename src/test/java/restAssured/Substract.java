package restAssured;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Substract {
    private static String URL = "http://localhost:8080";
    private static String basePath = "/substract";

    /*
    а. Вычитаем 5 и 5
    б. Вычитаем -1 и 2
    ц. Вычитаем +10 и -1
    ц. Вычитаем 10 и -15.5
    */
    @ParameterizedTest
    @CsvSource({"5,5,0", "-1,2,1", "+10,-1,9", "10,-15.5,-5.5"})
    public void substract(Double one, Double two, Double result) {
        given()
                .param("one", one)
                .param("two", two)
                .when()
                .get(URL + basePath)
                .then()
                .body("result", is(result.toString()));
    }

    //Соответствие ответа структуре
    @Test
    public void addStructure() {
        given()
                .param("one", 1)
                .param("two", 2)
                .when()
                .get(URL + basePath)
                .then()
                .assertThat()
                .body(equalTo("{\"operation\":\"substract\",\"oneParam\":\"1.0\",\"twoParam\":\"2.0\",\"result\":\"-1.0\"}"));
    }

    /*
     а. Параметры не являются цифрой
     б. Не передаются параметры
     ц. Параметры содержат пробел
     +Проверка выдачи сообщения об ошибке
     */
    @ParameterizedTest
    @CsvSource({"q,w,fail operation", ",,fail operation", " , ,fail operation ", "1,1,fail operation"})
    public void wrongParameters(Character one, Character two, String result) {
        given()
                .param("one", one)
                .param("two", two)

                .get(URL + basePath)
                .then()
                .statusCode(200)
                .and()
                .body("error", is(result));
    }

}
