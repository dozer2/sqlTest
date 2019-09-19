package restAssured;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public abstract class AbstractTest {
    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Story("Story")
    @Description(" Description")
    public void addValidDataType(String one, String two, Integer resultCode) {
        given()
                .param("one", one)
                .param("two", two)
                .when()
                .get(getPath())
                .then()
                .statusCode(resultCode);
    }


    protected void positiveTestAllData(Double one, Double two, Double result, String operation) {
        given()
                .param("one", one)
                .param("two", two)
                .when()
                .get(getPath())
                .then()
                .body("oneParam", is(one.toString()))
                .body("twoParam", is(two.toString()))
                .body("result", is(result.toString()))
                .body("operation", is(operation));

    }

    protected void positiveTest(Double one, Double two, Double result) {
        given()
                .param("one", one)
                .param("two", two)
                .when()
                .get(getPath())
                .then()
                .body("result", is(result.toString()));
    }

    @Test
    @Story(" Story")
    @Description("Description")
    public void structIsValid() {
        given()
                .param("one", 5)
                .param("two", 1)
                .when()
                .get(getPath())
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("SuccessSchema.json"));
    }

    abstract String getPath();
}
