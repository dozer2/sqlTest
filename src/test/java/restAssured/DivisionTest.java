package restAssured;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;


@Epic("CALCULATOR EPIC")
@Feature("CHECK DIVISION METHODS")
@Owner("DEMO.JAR")
@Link(name = "URL LINK TO DIVISION METHODS ", url = "http://localhost:8080/division?one=1&two=5")
public class DivisionTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,10", "-10,5,-2", "-10,-2,5","1,0,Infinity"})
    @Story(value = "DIVISION METHOD - POSITIVE DATA")
    @Description("It is the best Description that you've ever seen")
    public void divisionPositive(Double one, Double two,Double result) throws IOException {
        positiveTest(one,two,result);
        readJson(one, two);
    }
    @Test
    @Story(value = "FIELD VALIDATION")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.BLOCKER)
    public void divPositiveTestAllData() throws IOException {
        positiveTestAllData(2d,1d,2d,"division");
    }

    @Override
    String getPath() {
        return "/division";
    }
}
