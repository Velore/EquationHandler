import org.junit.Test;

public class EquationUtilsTest {

    @Test
    public void buildEquationTest(){
        Equation equation = EquationUtils.buildEquation(10);
        System.out.println(equation);
    }

    @Test
    public void buildEquationTest2(){
        for(int i = 0;i<10;i++){
            Equation equation = EquationUtils.buildEquation(10);
            System.out.println(equation);
        }
    }
}
