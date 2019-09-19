package restAssured;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Link("https://storage1a.censor.net/images/0/1/a/8/01a8d1525133e5160c4658d900cae56a/original.jpg")
@Epic("All cases Epic")
@Feature("Division operation Feature")
public class DivisionTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,10", "-10,5,-2", "-10,-2,5","1,0,Infinity"})
    public void divisionPositive(Double one, Double two,Double result) {
        positiveTest(one,two,result);
    }
    @Test
    public void divPositiveTestAllData() {
        positiveTestAllData(2d,1d,2d,"division");
    }

    @Override
    String getPath() {
        return "/division";
    }
}
