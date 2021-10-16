import com.czh.Equation;
import org.junit.Test;
import com.czh.utils.EquationUtils;

/**
 * @author chenzhuohong
 */
public class EquationUtilsTest {

    @Test
    public void buildEquationTest(){
        Equation equation = EquationUtils.buildEquation(10);
        System.out.println(equation + "->" +equation.getAnswer());
    }

    @Test
    public void buildEquationTest2(){
        for(int i = 0;i<10;i++){
            Equation equation = EquationUtils.buildEquation(10);
            System.out.println(equation + "->" +equation.getAnswer());
        }
    }
}
