import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

/**
 * Created by coura on 13.05.2016.
 */
public class Main {



    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputExpression = reader.readLine();
        Arithmetic arithmetic = new Arithmetic();

        BigDecimal result = arithmetic.calculation(inputExpression);
        System.out.println(result);

    }


}
