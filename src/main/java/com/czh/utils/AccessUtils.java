package com.czh.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 文件读写工具类
 * @author chenzhuohong
 */
public class AccessUtils {

    /**
     * 从绝对路径中读入算式
     * @param readPath 文本路径
     * @return 算式list
     */
    public static ArrayList<String> read(String readPath){
        if(readPath == null){
            throw new NullPointerException("文本路径为空");
        }
        ArrayList<String> list = new ArrayList<>();
        try{
            //使用系统默认字符集读取,否则会无法识别
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(readPath)));
            String strBuffer;
            //循环读取文件直至文件结尾
            while((strBuffer = reader.readLine())!=null){
                int index = strBuffer.lastIndexOf(".");
                //提取读出的String中有用的部分并保存
                list.add(strBuffer.substring(index+2));
            }
            reader.close();
            return list;
        }catch (Exception e){
            throw new IllegalArgumentException("文本路径错误:"+readPath);
        }
    }

    /**
     * 将String写入路径文件
     * @param text String
     * @param writePath 写入文件的绝对路径
     */
    public static void write(String text, String writePath){
        try{
            FileWriter writer = new FileWriter(writePath, true);
            writer.write(text);
            writer.flush();
            System.out.println(text + "已写入文件:" + writePath);
            writer.close();
        }catch (Exception e){
            System.out.println("写入文件失败");
        }
    }
}
