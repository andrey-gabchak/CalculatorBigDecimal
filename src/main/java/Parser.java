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
