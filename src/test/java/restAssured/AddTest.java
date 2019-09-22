package restAssured;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

@Epic("CALCULATOR EPIC")
@Feature("CHECK ADDITION METHODS")
@Owner("DEMO.JAR")
@Link(name = "URL LINK TO ADDITION METHODS ", url = "http://localhost:8080/add?one=1&two=5")
public class AddTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,55", "-10,5,-5", "-10,-2,-12","1,0,1"})
    @Story(value = "ADDITION METHOD - POSITIVE DATA")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.CRITICAL)
    public void addPositive(Double one, Double two,Double result) {
        positiveTest(one,two,result);

    }

    @Test
    @Story(value = "FIELD VALIDATION")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.BLOCKER)
    public void addPositiveTestAllData() throws IOException {
        positiveTestAllData(1d,2d,3d,"addition");

    }

    @Override
    String getPath() {
        return "/add";
    }

}
