package com.czh;

import java.util.ArrayList;

/**
 * 算式实体类;
 * 算式的运算数和运算符都称为算式的元素;
 * @author chenzhuohong
 */
public class Equation {

    /**
     * 算式中运算符个数
     */
    public final static int OPERATOR_MAX_NUM = 3;

    /**
     * 四则运算算式的元素list
     * 运算数和运算符
     */
    private ArrayList<String> elementList;

    /**
     * 算式的答案
     */
    private String answer;

    public Equation(){
        this.elementList = new ArrayList<>();
    }

    /**
     * 获取算式的元素List
     * @return ArrayList<String>
     */
    public ArrayList<String> getElementList(){
        return this.elementList;
    }

    /**
     * 设置算式的元素list
     * @param list ArrayList
     */
    public void setElementList(ArrayList<String> list){
        this.elementList = list;
    }

    /**
     * 获取算式的答案
     * @return String
     */
    public String getAnswer(){
        return this.answer;
    }

    /**
     * 写入算式的答案
     * @param answer String
     */
    public void setAnswer(String answer){
        this.answer = answer;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (String s : this.elementList) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 将元素放入list的最后
     * @param s String
     */
    public void addLast(String s){
        this.elementList.add(this.elementList.size(), s);
    }
}
