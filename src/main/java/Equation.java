import java.util.ArrayList;

public class Equation {

    /**
     * 方程中运算符个数
     */
    public final static int OPERATOR_MAX_NUM = 3;

    /**
     * 四则运算方程
     * 存储形式:栈
     */
    private final ArrayList<String> equationStack;

    public Equation(){
        this.equationStack = new ArrayList<>();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (String s : this.equationStack) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 元素压入栈
     * @param s String
     */
    public void push(String s){
        this.equationStack.add(this.equationStack.size(), s);
    }

    /**
     * 栈弹出元素
     * @return String
     */
    public String pop(){
        assert this.equationStack.size() !=0 : "栈为空";
        return this.equationStack.remove(this.equationStack.size()-1);
    }

}
