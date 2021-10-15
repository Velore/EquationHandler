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
    public void randomProperFractionTest(){
        for(int i = 0; i < 20 ;i++){
            System.out.println(RandomUtils.randomProperFraction(10));
        }
    }

    @Test
    public void randomMixedFractionTest(){
        for(int i = 0; i < 20 ;i++){
            System.out.println(RandomUtils.randomMixedFraction(10));
        }
    }

    @Test
    public void randomOperatorTest(){
        for(int i = 0; i < 20 ;i++){
            System.out.println(RandomUtils.randomOperator());
        }
    }

}
