package com.czh;

import java.util.ArrayList;

/**
 * @author chenzhuohong
 */
public class Equation {

    /**
     * 算式中运算符个数
     */
    public final static int OPERATOR_MAX_NUM = 3;

    /**
     * 四则运算算式的元素list
     */
    private ArrayList<String> elementList;

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

    public void setElementList(ArrayList<String> list){
        this.elementList = list;
    }

    public String getAnswer(){
        return this.answer;
    }

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
