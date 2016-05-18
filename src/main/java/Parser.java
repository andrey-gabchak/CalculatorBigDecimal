import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by coura on 16.05.2016.
 */
public class Parser {
    private final String OPERATORS = "+-*/^";
    private Stack<String> stackOperations = new Stack<>();
    private Stack<String> stackRPN = new Stack<>();
    private Stack<BigDecimal> stackResult = new Stack<>();

    public BigDecimal calculation(String expression) {

        Stack<String> stack = parsing(expression);
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

    protected Stack<String> parsing(String InputExpression) {
        stackOperations.clear();
        stackRPN.clear();

        InputExpression = InputExpression.replace(" ", "").replace("(-", "(0-");
        if (InputExpression.charAt(0) == '-') {
            InputExpression = "0" + InputExpression;
        }

        StringTokenizer expressionTokenizer = new StringTokenizer(InputExpression, OPERATORS + "()", true);

        while (expressionTokenizer.hasMoreTokens()) {
            String token = expressionTokenizer.nextToken();
            if (isNumber(token)) {
                stackRPN.push(token);
            } else if (token.equals("(")) {
                stackOperations.push(token);
            } else if (token.equals(")")) {
                while (!stackOperations.peek().equals("(")) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.pop();
            } else if (isOperator(token)) {
                while (!stackOperations.empty()
                        && getPrecedence(token) <= getPrecedence(stackOperations
                        .lastElement())) {
                    stackRPN.push(stackOperations.pop());
                }
                stackOperations.push(token);
            }
        }
        while (!stackOperations.empty()) {
            stackRPN.push(stackOperations.pop());
        }

        return stackRPN;
    }

    private boolean isNumber(String token) {
        try {
            new BigDecimal(token);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }

    private byte getPrecedence(String token) {
        if (token.equals("^")) {
            return 3;
        } else if (token.equals("*") || token.equals("/")) {
            return 2;
        } else if (token.equals("+") || token.equals("-")) {
            return 1;
        }
        return 0;
    }
}
