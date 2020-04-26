package com.yiwo.friendscometogether.tongban_emoticon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;

import java.util.Arrays;

public  class String2HtmlTextTools {
    public static void tvSetHtmlForImage(Context context,TextView textView, String string){
        Log.d("拆分前：：",string);
        String [] strings = string.split("]");
        for (int i = 0 ; i<strings.length ;i++){
            Log.d("拆分：：",strings[i]);
            for (int j = 0; j < EmotionNames.NAMES.length;j++){
                if (EmotionNames.NAMES[j].equals(strings[i]+"]")){
                    Log.d("拆分：遇到表情：",strings[i]+"///"+EmotionNames.NAMES[j]);
                    strings[i] = "<img src=\"" + context.getResources().getIdentifier("em_"+(j+1), "mipmap",context.getPackageName()) + "\"/>";
                    Log.d("拆分：变成Html表情：",strings[i]+"///");
                    break;
                }
            }
        }
        Html.ImageGetter imgGetterFromProject = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                int rId = Integer.parseInt(source);
                drawable = context.getResources().getDrawable(rId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        String strFinal = "";
        for (String s :strings){
            strFinal += s;
        }
        Log.d("拆分后：：",strFinal);
        textView.setText(Html.fromHtml(strFinal,imgGetterFromProject,null));
    }
}
