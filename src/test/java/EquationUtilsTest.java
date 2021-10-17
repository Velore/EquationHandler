import com.czh.Equation;
import org.junit.Test;
import com.czh.utils.EquationUtils;

import java.util.ArrayList;

/**
 * @author chenzhuohong
 */
public class EquationUtilsTest {

    @Test
    public void checkMinusTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("4");
        list.add(" - ");
        list.add("5");
        EquationUtils.checkMinus(list);
        System.out.println(list);
    }

    @Test
    public void buildEquationTest(){
        Equation equation = EquationUtils.buildEquation(10);
        System.out.println(equation + "->" +equation.getAnswer());
    }

    @Test
    public void buildEquationTest2(){
        for(int i = 0;i<100;i++){
            Equation equation = EquationUtils.buildEquation(10);
            System.out.println(equation + "->" +equation.getAnswer());
        }
    }
}
