package restAssured;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractTest {
    @BeforeAll
    public static void init() {
        BasicConfigurator.configure();
        RestAssured.baseURI = "http://localhost:8080";
    }


    @Step("CHECKED DATA: {num1} AND {num2}")
    public void addValidDataType(String one, String two, Integer resultCode) {
        given()
                .param("one", one)
                .param("two", two)
                .when()
                .get(getPath())
                .then()
                .statusCode(resultCode);
    }

    @Step("CHECK JSON FIELD 'operation' FROM SERVER. EXPECTED RESULT {result},{operation}")
    protected void positiveTestAllData(Double one, Double two, Double result, String operation) throws IOException {
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
        getBytesAnnotationWithArgs("SuccessSchema.json");

    }

    @Step("STEP: CHECKED DATA: {one} AND {two}")
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
    public void structIsValid() throws IOException {
        given()
                .param("one", 5)
                .param("two", 1)
                .when()
                .get(getPath())
                .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("SuccessSchema.json"));
        getBytesAnnotationWithArgs("SuccessSchema.json");
    }

    @Attachment
    public static byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources", resourceName));
    }

    @Attachment(value = "Attachment JSON", type = "application/json", fileExtension = ".txt")
    public static byte[] getBytesAnnotationWithArgs(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/test/resources", resourceName));
    }

    @Step("Проверка сложения чисел {num1} и числа {num2}")
    public void checkSumStep(int num1, int num2, int expectedSum) throws IOException {
        assertTrue(num1 + num2 == expectedSum, "Сумма слагаемых не соответствует ожидаемому значению");
        getBytes("done.jpg");

    }

    @Step("STEP: WRITE JSON TO FILE AND ADD TO ATTACHMENT CHECKED DATA: {one} AND {two}")
    public void readJson(Double one, Double two) throws IOException {
        String jsonToFile = given()
                .param("one", one)
                .param("two", two)
                .get(getPath())
                .body()
                .prettyPrint();

        try {
            FileWriter file = new FileWriter("src/test/resources/output.json");
            file.write(jsonToFile);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        //Allure.addAttachment("The JSON Has just been read ","application/json","src/test/resources/output.json");
        getBytes("output.json");
        getBytesAnnotationWithArgs("output.json");
    }
@Step("STEP: Read Json to Object CHECKED DATA: {one} AND {two}")
public void readJsonToObject(Double one, Double two) throws IOException {
        ObjectToClass objectToClass=given()
                .param("one", one)
                .param("two", two)
                .get(getPath())
                .body()
                .as(ObjectToClass.class);
    try {
        FileWriter file = new FileWriter("src/test/resources/object.json");
        file.write(objectToClass.toString());
        file.flush();
        file.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    getBytesAnnotationWithArgs("object.json");
}

    abstract String getPath();
}
