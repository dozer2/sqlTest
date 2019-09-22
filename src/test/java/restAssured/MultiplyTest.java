package restAssured;

import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

@Epic("CALCULATOR EPIC")
@Feature("CHECK MULTIPLY METHODS")
@Owner("DEMO.JAR")
@Link(name = "URL LINK TO MULTIPLY METHODS ", url = "http://localhost:8080/multiply?one=1&two=5")
public class MultiplyTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,250", "-10,5,-50", "-10,-2,20","1,0,0"})
    @Story("MULTIPLY METHOD - POSITIVE DATA")
    @Description("It is the best Description that you've ever seen")
    @Severity(SeverityLevel.CRITICAL)
    public void multiplyPositive(Double one, Double two,Double result) throws IOException {
        positiveTest(one,two,result);
        readJson(one, two);
    }

    @Test
    @Story("FIELD VALIDATION")
    @Severity(SeverityLevel.BLOCKER)
    @Description("It is the best Description that you've ever seen")
    public void multiplyPositiveTestAllData() throws IOException {
        positiveTestAllData(2d,2d,3d,"multiply");
    }


    @Test
    public void simpleTest2() throws IOException {
        checkSumStep(3, 2, 5);
        checkSumStep(5, 4, 100);

    }

    @Override
    String getPath() {
        return "/add";
    }
}
