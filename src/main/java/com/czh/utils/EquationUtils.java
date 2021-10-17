
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
     * @return 修改后的list
     */
    public static ArrayList<String> addBracket(ArrayList<String> list){
        int size = list.size();
        //至少需要一个运算符和两个运算数才能生成括号
        if(size >= 3){
            //只有在+和-上加括号才对算式有影响
            if("-".equals(list.get(size-2).trim())|| "+".equals(list.get(size-2).trim())){
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
     * 通过下标置换原list下标两边的子list
     * 2为下标元素:[1, 2, 3]->[3, 2, 1]
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
     * 检查算式的减法运算是否大于0
     * 若小于0，则交换减法的运算数
     * @param list 算式的元素list
     */
    public static void checkMinus(ArrayList<String> list){
        int index;
        for(index = 0;index<list.size();index++){
            if("-".equals(list.get(index).trim())){
                //减法左值元素的下标
                int li;
                //减法右值元素的下标
                int ri;
                //减法的左值元素list
                ArrayList<String> rightList = new ArrayList<>();
                //减法的右值元素list
                ArrayList<String> leftList = new ArrayList<>();
                for(li = index-1;li>=0;){
                    //将减法左边的值元素整个取出
                    // 即a*(b+c)-d取出a*(b+c)
                    // 或(b+c)*a-d取出(b+c)*a
                    if(!"+".equals(list.get(li).trim()) || !"-".equals(list.get(li).trim())){
                        if("(".equals(list.get(li))){
                            break;
                        }
                        //若左值内存在右括号，则说明减法在这对括号之外，将括号内的值全部取出
                        //如：(...)-b
                        if(")".equals(list.get(li))){
                            do{
                                leftList.add(0, list.get(li));
                                li--;
                            }while (!"(".equals(list.get(li)));
                        }
                        leftList.add(0, list.get(li));
                        li--;
                        continue;
                    }
                    break;
                }
                //若减法的左值内仍包含减法，则递归检查
                if(leftList.contains(" - ")){
                    checkMinus(leftList);
                }
                for(ri = index+1;ri<list.size();){
                    if(!"+".equals(list.get(ri).trim()) || !"-".equals(list.get(ri).trim())){
                        if(")".equals(list.get(ri))){
                            break;
                        }
                        //若右值内存在左括号，则说明减法在这对括号之外，将括号内的值全部取出
                        //如：a-(...)
                        if("(".equals(list.get(ri))){
                            do{
                                rightList.add(list.get(ri));
                                ri++;
                            }while (!")".equals(list.get(ri)));
                        }
                        rightList.add(list.get(ri));
                        ri++;
                        continue;
                    }
                    break;
                }
                //若减法的右值内仍包含减法，则递归检查
                if(rightList.contains(" - ")){
                    checkMinus(rightList);
                }
                //若值的位数小于3,说明只有一个运算数,若大于等于3,说明至少有一个运算符
                String lv = (leftList.size()>=3)?CalculateUtils.calculate(transformToEquation(leftList)): leftList.get(0);
                String rv = (rightList.size()>=3)?CalculateUtils.calculate(transformToEquation(rightList)): rightList.get(0);
                //eList存放减法检查后的结果
                ArrayList<String> eList;
                if(ElementUtils.compareNum(lv, rv)){
                    eList = new ArrayList<>(leftList);
                    eList.add(" - ");
                    eList.addAll(rightList);
                }else{
                    eList = new ArrayList<>(rightList);
                    eList.add(" - ");
                    eList.addAll(leftList);
                }
                li += 1;
                ri -= 1;
                //将eList中的元素置换保存至原算式list
                for(int j = 0, i = li;i<=ri;i++, j++){
                    list.set(i, eList.get(j));
                }
            }
        }
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
        //检查算式的减法
        checkMinus(equation.getElementList());
        equation.addLast(" = ");
        //计算算式的结果并保存回自身
        equation.setAnswer(CalculateUtils.calculate(equation.toString()));
        return equation;
    }

//    /**
//     * 检测两个方程是否相同
//     * @param e Equation
//     * @return 相同返回true
//     */
//    public boolean duplicateCheck(Equation e){
//        return false;
//    }

}
