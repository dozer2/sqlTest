package restAssured;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

@Epic("CALCULATOR EPIC")
@Feature("CHECK SUBSTRACTION METHODS")
@Owner("DEMO.JAR")
@Link(name = "URL LINK TO SUBSTRACTION METHODS ", url = "http://localhost:8080/substract?one=1&two=5")
public class SubstructTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,45", "-10,5,-15", "-10,-2,-8","1,0,1"})
    @Story("MULTIPLY METHOD - POSITIVE DATA")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.CRITICAL)
    public void substructPositive(Double one, Double two,Double result) throws IOException {
        positiveTest(one,two,result);
        readJson(one, two);


    }

    @Test
    @Story("FIELD VALIDATION")
    @Severity(SeverityLevel.BLOCKER)
    @Description("It is the best Description that you've ever seen")
    public void substrPositiveTestAllData() throws IOException {
        positiveTestAllData(4d,2d,2d,"substract");

    }

    @ParameterizedTest
    @CsvSource({ "g,b,400", "1,,500"})
    @Story(value = "ADDITION METHOD - NEGATIVE DATA")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.CRITICAL)
    public void Negative(String one, String two,Integer code) throws IOException {
        CheckStatusCode(one, two,code);

    }


    @Override
    String getPath() {
        return "/substract";
    }
}
