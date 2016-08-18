import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

public class ArithmeticTest {

    private static Arithmetic arithmetic;

    @BeforeClass
    public static void setUpClass() {
        arithmetic = new Arithmetic();
    }

    @Test
    public void simpleCalculation() {
        String inputExpression = "1+2*(3/4)";
        BigDecimal expected = new BigDecimal(2.50);

        Assert.assertEquals(expected, arithmetic.calculation(inputExpression));
    }

    @Test
    public void calculationBigDecimal() {
        String inputExpression = "1111111111111111+2222222222222222222*(3333333333333333333+4444444444444444444)";
        String expected = "17283950617283950613828271604938271605";

        Assert.assertEquals(expected, arithmetic.calculation(inputExpression).toString());
    }

}