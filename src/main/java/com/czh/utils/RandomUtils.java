package com.czh.utils;

/**
 * @author chenzhuohong
 */
public class RandomUtils {

    /**
     * 生成随机整数
     * @param n 随机数的右边界
     * @return 生成随机整数(0,n)
     */
    public static int randomInt(int n) {
        return (int)(Math.random()*(n-1)+1);
    }

    /**
     * 随机真分数
     * @param n 分母的最大值
     * @return String
     */
    public static String randomProperFraction(int n){
        int[] pref = new int[2];
        pref[1] = randomInt(n+1);
        pref[0] = randomInt(pref[1]);
        return CalculateUtils.reduceFraction(pref[0] + "/" + pref[1]);
    }

    /**
     * 随机带分数
     * @param n 分数的最大值，分母的最大值
     * @return String
     */
    public static String randomMixedFraction(int n){
        int[] f = new int[3];
        f[1] = randomInt(n+1);
        f[0] = randomInt(f[1]);
        f[2] = randomInt(n);
        return CalculateUtils.reduceFraction(f[2] + "'" + f[0] + "/" + f[1]);
    }

    /**
     * 生成随机数(整数,真分数,带分数)
     * @param n 随机整数的最大值(必须大于0)
     * @return 随机数
     */
    public static String randomNum(int n){
        assert n > 0 : "参数必须大于0";
        //0:真分数,1:带分数,3或4:整数
        int numType = (int)(Math.random()*4);
        switch (numType){
            case 0:
                return randomProperFraction(n);
            case 1:
                return randomMixedFraction(n);
            default:
                return String.valueOf(randomInt(n));
        }
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
