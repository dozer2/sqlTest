package restAssured;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("All cases")
@Feature("Multiply operation")
public class MultiplyTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,250", "-10,5,-50", "-10,-2,20","1,0,0"})
    public void multiplyPositive(Double one, Double two,Double result) {
        positiveTest(one,two,result);
    }

    @Test
    @Severity(value = SeverityLevel.BLOCKER)
    public void multiplyPositiveTestAllData() {
        positiveTestAllData(2d,2d,3d,"multiply");
    }

    @Test
    public void simpleTest2() {
        StepTestMethod.checkSumStep(3, 2, 5);
        StepTestMethod.checkSumStep(5, 4, 100);
    }

    @Override
    String getPath() {
        return "/add";
    }
}
