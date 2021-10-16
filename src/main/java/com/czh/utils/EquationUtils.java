
package com.czh.utils;

import com.czh.Equation;

import java.util.ArrayList;

/**
 * @author chenzhuohong
 */
public class EquationUtils {

    /**
     * 添加括号
     * @param list 算式的元素list
     * @return 修改后的list
     */
    public static ArrayList<String> addBracket(ArrayList<String> list){
        int size = list.size();
        //至少需要一个运算符和两个运算数才能生成括号
        if(size>=3){
            //只有在+和-上加括号才对算式有影响
            if( " - ".equals(list.get(size-2))|| " + ".equals(list.get(size-2))){
                //如果要添加括号的位置已经存在括号，则不做处理
                if(")".equals(list.get(size-3)) || size == 3){
                    return list;
                }
                list.add(size-3, "(");
                list.add(")");
            }
        }
        return list;
    }

    /**
     * 检查算式的减法运算是否大于0
     * 若小于0，则交换减法的运算数
     * @param list 算式
     */
    public static void checkMinus(ArrayList<String> list){
        int index;
        for(index = 0;index<list.size();index++){
            if(" - ".equals(list.get(index))){
                //区分a-b和(..a)-b
                String leftN = (")".equals(list.get(index-1)))?list.get(index-2):list.get(index-1);
                //区分a-b和a-(b..)
                String rightN = ("(".equals(list.get(index+1)))?list.get(index+2):list.get(index+1);
                if(!CalculateUtils.compareNum(leftN, rightN)){
                    if(")".equals(list.get(index-1))){
                        list.set(index-2, rightN);
                    }else{
                        list.set(index-1, rightN);
                    }
                    if("(".equals(list.get(index+1))){
                        list.set(index+2, leftN);
                    }else{
                        list.set(index+1, leftN);
                    }
                }
            }
        }
    }

    /**
     * 构建四则运算方程
     * @param maxNum 方程内包含的最大的运算数
     * @return com.czh.Equation
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
                    equation.addLast(RandomUtils.randomNum(maxNum));
                    equation.setElementList(EquationUtils.addBracket(equation.getElementList()));
            }else{
                equation.addLast(" "+RandomUtils.randomOperator()+" ");
            }
        }
        checkMinus(equation.getElementList());
        equation.addLast(" = ");
        equation.setAnswer(CalculateUtils.calculate(equation.toString()));
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

}
