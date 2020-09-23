package com.yiwo.friendscometogether.utils;

public class WebUntils {
    public static String replaceStr(String str){
        str = str.replace("\r","<br/>").replace("\n","<br/>").replace("\t","<br/>");
        str = str.replace("'","").replace("\\","").replace("\"","");
        return str;
    }
}
