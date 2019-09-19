package restAssured;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Epic("All cases")
@Feature("Add operation")
public class AddTest extends AbstractTest{

    @ParameterizedTest
    @CsvSource({ "50,5,55", "-10,5,-5", "-10,-2,-12","1,0,1"})
    public void addPositive(Double one, Double two,Double result) {

        positiveTest(one,two,result);

    }
    @Test
    @Story("Valid data typeasassa")
    public void addPositiveTestAllData() {
        positiveTestAllData(1d,2d,3d,"addition");

    }



    @Override
    String getPath() {
        return "/add";
    }
}
