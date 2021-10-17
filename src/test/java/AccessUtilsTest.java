import com.czh.EquationHandler;
import com.czh.utils.AccessUtils;
import com.czh.utils.CalculateUtils;
import org.junit.Test;

import java.util.ArrayList;

public class AccessUtilsTest {

    @Test
    public void readEquationTest(){
        ArrayList<String> eList = AccessUtils.read(EquationHandler.EXERCISES_PATH);
        for(int i =0;i<10;i++){
            System.out.println(eList.get(i));
        }
    }

    @Test
    public void writeTest(){
        ArrayList<String> eList = AccessUtils.read(EquationHandler.EXERCISES_PATH);
        for(int i =0;i<10;i++){
            AccessUtils.write((i+1) +". "+ CalculateUtils.calculate(eList.get(i))+"\n", EquationHandler.ANSWER_PATH);
        }
    }
}
