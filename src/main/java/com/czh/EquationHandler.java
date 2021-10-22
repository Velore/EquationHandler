package com.czh;

import com.czh.utils.AccessUtils;
import com.czh.utils.CalculateUtils;
import com.czh.utils.DuplicateUtils;
import com.czh.utils.EquationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public static int maxNum;

    /**
     * 算式list
     */
    public static ArrayList<Equation> list = new ArrayList<>();

    /**
     * 主启动方法
     * @param args 参数
     */
    public static void start(String[] args){
        try{
            String str = "参数应包含'-e'或者'-r'其中一个\n若为生成算式和答案,使用-r指定算式的最大整数,-n指定生成算式的数量,若不输入-n,则默认生成10条算式\n" +
                    "若为检查题目答案的正确率,使用-e指定算式的路径,使用-a指定答案的路径,两者缺一不可";
            List<String> argList = new ArrayList<>(Arrays.asList(args));
            if(!argList.contains("-e") && !argList.contains("-r")){
                throw new IllegalArgumentException(str);
            }
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
                if ("-r".equals(arg)) {
                    generateEquationAndAnswer(count, maxNum);
                    break;
                }else if("-e".equals(arg)){
                    checkAnswer(EXERCISES_PATH, ANSWER_PATH);
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 根据参数生成算式
     * @param count 生成算式的数量
     * @param maxNum 生成算式中最大的整数
     * @return 算式list
     */
    public static ArrayList<Equation> generator(int count, int maxNum){
        //用于鉴别算式是否重复的HashMap
        HashMap<String, String> eList = new HashMap<>(2*count);
        ArrayList<Equation> list = new ArrayList<>();
        //因为题库初始化后是空的，所以首先生成一道题目并放入题库中
        Equation equation = EquationUtils.buildEquation(maxNum);
        list.add(equation);
        eList.put(equation.getAnswer(), equation.toString());
        System.out.println(list.size() + ":" + equation);
        for(int i = 1;i<count; i++){
            boolean isDuplicate;
            Equation newEquation;
            do{
                //新生成一道题目
                newEquation = EquationUtils.buildEquation(maxNum);
                //取出已经存放在题库中的每道题目一一进行比对
                if(eList.containsKey(newEquation.getAnswer())){
                    isDuplicate = DuplicateUtils.duplicateCheck(newEquation.toString(), eList.get(newEquation.getAnswer()));
                }else{
                    isDuplicate = false;
                }
            }while (isDuplicate);
            list.add(newEquation);
            eList.put(newEquation.getAnswer(), newEquation.toString());
            System.out.println(list.size() + ":" + newEquation);
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
        AccessUtils.write("", EquationHandler.EXERCISES_PATH, false);
        AccessUtils.write("", EquationHandler.ANSWER_PATH, false);
        for (int i = 0;i< list.size();i++) {
            AccessUtils.write((i+1)+". "+list.get(i)+"\n", EquationHandler.EXERCISES_PATH, true);
            AccessUtils.write((i+1)+". "+list.get(i).getAnswer()+"\n", EquationHandler.ANSWER_PATH, true);
        }
    }

    /**
     * 检查输入算式和答案的正确率
     * @param ePath 算式的路径
     * @param aPath 答案的路径
     */
    public static void checkAnswer(String ePath, String aPath){
        //存放读取算式的list
        ArrayList<String> eList;
        //存放读取答案的list
        ArrayList<String> aList;
        //存放答案正确的算式编号
        ArrayList<String> cList = new ArrayList<>();
        //存放答案错误的算式编号
        ArrayList<String> wList = new ArrayList<>();
        //覆盖算式所在的路径
        EXERCISES_PATH = ePath;
        //覆盖答案所在的路径
        ANSWER_PATH = aPath;
        try{
            eList = AccessUtils.read(ePath);
            aList = AccessUtils.read(aPath);
            if(eList.isEmpty() || aList.isEmpty()){
                System.out.println("算式或答案为空");
                System.exit(1);
            }
            // 对于每个对应的算式和答案
            // 计算算式的答案后对比并写入cList或wList
            for(int i = 0;i<aList.size();i++){
                if(aList.get(i).equals(CalculateUtils.calculate(eList.get(i)))){
                    cList.add((i+1)+"");
                }else {
                    wList.add((i+1)+"");
                }
            }
            AccessUtils.write("", GRADE_PATH, false);
            AccessUtils.write("Correct :", GRADE_PATH, true);
            writeGrade(cList, GRADE_PATH);
            AccessUtils.write("Wrong :", GRADE_PATH, true);
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
        AccessUtils.write(grade.size()+"(", path, true);
        for (int i = 0 ; i<grade.size() ; i++) {
            AccessUtils.write(grade.get(i),  path, true);
            if(i!=grade.size()-1){
                AccessUtils.write(", ",  path, true);
            }
        }
        AccessUtils.write(")\n", path, true);
    }

    public static void main(String[] args) {
        start(args);
    }
}
