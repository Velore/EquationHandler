package com.czh.utils;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author chenzhuohong
 */
public class CalculateUtils {

    /**
     * 将字符串转换为逆波兰式
     * @param s 字符串
     * @return 逆波兰式String
     */
    public static String getPolishNotation(String s){
        s = s.replaceAll(" ", "");
        ArrayList<String> list = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        //s.length()-1是为了去掉方程末尾的"="
        for (int i=0;i<s.length()-1;){
            if (isInteger(s.charAt(i)+"")){
                //匹配运算数
                StringBuilder builder = new StringBuilder();
                do{
                    builder.append(s.charAt(i));
                    i++;
                }while (i<s.length()-1 && !isOperator(s.charAt(i)));
                list.add(builder.toString());
            }else if((s.charAt(i)+"").matches("[-+×÷]")){
                //匹配运算符
                if (stack.isEmpty()){
                    stack.push(s.charAt(i)+"");
                    i++;
                    continue;
                }
                //上一个元素不为"(",且当前运算符优先级小于上一个元素,则将比这个运算符优先级大的元素全部加入到队列中
                while (!stack.isEmpty()&&!("(").equals(stack.lastElement())&&!comparePriority(s.charAt(i)+"",stack.lastElement())){
                    list.add(stack.pop());
                }
                stack.push(s.charAt(i)+"");
                i++;
            }else if(s.charAt(i)=='('){
                //遇到"("无条件加入
                stack.push(s.charAt(i) + "");
                i++;
            }else if(s.charAt(i)==')'){
                //遇到")"，则寻找上一个"("，然后把中间的值全部放入队列中
                while(!("(").equals(stack.lastElement())){
                    list.add(stack.pop());
                }
                //上述循环停止，这栈顶元素必为"("
                stack.pop();
                i++;
            }
        }
        //将栈中剩余元素加入到队列中
        while (!stack.isEmpty()){
            list.add(stack.pop());
        }
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
     * 对比两个数的大小
     * @param s1 String运算数1
     * @param s2 String运算数2
     * @return s1>s2,返回true
     */
    public static boolean compareNum(String s1, String s2){
        int[] l1 = splitFraction(s1);
        int[] l2 = splitFraction(s2);
        return l1[0]*l2[1] > l2[0]*l1[1];
    }

    /**
     * 比较运算符的优先级
     * @param o1 运算符1
     * @param o2 运算符2
     * @return o1>o2,返回true
     */
    public static boolean comparePriority(String o1,String o2){
        return getPriorityValue(o1)>getPriorityValue(o2);
    }

    /**
     * 获得运算符的优先级
     * @param str 运算符
     * @return int
     */
    public static int getPriorityValue(String str) {
        switch(str.trim()){
            case "+":
            case "-":
                return 1;
            case "×":
            case "÷":
                return 2;
            default:
                throw new IllegalArgumentException("无效运算符");
        }
    }

    /**
     * 计算四则运算方程
     * @param str 四则运算方程
     * @return 计算结果可能为分数，用字符串表示
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
                    }while (i<s.length() && !isOperator(s.charAt(i)));
                    stack.push(builder.toString());
                }else if ((s.charAt(i) + "").matches("[-+×÷]")){
                    //运算符的处理
                    String k1 = stack.pop();
                    String k2 = stack.pop();
                    // 计算结果
                    String res = simpleCalculate(k2, k1, (s.charAt(i) + ""));
                    stack.push(res+"");
                    i++;
                }else if("|".equals(s.charAt(i)+"")){
                    //对分隔符不做处理
                    i++;
                }
            }
            return stack.pop();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "NaN";
    }

    /**
     * 是运算数还是运算符
     * @return 是运算数返回true
     */
    public static boolean isOperator(char c){
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '(' || c == ')' || c == '|';
    }

    /**
     * 判断字符串是否为纯数字组成
     * @param s String
     * @return 是则返回true
     */
    public static boolean isInteger(String s){
        for(int i = 0;i<s.length();i++){
            //逐位检查
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * 提取分数字符串的分子和分母
     * @param s 分数字符串
     * @return int数组
     */
    public static int[] splitFraction(String s){
        int[] nums = new int[2];
        //传入的参数是个整数,则分子为参数,分母为1
        if(CalculateUtils.isInteger(s)){
            nums[0] = Integer.parseInt(s);
            nums[1] = 1;
        }else{
            //提取分母
            nums[1] = Integer.parseInt(s.substring(s.indexOf("/")+1));
            //提取分子
            if(s.contains("'")){
                //带分数分子
                //a'b/c = (a*c+b)/c
                nums[0] = Integer.parseInt(s.substring(0, s.indexOf("'")))*nums[1]
                        +Integer.parseInt(s.substring(s.indexOf("'")+1, s.indexOf("/")));
            }else{
                //真分数分子
                nums[0] = Integer.parseInt(s.substring(0, s.indexOf("/")));
            }
        }
        return nums;
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
     * @param m 运算数1
     * @param n 运算数2
     * @param o 运算符
     * @return 运算结果 String
     */
    public static String simpleCalculate(String m, String n, String o){
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
     * 化简分数
     * @param s 分数字符串
     * @return 最简分数的表达式
     */
    public static String reduceFraction(String s){
        int[] result = CalculateUtils.splitFraction(s);
        if((result[0]%result[1])==0){
            return String.valueOf(result[0]/result[1]);
        }
        //计算分子和分母的最大公约数
        int maxFactor = CalculateUtils.getMaxFactor(result[0], result[1]);
        //化简分子
        result[0] /= maxFactor;
        //化简分母
        result[1] /= maxFactor;
        if(result[0]>result[1]){
            //分子大于分母,获取余数reminder
            int reminder = result[0]%result[1];
            return (result[0]-reminder)/result[1] + "'" + reminder + "/" + result[1] ;
        }
        return result[0] + "/" + result[1];
    }

    /**
     * 加法
     * @param m 运算数1
     * @param n 运算数2
     * @return 加法结果String
     */
    public static String plus(String m, String n){
        //两个运算数都是整数
        if(isInteger(m) && isInteger(n)){
            return String.valueOf(Integer.parseInt(m)+Integer.parseInt(n));
        }
        int[] m1 = splitFraction(m);
        int[] n1 = splitFraction(n);
        //result为计算结果的分子和分母
        // a/b + c/d = ( a*d + b*c )/( b*d )
        String result = (m1[0] * n1[1] + n1[0] * m1[1]) + "/" + (m1[1] * n1[1]);
        return reduceFraction(result);
    }

    /**
     * 减法
     * @param m 运算数1
     * @param n 运算数2
     * @return 减法结果String
     */
    public static String minus(String m, String n){
        //两个运算数都是整数
        if(isInteger(m) && isInteger(n)){
            return String.valueOf(Integer.parseInt(m)-Integer.parseInt(n));
        }
        int[] m1 = splitFraction(m);
        int[] n1 = splitFraction(n);
        //result为计算结果的分子和分母
        // a/b - c/d = ( a*d - b*c )/( b*d )
        String result = (m1[0] * n1[1] - n1[0] * m1[1]) + "/" + (m1[1] * n1[1]);
        return reduceFraction(result);
    }

    /**
     * 乘法
     * @param m 运算数1
     * @param n 运算数2
     * @return 乘法结果String
     */
    public static String multiply(String m, String n){
        //两个运算数都是整数
        if(isInteger(m) && isInteger(n)){
            return String.valueOf(Integer.parseInt(m)*Integer.parseInt(n));
        }
        int[] m1 = splitFraction(m);
        int[] n1 = splitFraction(n);
        //result为计算结果的分子和分母
        // a/b * c/d = ( a*c )/( b*d )
        String result = (m1[0] * n1[0]) + "/" + (m1[1] * n1[1]);
        return reduceFraction(result);
    }

    /**
     * 除法
     * @param m 被除数
     * @param n 除数
     * @return String
     */
    public static String divide(String m, String n){
        if("0".equals(m)){
            return "0";
        }
        if("0".equals(n)){
            throw new IllegalArgumentException("除数不能为0");
        }
        //两个运算数都是整数
        if(isInteger(m) && isInteger(n)){
            return reduceFraction(m + "/" + n);
        }
        int[] n1 = splitFraction(m);
        int[] n2 = splitFraction(n);
        //(a/b)/(c/d) = (a/b)*(d/c) = (a*d)/(b*c)
        return divide(String.valueOf(n1[0]*n2[1]), String.valueOf(n1[1]*n2[0]));
    }
}
