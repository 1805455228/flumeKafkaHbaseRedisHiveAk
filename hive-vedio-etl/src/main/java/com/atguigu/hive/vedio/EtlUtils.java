package com.atguigu.hive.vedio;

/**
 * @author zxfcode
 * @create 2018-11-08 15:30
 */
public class EtlUtils {
    public static String strEtl(String line) {
        //sb用来处理字符串
        StringBuffer sb = new StringBuffer();
        //以分割符\t分割为一个一个的字段
        String[] str = line.split("\t");
        if(str.length<9){
            return null;
        }
//        System.out.println(str.toString());
        //业务逻辑
        str[3] = str[3].replace(" ", "");

        for(int i=0;i<str.length;i++){
            if(i<9){
                if(i == str.length-1){
                    sb.append(str[i]);
                }else{
                    sb.append(str[i]+"\t");
                }
            }else{
                if(i == str.length-1){
                    sb.append(str[i]);
                }else{
                    sb.append(str[i]+"&");
                }
            }
        }
        String string = sb.toString();

        return string;
    }
}
