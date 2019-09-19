package restAssured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("222222222222222222")
@Feature("33333333333333333333333333")
public class SubstructTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,45", "-10,5,-15", "-10,-2,-8","1,0,1"})
    @Story("11111111111111111111111")
    @Description("traalalala")
    public void substructPositive(Double one, Double two,Double result) {
        positiveTest(one,two,result);
    }

    @Test
    @Story("555555555555555555555555")
    @Description("traalalala")
    public void substrPositiveTestAllData() {
        positiveTestAllData(4d,2d,2d,"substract");
    }

    @Override
    String getPath() {
        return "/substract";
    }
}
