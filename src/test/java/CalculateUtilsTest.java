import com.czh.Equation;
import com.czh.utils.CalculateUtils;
import com.czh.utils.ElementUtils;
import com.czh.utils.EquationUtils;
import com.czh.utils.RandomUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        System.out.println("2'1/7 is Integer："+ElementUtils.isInteger("2'1/7"));
        System.out.println("1/2 is Integer："+ElementUtils.isInteger("1/2"));
        System.out.println("132472 is Integer："+ElementUtils.isInteger("132472"));
    }

    @Test
    public void splitFractionTest(){
        System.out.println(Arrays.toString(ElementUtils.splitFraction("2'3/5")));
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
        System.out.println(CalculateUtils.calculate("10 ÷ (14'3/5 + 4) - 12/13 ="));
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
    public void simplifyFractionTest(){
        System.out.println(ElementUtils.simplifyFraction("3'8/8"));
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
        System.out.println(CalculateUtils.divide("0", "4"));
        System.out.println(CalculateUtils.divide("2", "0"));
        System.out.println(CalculateUtils.divide("2'2/3", "4"));
        System.out.println(CalculateUtils.divide("1'2/3", "2'5/7"));

    }

    @Test
    public void duplicateCheckTestTrue(){
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        l1.add("1/2");
        l1.add("2");
        l1.add("3");
        l1.add("÷");
        l1.add("+");
        l2.add("2");
        l2.add("3");
        l2.add("÷");
        l2.add("1/2");
        l2.add("+");
        System.out.println(l1);
        System.out.println(l2);
        boolean result = CalculateUtils.duplicateCheck(l1,l2);
        System.out.println(result);
    }

    @Test
    public void duplicateCheckTestFalse(){
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        l1.add("1/2");
        l1.add("2");
        l1.add("3");
        l1.add("×");
        l1.add("+");
        l2.add("2");
        l2.add("3");
        l2.add("-");
        l2.add("1/2");
        l2.add("+");
        System.out.println(l1);
        System.out.println(l2);
        boolean result = CalculateUtils.duplicateCheck(l1,l2);
        System.out.println(result);
    }

    @Test
    public void duplicateCheckTestDivideFalse(){
        //比起第一个测试类，更换了除数和被除数的顺序
        List<String> l1 = new ArrayList<>();
        List<String> l2 = new ArrayList<>();
        l1.add("1/2");
        l1.add("3");
        l1.add("2");
        l1.add("÷");
        l1.add("+");
        l2.add("2");
        l2.add("3");
        l2.add("÷");
        l2.add("1/2");
        l2.add("+");
        System.out.println(l1);
        System.out.println(l2);
        boolean result = CalculateUtils.duplicateCheck(l1,l2);
        System.out.println(result);
    }
}
