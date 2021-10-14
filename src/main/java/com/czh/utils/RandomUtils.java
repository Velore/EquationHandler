package com.czh.utils;

/**
 * @author chenzhuohong
 */
public class RandomUtils {

    /**
     * @param n 随机数字的右边界
     * @return 生成随机数字(0,n)
     */
    public static int randomInt(int n) {
        return (int)(Math.random()*(n-1)+1);
    }

    /**
     * 随机生成四则运算符
     * 暂未加入括号
     * @return String 运算符
     */
    public static String randomOperator(){
        String[] operatorList = {"+", "-", "×", "÷"};
        return operatorList[(int)(Math.random()*4)];
    }

}
