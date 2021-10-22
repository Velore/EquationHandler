package com.czh.utils;

import java.util.*;

/**
 * 算式计算工具类
 * @author chenzhuohong
 */
public class CalculateUtils {

    /**
     * 将字符串转换为逆波兰式
     * @param s 字符串
     * @return 逆波兰式String
     */
    public static String getPolishNotation(String s){
        //去除算式String中的空格,便于后续处理
        s = s.replaceAll(" ", "");
        //初始化一个栈和一个list
        //用于存储中间结果
        ArrayList<String> list = new ArrayList<>();
        //用于存储运算符
        Stack<String> stack = new Stack<>();
        //s.length()-1是为了去掉算式末尾的"="
        for (int i=0;i<s.length()-1;){
            if (ElementUtils.isInteger(s.charAt(i)+"")){
                //匹配运算数
                StringBuilder builder = new StringBuilder();
                do{
                    builder.append(s.charAt(i));
                    i++;
                }while (i<s.length()-1 && !ElementUtils.isOperator(s.charAt(i)));
                list.add(builder.toString());
            }else if((s.charAt(i)+"").matches("[-+×÷]")){
                //匹配运算符
                if (stack.isEmpty()){
                    stack.push(s.charAt(i)+"");
                    i++;
                    continue;
                }
                //上一个元素不为"(", 且当前运算符优先级小于上一个元素, 则将比这个运算符优先级大的其余运算符全部加入到队列中
                while (!stack.isEmpty()&&
                        !("(").equals(stack.lastElement())&&
                        !ElementUtils.comparePriority(s.charAt(i)+"",stack.lastElement())){
                    list.add(stack.pop());
                }
                stack.push(s.charAt(i)+"");
                i++;
            }else if(s.charAt(i)=='('){
                //遇到"("直接加入
                stack.push(s.charAt(i)+"");
                i++;
            }else if(s.charAt(i)==')'){
                //遇到")", 则寻找上一个"(", 然后把中间的值全部放入队列中
                while(!("(").equals(stack.lastElement())){
                    list.add(stack.pop());
                }
                //上述循环停止, 栈顶元素必为"("
                stack.pop();
                i++;
            }
        }
        //将栈中剩余元素加入到队列中
        while (!stack.isEmpty()){
            list.add(stack.pop());
        }
        //"|"为元素分隔符
        //用"|"将逆波兰式中运算数和运算符分割开,便于后续有关String的处理
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<list.size(); i++){
            if(i==list.size()-1){
                builder.append(list.get(i));
            }else {
                builder.append(list.get(i)).append("|");
            }
        }
        return builder.toString();
    }

    /**
     * 计算四则运算方程
     * @param str 四则运算方程
     * @return 计算结果用字符串表示
     */
    public static String calculate(String str){
        //获取四则运算方程的逆波兰式
        String s = getPolishNotation(str);
        Stack<String> stack = new Stack<>();
        try{
            for (int i = 0; i <s.length() ;) {
                if ((s.charAt(i) + "").matches("\\d")){
                    // 运算数的处理
                    StringBuilder builder = new StringBuilder();
                    do{
                        builder.append(s.charAt(i));
                        i++;
                        //匹配到运算符或者到达逆波兰式的字符串尾部就说明匹配完成该运算数
                    }while (i<s.length() && !ElementUtils.isOperator(s.charAt(i)));
                    stack.push(builder.toString());
                }else if ((s.charAt(i) + "").matches("[-+×÷]")){
                    //运算符的处理
                    String v1 = stack.pop();
                    String v2 = stack.pop();
                    // 计算结果
                    String res = simpleCalculate(v2, v1, (s.charAt(i)+""));
                    //若计算结果出现NaN,直接返回
                    if("NaN".equals(res)){
                        return "NaN";
                    }
                    //若计算结果不为NaN,则入栈
                    stack.push(res);
                    i++;
                }else if("|".equals(s.charAt(i)+"")){
                    //对逆波兰式中加入的元素分隔符不做处理
                    i++;
                }
            }
            return stack.pop();
        }catch (Exception e){
            e.printStackTrace();
        }
        //计算过程中出现异常即返回NaN(Not a Number)
        return "NaN";
    }

    /**
     * 求两个数的最大公约数
     * @param m m
     * @param n n
     * @return 最大公约数
     */
    public static int getMaxFactor(int m, int n){
        while(n>0){
            int temp = m%n;
            m = n;
            n = temp;
        }
        return m;
    }

    /**
     * 求两个数的最小公倍数
     * @param m m
     * @param n n
     * @return 最小公倍数
     */
    public static int getMinMultiple(int m, int n){
        int factor = getMaxFactor(m, n);
        return m*n/factor;
    }

    /**
     * 简单四则运算
     * 只包含一个运算符的运算
     * @param m 运算数1
     * @param n 运算数2
     * @param o 运算符
     * @return 运算结果 String
     */
    public static String simpleCalculate(String m, String n, String o){
        //两个运算数中出现一个NaN,计算结果为NaN,直接返回
        if("NaN".equals(m) || "NaN".equals(n)){
            return "NaN";
        }
        //根据传入的运算符,执行对应的简单四则运算
        switch (o){
            case "+":
                return plus(m, n);
            case "-":
                return minus(m, n);
            case "×":
                return multiply(m, n);
            case "÷":
                return divide(m, n);
            default:
                throw new IllegalArgumentException("无效运算符："+o);
        }
    }

    /**
     * 加法
     * @param m 运算数1
     * @param n 运算数2
     * @return 加法结果String
     */
    public static String plus(String m, String n){
        //两个运算数都是整数
        if(ElementUtils.isInteger(m) && ElementUtils.isInteger(n)){
            return String.valueOf(Integer.parseInt(m)+Integer.parseInt(n));
        }
        int[] m1 = ElementUtils.splitFraction(m);
        int[] n1 = ElementUtils.splitFraction(n);
        //result为计算结果的分子和分母
        // a/b + c/d = ( a*d + b*c )/( b*d )
        String result = (m1[0] * n1[1] + n1[0] * m1[1]) + "/" + (m1[1] * n1[1]);
        return ElementUtils.simplifyFraction(result);
    }

    /**
     * 减法
     * @param m 运算数1
     * @param n 运算数2
     * @return 减法结果String
     */
    public static String minus(String m, String n){
        //两个运算数都是整数
        if(ElementUtils.isInteger(m) && ElementUtils.isInteger(n)){
            return String.valueOf(Integer.parseInt(m)-Integer.parseInt(n));
        }
        //运算数非整数时, 全部转换为分数处理
        int[] m1 = ElementUtils.splitFraction(m);
        int[] n1 = ElementUtils.splitFraction(n);
        //result为计算结果的分子和分母
        // a/b - c/d = ( a*d - b*c )/( b*d )
        String result = (m1[0] * n1[1] - n1[0] * m1[1]) + "/" + (m1[1] * n1[1]);
        //简化结果分数后返回
        return ElementUtils.simplifyFraction(result);
    }

    /**
     * 乘法
     * @param m 运算数1
     * @param n 运算数2
     * @return 乘法结果String
     */
    public static String multiply(String m, String n){
        //两个运算数都是整数
        if(ElementUtils.isInteger(m) && ElementUtils.isInteger(n)){
            return String.valueOf(Integer.parseInt(m)*Integer.parseInt(n));
        }
        int[] m1 = ElementUtils.splitFraction(m);
        int[] n1 = ElementUtils.splitFraction(n);
        //result为计算结果的分子和分母
        // a/b * c/d = ( a*c )/( b*d )
        String result = (m1[0] * n1[0]) + "/" + (m1[1] * n1[1]);
        return ElementUtils.simplifyFraction(result);
    }

    /**
     * 除法
     * @param m 被除数
     * @param n 除数
     * @return String
     */
    public static String divide(String m, String n){
        //被除数为0, 结果为0
        if("0".equals(m)){
            return "0";
        }
        //除数为0,即分母为0,返回NaN
        if("0".equals(n)){
            return "NaN";
        }
        //两个运算数都是整数
        if(ElementUtils.isInteger(m) && ElementUtils.isInteger(n)){
            return ElementUtils.simplifyFraction(m + "/" + n);
        }
        int[] n1 = ElementUtils.splitFraction(m);
        int[] n2 = ElementUtils.splitFraction(n);
        //(a/b)/(c/d) = (a/b)*(d/c) = (a*d)/(b*c)
        return divide(String.valueOf(n1[0]*n2[1]), String.valueOf(n1[1]*n2[0]));
    }
}
