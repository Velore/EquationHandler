package com.czh;

import com.czh.utils.AccessUtils;
import com.czh.utils.CalculateUtils;
import com.czh.utils.EquationUtils;

import java.util.ArrayList;
import java.util.Arrays;

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
     * 正确率写入路径
     */
    public static String GRADE_PATH = "./Grade.txt";

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

    /**
     * 根据输入生成算式和答案并写入文件
     * @param count 生成算式的数量
     * @param maxNum 生成算式中的最大整数
     */
    public static void generateEquationAndAnswer(int count, int maxNum){
        if(count <= 0 || maxNum <= 0){
            throw new IllegalArgumentException("请输入正确的参数");
        }
        list = generator(count, maxNum);
        System.out.println(list);
        for (int i = 0;i< list.size();i++) {
            AccessUtils.write((i+1)+". "+list.get(i)+"\n", EquationHandler.EXERCISES_PATH);
            AccessUtils.write((i+1)+". "+list.get(i).getAnswer()+"\n", EquationHandler.ANSWER_PATH);
        }
    }

    /**
     * 检查输入算式和答案的正确率
     * @param ePath 算式的路径
     * @param aPath 答案的路径
     */
    public static void checkAnswer(String ePath, String aPath){
        ArrayList<String> eList;
        ArrayList<String> aList;
        //存放答案正确的算式编号
        ArrayList<String> cList = new ArrayList<>();
        //存放答案错误的算式编号
        ArrayList<String> wList = new ArrayList<>();
        EXERCISES_PATH = ePath;
        ANSWER_PATH = aPath;
        try{
            eList = AccessUtils.read(ePath);
            aList = AccessUtils.read(aPath);
            if(eList.isEmpty() || aList.isEmpty()){
                System.out.println("算式或答案为空");
                System.exit(1);
            }
            for(int i = 0;i<aList.size();i++){
                if(aList.get(i).equals(CalculateUtils.calculate(eList.get(i)))){
                    cList.add((i+1)+"");
                }else {
                    wList.add((i+1)+"");
                }
            }
            AccessUtils.write("Correct :"+cList.size()+"(", GRADE_PATH);
            writeGrade(cList, GRADE_PATH);
            AccessUtils.write("Wrong :"+wList.size()+"(", GRADE_PATH);
            writeGrade(wList, GRADE_PATH);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 写入算式对应答案的正确或错误的编号
     * @param grade 编号list
     * @param path 写入路径
     */
    public static void writeGrade(ArrayList<String> grade, String path){
        for(int i = 0;i<grade.size();i++){
            if(i==grade.size()-1){
                AccessUtils.write(grade.get(i)+")\n", path);
            }else {
                AccessUtils.write(grade.get(i + 1)+", ", path);
            }
        }
    }

    public static void main(String[] args) {
        try{
            for(int i = 0;i<args.length-1;i++){
                if("-n".equals(args[i])){
                    count = Integer.parseInt(args[i+1]);
                }
                if("-r".equals(args[i])){
                    maxNum = Integer.parseInt(args[i+1]);
                }
                if("-e".equals(args[i])){
                    EXERCISES_PATH = args[i+1];
                }
                if("-a".equals(args[i])){
                    ANSWER_PATH = args[i+1];
                }
            }
            for (String arg : args) {
                if ("-n".equals(arg) || "-r".equals(arg)) {
                        generateEquationAndAnswer(count, maxNum);
                        break;
                } else {
                    checkAnswer(EXERCISES_PATH, ANSWER_PATH);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
