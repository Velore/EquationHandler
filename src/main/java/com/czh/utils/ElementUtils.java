package com.czh.utils;

/**
 * 对运算数和运算符的判断
 * @author chenzhuohong
 */
public class ElementUtils {

    /**
     * 提取分数字符串的分子和分母
     * 若参数为负数, 则”/“的下标为-, 抛出越界-1异常
     * @param s 分数字符串
     * @return int数组
     */
    public static int[] splitFraction(String s){
        int[] nums = new int[2];
        //传入的参数是个整数,则分子为参数,分母为1
        if(ElementUtils.isInteger(s)){
            nums[0] = Integer.parseInt(s);
            nums[1] = 1;
        }else{
            //分数分号的下标
            int index1 = s.indexOf("/");
            //提取分母
            nums[1] = Integer.parseInt(s.substring(index1+1));
            //提取分子
            if(s.contains("'")){
                //带分数符号的下标
                int index2 = s.indexOf("'");
                //带分数分子
                //a'b/c = (a*c+b)/c
                nums[0] = Integer.parseInt(s.substring(0, index2))*nums[1]
                        +Integer.parseInt(s.substring(index2+1, index1));
            }else{
                //真分数分子
                nums[0] = Integer.parseInt(s.substring(0, index1));
            }
        }
        return nums;
    }

    /**
     * 化简分数
     * @param s 分数字符串
     * @return 最简分数的表达式
     */
    public static String simplifyFraction(String s){
        int[] result = ElementUtils.splitFraction(s);
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
     * 是运算数还是运算符
     * @return 是运算数返回true
     */
    public static boolean isOperator(char c){
        //'|'本身无意义, 看作运算符方便计算
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
            if(!(s.charAt(i)+"").matches("\\d")){
                return false;
            }
        }
        return true;
    }


    /**
     * 对比两个数的大小
     * @param s1 String运算数1
     * @param s2 String运算数2
     * @return s1>s2,返回true
     */
    public static boolean compareNum(String s1, String s2){
        //两个运算数都是负数，提取正数部分，正数部分越小，原运算数越大
        if(s1.charAt(0)=='-' && s2.charAt(0)=='-'){
            s1 = s1.substring(1);
            s2 = s2.substring(1);
            int[] l1 = splitFraction(s1);
            int[] l2 = splitFraction(s2);
            return l1[0]*l2[1] < l2[0]*l1[1];
        }
        //正数大于负数
        if(s1.charAt(0)=='-' && s2.charAt(0)!='-'){
            return false;
        }
        //正数大于负数
        if(s1.charAt(0)!='-' && s2.charAt(0)=='-'){
            return true;
        }
        //两正运算数数比较
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
            //加号减号优先级低
            case "+":
            case "-":
                return 1;
            //乘号除号优先级高
            case "×":
            case "÷":
                return 2;
            default:
                throw new IllegalArgumentException("无效运算符");
        }
    }
}
