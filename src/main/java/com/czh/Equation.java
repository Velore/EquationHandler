package com.czh;

import java.util.ArrayList;

/**
 * @author chenzhuohong
 */
public class Equation {

    /**
     * 方程中运算符个数
     */
    public final static int OPERATOR_MAX_NUM = 3;

    /**
     * 四则运算方程
     */
    private final ArrayList<String> elementList;

    public Equation(){
        this.elementList = new ArrayList<>();
    }

    /**
     * 获取方程的元素栈
     * @return ArrayList<String>
     */
    public ArrayList<String> getElementList(){
        return this.elementList;
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
     * 元素压入栈
     * @param s String
     */
    public void addLast(String s){
        this.elementList.add(this.elementList.size(), s);
    }

}
