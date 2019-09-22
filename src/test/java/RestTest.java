//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.specification.RequestSpecification;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import static io.restassured.RestAssured.given;
//
//
//public class RestTest {
//    private static String myAlfa = "F";
//    private static String URL = "https://restcountries-v1.p.rapidapi.com";
//    private static RequestSpecification requestSpecification;
//
//    @BeforeAll
//    private static void addDataToHeader() {
//        requestSpecification = new RequestSpecBuilder()
//                .addHeader("X-RapidAPI-Key", "2c4a134350mshb29924d67b8e242p1a09b1jsn49e416ee84d1")
//                .build();
//    }
//
//    @Test
//    public void mustBeGetCountryCode() {
//                given()
//                .spec(requestSpecification)
//                .when()
//                .get(URL.concat("/all"));
////              .asString();
//               // .then()
//                //.body("name", Matchers.not(hasItem(startsWith(myAlfa))));
//    }
//
//}
