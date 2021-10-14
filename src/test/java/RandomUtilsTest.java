import org.junit.Test;
import com.czh.utils.RandomUtils;

public class RandomUtilsTest {

    @Test
    public void randomIntTest(){
        for(int i = 0; i < 20 ;i++){
            System.out.println(RandomUtils.randomInt(10));
        }
    }

    @Test
    public void randomOperatorTest(){
        for(int i = 0; i < 20 ;i++){
            System.out.println(RandomUtils.randomOperator());
        }
    }

}
