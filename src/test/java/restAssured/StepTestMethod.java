package restAssured;

import io.qameta.allure.Step;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepTestMethod {

    @Step
    public static void checkSumStep(int num1, int num2, int expectedSum) {
        assertTrue(num1 + num2 == expectedSum, "Сумма слагаемых не соответствует ожидаемому значению");
    }
}
