import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

/**
 * Created by coura on 18.05.2016.
 */

public class Arithmetic {
    private Stack<BigDecimal> stackResult = new Stack<>();

    public BigDecimal calculation(String expression) {

        Parser parser = new Parser();
        Stack<String> stack = parser.parsing(expression);
        for (String s : stack) {
            try {
                stackResult.push(new BigDecimal(s));
            } catch (NumberFormatException e) {
                if (s.equals("*")) {
                    stackResult.push(stackResult.pop().multiply(stackResult.pop()));
                } else if (s.equals("/")) {
                    BigDecimal a = stackResult.pop(), b = stackResult.pop();
                    stackResult.push(b.divide(a));
                } else if (s.equals("+")) {
                    stackResult.push(stackResult.pop().add(stackResult.pop()));
                } else if (s.equals("-")) {
                    BigDecimal a = stackResult.pop(), b = stackResult.pop();
                    stackResult.push(b.subtract(a));
                } else if (s.equals("^")) {
                    BigDecimal a = stackResult.pop(), b = stackResult.pop();
                    stackResult.push(pow(b, a));
                }
            }
        }
        return stackResult.pop();
    }

    private BigDecimal pow(BigDecimal number, BigDecimal power) {
        if (power.equals(BigDecimal.ZERO)) {
            return BigDecimal.valueOf(1);
        }

        BigDecimal result = number;
        BigDecimal count = BigDecimal.valueOf(1);

        while (count.compareTo(power.abs()) < 0) {
            result = result.multiply(number);
            count = count.add(BigDecimal.ONE);
        }
        if (power.compareTo(BigDecimal.ZERO) > 0) {
            return result;
        } else if (power.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ONE.divide(result, MathContext.DECIMAL128);
            //Just so that it doesn't throw an error of non-terminating decimal expansion (ie. 1.333333333...)
        } else return null;    //In case something goes wrong
    }
}
