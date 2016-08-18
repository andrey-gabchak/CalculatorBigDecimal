import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Stack;

public class TestParser {

    static Parser parser;

    @BeforeClass
    public static void setUpClass() {
        parser = new Parser();
    }

    @Test
    public void parsingOperatorWithOperatorAndBracket() {
        final String inputExpression = "1+2*(3+4)";
        final String expected = "1234+*+";
        Assert.assertEquals(expected, stackToString(parser.parsing(inputExpression)));
    }

    @Test
    public void parsingSimpleOperations() {
        final String inputExpression = "1+2*3+4";
        final String expected = "123*+4+";
        Assert.assertEquals(expected, stackToString(parser.parsing(inputExpression)));
    }

    @Test(expected = RuntimeException.class)
    public void tooManyBrackets() {
        final String inputExpression = "(*))";
        parser.parsing(inputExpression);
    }

    @Test(expected = RuntimeException.class)
    public void tooManyOperators() {
        final String inputExpression = "1**2";
        parser.parsing(inputExpression);
    }

    @Test(expected = RuntimeException.class)
    public void insufficientlyBrackets() {
        final String inputExpression = "(1+2";
        parser.parsing(inputExpression);
    }

    public String stackToString(Stack<String> inputStack) {
        StringBuilder result = new StringBuilder();
        for (String stackElement : inputStack) {
            result.append(stackElement);
        }
        System.out.println(result);
        return result.toString();
    }
}
