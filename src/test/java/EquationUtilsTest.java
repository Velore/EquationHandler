import com.czh.Equation;
import org.junit.Test;
import com.czh.utils.EquationUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author chenzhuohong
 */
public class EquationUtilsTest {

    @Test
    public void reverseListByIndexTest(){
        String[] s = {"(", "a", "b", ")", "-", "c", "d"};
        ArrayList<String> list = new ArrayList<>(Arrays.asList(s));
        list = EquationUtils.reverseListByIndex(list, 4);
        System.out.println(list);
    }

    @Test
    public void checkMinusTest(){
        //减法出现的四种情况
        //a*(b-c)-d
        String[] s0 = {"2", " × ", "(", "4", " - ", "5", ")", " - ", "6"};
        ArrayList<String> l0 = new ArrayList<>(Arrays.asList(s0));
        System.out.println(l0);
        EquationUtils.checkMinus(l0);
        System.out.println(l0);
        //(a-b)*c-d
        String[] s1 = {"(", "4", " - ", "5", ")", " × ", "2", " - ", "6"};
        ArrayList<String> l1 = new ArrayList<>(Arrays.asList(s1));
        System.out.println(l1);
        EquationUtils.checkMinus(l1);
        System.out.println(l1);
        //a-(b-c)*d
        String[] s2 = { "2", " - ", "(", "4", " - ", "5", ")", " × ", "6"};
        ArrayList<String> l2 = new ArrayList<>(Arrays.asList(s2));
        System.out.println(l2);
        EquationUtils.checkMinus(l2);
        System.out.println(l2);
        //a-b*(c-d)
        String[] s3 = {"2", " - ", "6", " × ", "(", "4", " - ", "5", ")"};
        ArrayList<String> l3 = new ArrayList<>(Arrays.asList(s3));
        System.out.println(l3);
        EquationUtils.checkMinus(l3);
        System.out.println(l3);
    }

    @Test
    public void buildEquationTest(){
        Equation equation = EquationUtils.buildEquation(10);
        System.out.println(equation + "->" +equation.getAnswer());
    }

    @Test
    public void buildEquationTest2(){
        for(int i = 0;i<10000;i++){
            Equation equation = EquationUtils.buildEquation(10);
            System.out.println((i+1)+". "+equation + "->" +equation.getAnswer());
        }
    }
}
