
package com.czh.utils;

import com.czh.Equation;

import java.util.ArrayList;
import java.util.List;

/**
 * 对算式的处理工具类
 * @author chenzhuohong
 */
public class EquationUtils {

    /**
     * 在算式的元素list中添加括号
     * @param list 算式的元素list
     * @return 添加括号后的元素list
     */
    public static ArrayList<String> addBracket(ArrayList<String> list){
        int size = list.size();
        //至少需要一个运算符和两个运算数才能生成括号
        if(size >= 3){
            //只有在+和-上加括号才对算式有影响
            if("-".equals(list.get(size-2).trim())|| "+".equals(list.get(size-2).trim())){
                //如果要添加括号的位置已经存在括号，则不做处理
                //算式只有一个运算符时，也不做处理
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
     * 通过下标置换下标两边的子list;
     * 如: 2为下标元素:[1, 2, 3]->[3, 2, 1];
     * @param list 需要置换的list
     * @param index 下标
     * @return 置换后的list
     */
    public static ArrayList<String> reverseListByIndex(ArrayList<String> list, int index){
        //下标左边的子list
        List<String> leftList = list.subList(0, index);
        //下标右边的子list
        List<String> rightList = list.subList(index+1, list.size());
        //res:转换后的结果list
        ArrayList<String> res = new ArrayList<>(rightList);
        res.add(list.get(index));
        res.addAll(leftList);
        return res;
    }

    /**
     * 检查算式的减法运算是否大于0;
     * 若小于0，则交换减法的运算数;
     * 若算式形式为(a-b-c且a>b>c),则该方法无法检测出来;
     * 因此在生成运算符时,避免连续生成两个减号;
     * @param list 算式的元素list
     */
    public static void checkMinus(ArrayList<String> list){
        int index;
        for(index = 0;index<list.size();index++){
            if("-".equals(list.get(index).trim())){
                //减法左值元素的下标
                int li = index-1;
                //减法右值元素的下标
                int ri = index+1;
                //减法的左值元素list
                ArrayList<String> rightList;
                //减法的右值元素list
                ArrayList<String> leftList;
                leftList = getLeftSubElement(li, list);
                //算式从左边开始检查, 因此, 若减法的左值内仍包含减法, 不再重复检查
                rightList = getRightSubElement(ri, list);
                //若减法的右值内仍包含减法，则递归检查
                if(rightList.contains(" - ")){
                    checkMinus(rightList);
                }
                //若值的位数小于3,说明只有一个运算数,若大于等于3,说明至少有一个运算符
                String lv = (leftList.size()>=3)?CalculateUtils.calculate(transformToEquation(leftList)): leftList.get(0);
                String rv = (rightList.size()>=3)?CalculateUtils.calculate(transformToEquation(rightList)): rightList.get(0);
                //eList存放减法检查后的结果
                //创建eList时假设减法结果大于0, 因此复制list的值
                ArrayList<String> eList;
                if(ElementUtils.compareNum(lv, rv)){
                    eList = new ArrayList<>(leftList);
                    eList.add(" - ");
                    eList.addAll(rightList);
                }else{
                    //如果减法的两个运算数比较小于0, 说明计算会出现负数, 交换两运算数
                    eList = new ArrayList<>(rightList);
                    eList.add(" - ");
                    eList.addAll(leftList);
                }
                li -= leftList.size()-1;
                ri += rightList.size()-1;
                //将eList中的元素置换保存至原算式list
                for(int j = 0, i = li;i<=ri;i++, j++){
                    list.set(i, eList.get(j));
                }
            }
        }
    }

    /**
     * 获取算式元素中的左运算数(左子算式);
     * 如: a-b获取a, 此时a可以是任何复杂的运算数(子算式);
     * 如: (a*b+c)-d, 该方法返回(a*b+c);
     * @param index 算式元素中运算符左边第一位字符的下标
     * @param element 算式的元素list
     * @return 元素或子算式list
     */
    public static ArrayList<String> getLeftSubElement(int index, ArrayList<String> element){
        ArrayList<String> subElement = new ArrayList<>();
        while(index>=0){
            if(!"+".equals(element.get(index).trim()) && !"-".equals(element.get(index).trim())){
                if("(".equals(element.get(index))){
                    break;
                }
                if(")".equals(element.get(index))){
                    do{
                        subElement.add(0, element.get(index));
                        index--;
                    }while (index >0 && !"(".equals(element.get(index)));
                }
                if(index>=0){
                    subElement.add(0, element.get(index));
                }
                index--;
                continue;
            }
            break;
        }
        return subElement;
    }

    /**
     * 获取算式元素中的右运算数(右子算式);
     * 如: a-b获取b, 此时b可以是任何复杂的运算数(子算式);
     * 如: a-(b*c+d), 该方法返回(b*c+d);
     * @param index 算式元素中运算符右边第一位字符的下标
     * @param element 算式的元素list
     * @return 元素或子算式list
     */
    public static ArrayList<String> getRightSubElement(int index, ArrayList<String> element){
        ArrayList<String> subElement = new ArrayList<>();
        while(index< element.size()){
            if(!"+".equals(element.get(index).trim()) && !"-".equals(element.get(index).trim())){
                if(")".equals(element.get(index))){
                    break;
                }
                if("(".equals(element.get(index))){
                    do{
                        subElement.add(element.get(index));
                        index++;
                    }while (index < element.size() && !")".equals(element.get(index)));
                }
                if(index<element.size()){
                    subElement.add(element.get(index));
                }
                index++;
                continue;
            }
            break;
        }
        return subElement;
    }

    /**
     * 将元素list转换为可计算的算式String
     * @param list 元素list
     * @return 算式String
     */
    public static String transformToEquation(ArrayList<String> list){
        StringBuilder builder = new StringBuilder();
        for(String s : list){
            builder.append(s);
        }
        return builder.append("=").toString();
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
        String lastOperator = "+";
        for(index = 0; index<operatorNum*2+1 ;index++){
            //方程最开始push运算数
            //之后每push一个运算数, 就push一个运算符
            if( index % 2 == 0 ){
                    equation.addLast(RandomUtils.randomNum(maxNum));
                    //每push一个运算数, 就尝试能否添加一个括号
                    equation.setElementList(EquationUtils.addBracket(equation.getElementList()));
            }else{
                //若上一次添加的运算符为减号, 则避免本次添加的运算符仍为减号
                if("-".equals(lastOperator)){
                    String o;
                    do{
                        o = RandomUtils.randomOperator();
                        //循环获取直至运算符不是减号
                    }while (lastOperator.equals(o));
                    lastOperator = o;
                }else{
                    //若上次添加的运算符不是减号, 则随机获取运算符
                    lastOperator = RandomUtils.randomOperator();
                }
                //将运算符添加至算式的末尾
                equation.addLast(" "+lastOperator+" ");
            }
        }
        //检查算式的减法
        checkMinus(equation.getElementList());
        equation.addLast(" = ");
        //计算算式的结果并保存回自身
        //若算式的结果为NaN,则递归生成算式直至结果不为NaN
        String result = CalculateUtils.calculate(equation.toString());
        if("NaN".equals(result)){
            equation = buildEquation(maxNum);
            result = CalculateUtils.calculate(equation.toString());
        }
        equation.setAnswer(result);
        return equation;
    }
}
