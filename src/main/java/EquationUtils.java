public class EquationUtils {

    /**
     * 构建四则运算方程
     * @param maxNum 方程内包含的最大的运算数
     * @return Equation
     */
    public static Equation buildEquation(int maxNum){
        Equation equation = new Equation();
        //操作符个数随机范围[1,4),即[1,3]
        int operatorNum = RandomUtils.randomInt(Equation.OPERATOR_MAX_NUM+1);
        //操作数个数 = 操作符个数 + 1
        //总数 = 操作符个数 * 2 + 1
        int index;
        for(index = 0; index<operatorNum*2+1 ;index++){
            //方程最开始push运算数
            //之后每push一个运算数，就push一个运算符
            if( index % 2 == 0 ){
                equation.push(String.valueOf(RandomUtils.randomInt(maxNum)));
            }else{
                //没有引入括号
                //没有解决连续除法
                //没有解决减法结果大于0
                equation.push(RandomUtils.randomOperator());
            }
        }
        equation.push("=");
        return equation;
    }

    /**
     * 检测两个方程是否相同
     * @param e Equation
     * @return 相同返回true
     */
    public boolean duplicateCheck(Equation e){
        return false;
    }

    /**
     * 计算四则运算方程
     * @return 计算结果可能为分数，用字符串表示
     */
    public static String calculate(){
        return null;
    }
}
