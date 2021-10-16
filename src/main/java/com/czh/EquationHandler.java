package com.czh;

import com.czh.utils.AccessUtils;
import com.czh.utils.EquationUtils;

import java.util.ArrayList;

/**
 * 主启动类
 * @author chenzhuohong
 */
public class EquationHandler {

    /**
     * 写入路径
     */
    public static String EXERCISES_PATH = "./Exercises.txt";

    /**
     * 读入路径
     */
    public static String ANSWER_PATH = "./Answers.txt";

    /**
     * -e的参数
     */
    public static String E_PATH = "";

    /**
     * -a的参数
     */
    public static String A_PATH = "";

    /**
     * 生成算式的数量
     */
    public static int count = 10;

    /**
     * 算式中最大的整数
     */
    public static int maxNum = 10;

    /**
     * 算式list
     */
    public static ArrayList<Equation> list = new ArrayList<>();

    /**
     * 根据参数生成算式
     * @param count 生成算式的数量
     * @param maxNum 生成算式中最大的整数
     * @return 算式list
     */
    public static ArrayList<Equation> generator(int count, int maxNum){
        ArrayList<Equation> list = new ArrayList<>();
        for(int i = 0;i<count; i++){
            list.add(EquationUtils.buildEquation(maxNum));
        }
        return list;
    }

    public static void main(String[] args) {
        count = 10000;
        maxNum = 10;
//        try{
//            for(int i = 0;i<args.length;i++){
//                if("-n".equals(args[i])){
//                    count = Integer.parseInt(args[i+1]);
//                }
//                if("-r".equals(args[i])){
//                    maxNum = Integer.parseInt(args[i+1]);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        if(count <= 0 || maxNum <= 0){
//            throw new IllegalArgumentException("请输入正确的参数");
//        }
        list = generator(count, maxNum);
        System.out.println(list);
        for (int i = 0;i< list.size();i++) {
            AccessUtils.write((i+1)+". "+list.get(i), EquationHandler.EXERCISES_PATH);
//            AccessUtils.write((i+1)+". "+list.get(i).getAnswer(), EquationHandler.ANSWER_PATH);
        }
    }

}
