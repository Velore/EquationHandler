import com.czh.Equation;
import com.czh.utils.CalculateUtils;
import com.czh.utils.EquationUtils;
import com.czh.utils.RandomUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author chenzhuohong
 */
public class CalculateUtilsTest {

    @Test
    public void getPolishNotationTest(){
        Equation equation = EquationUtils.buildEquation(9);
        System.out.println(equation);
        System.out.println(CalculateUtils.getPolishNotation(equation.toString()));
//        System.out.println(CalculateUtils.getPolishNotation("3×(1'6/13-4/5)+3'2/3="));
    }

    @Test
    public void isIntegerTest(){
        System.out.println(CalculateUtils.isInteger("2'1/7"));
        System.out.println(CalculateUtils.isInteger("1/2"));
        System.out.println(CalculateUtils.isInteger("132472"));
    }

    @Test
    public void splitFractionTest(){
        System.out.println(Arrays.toString(CalculateUtils.splitFraction("2'3/5")));
    }

    @Test
    public void getMaxFactorTest(){
        System.out.println(CalculateUtils.getMaxFactor(36, 54));
    }

    @Test
    public void getMinMultipleTest(){
        System.out.println(CalculateUtils.getMinMultiple(12, 30));
    }

    @Test
    public void calculateTest(){
//        System.out.println(CalculateUtils.calculate("2'1/2÷1'3/4="));
        for(int i = 0;i<10;i++){
            Equation equation = EquationUtils.buildEquation(20);
            System.out.println(equation + CalculateUtils.calculate(equation.toString()));
        }
    }

    @Test
    public void simpleCalculateTest(){
        int maxNum = 20;
        String num1, num2;
        String operator;
        for(int i = 0;i<10;i++){
            num1 = String.valueOf(RandomUtils.randomInt(maxNum));
            num2 = String.valueOf(RandomUtils.randomInt(Integer.parseInt(num1)));
            operator = RandomUtils.randomOperator();
            System.out.println(
                    num1 +operator + num2 + "=" + CalculateUtils.simpleCalculate(num1, num2, operator));
        }
    }

    @Test
    public void reduceFractionTest(){
        System.out.println(CalculateUtils.reduceFraction("24/7"));
    }

    @Test
    public void plusTest(){
        System.out.println(CalculateUtils.plus("1'2/3", "2'5/7"));
    }

    @Test
    public void minusTest(){
        System.out.println(CalculateUtils.minus("2'3/5", "1'2/7"));
    }

    @Test
    public void multiplyTest(){
        System.out.println(CalculateUtils.multiply("1'3/5", "1'2/7"));
    }

    @Test
    public void divideTest(){
        System.out.println(CalculateUtils.divide("3", "4"));
        System.out.println(CalculateUtils.divide("2'2/3", "4"));
        System.out.println(CalculateUtils.divide("1'2/3", "2'5/7"));

    }
}
