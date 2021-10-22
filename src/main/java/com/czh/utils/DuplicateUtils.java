package com.czh.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 检查算式重复的类
 * @author chenzhuohong
 */
public class DuplicateUtils {

    /**
     * 检测两个算式是否相同
     * @param s1 算式1
     * @param s2 算式2
     * @return 相同返回true
     */
    public static boolean duplicateCheck(String s1, String s2){
        List<String> list1 = EquationUtils.transformStringToList(CalculateUtils.getPolishNotation(s1));
        List<String> list2 = EquationUtils.transformStringToList(CalculateUtils.getPolishNotation(s2));
        if(list1.size() != list2.size()){
            return false;
        }
        try{
            for(int i = 0;i<list1.size();i++){
                ArrayList<String> l1 = new ArrayList<>();
                ArrayList<String> l2 = new ArrayList<>();
                int index1 = 0;
                int index2 = 0;
                for(int j = 0;j< list1.size();j++){
                    if(list1.get(j).matches("[-+×÷]")){
                        index1 = j;
                        break;
                    }
                }
                for(int j = 0;j< list2.size();j++){
                    if(list2.get(j).matches("[-+×÷]")){
                        index2 = j;
                        break;
                    }
                }
                for(int j = 0 ; j < 3 ; j++){
                    l1.add(0, list1.get(index1-j));
                    l2.add(0, list2.get(index2-j));
                }
                if(!simpleDuplicateCheck(l1, l2)){
                    return false;
                }
                String res1 = CalculateUtils.simpleCalculate(l1.get(0), l1.get(1), l1.get(2));
                String res2 = CalculateUtils.simpleCalculate(l2.get(0), l2.get(1), l2.get(2));
                for(int j = 0 ; j < 3 ; j++){
                    list1.remove(index1-2);
                    list2.remove(index2-2);
                }
                list1.add(index1-2, res1);
                list2.add(index2-2, res2);
                if(list1.size()==1){
                    return list1.get(0).equals(list2.get(0));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(s1);
            System.out.println(s2);
        }
        return false;
    }

    /**
     * 比较两个简单算式list是否重复
     * list的格式为[运算数1, 运算数2, 运算符]
     * @param list1 简单算式1
     * @param list2 简单算式2
     * @return 若算式重复，返回true
     */
    public static boolean simpleDuplicateCheck(ArrayList<String> list1, ArrayList<String> list2){
        boolean duplicate = false;
        String o1 = list1.get(list1.size()-1).trim();
        String o2 = list2.get(list2.size()-1).trim();
        if(!o1.equals(o2)){
            return false;
        }
        switch (o1){
            case "+":
            case "×":
                if(list1.get(0).equals(list2.get(0)) &&list1.get(1).equals(list2.get(1))){
                    duplicate = true;
                }
                if(list1.get(0).equals(list2.get(1)) &&list1.get(1).equals(list2.get(0))){
                    duplicate = true;
                }
                break;
            case "-":
            case "÷":
                if(list1.get(0).equals(list2.get(0)) && list1.get(1).equals(list2.get(1))){
                    duplicate = true;
                }
                break;
            default:
                throw new IllegalArgumentException("无效运算符:"+o1);
        }
        return duplicate;
    }

    public static void main(String[] args) {
        String s1 = "2÷5+3-2 = ";
        String s2 = "3+2÷5-2 = ";
        System.out.println(duplicateCheck(s1, s2));
    }
}
