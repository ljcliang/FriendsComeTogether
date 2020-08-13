package com.yiwo.friendscometogether.newpage.renzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yiwo.friendscometogether.R;

public class RenZheng4_WanChengRenZhengActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng4__wan_cheng_ren_zheng);
    }
    public static void openActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context,RenZheng4_WanChengRenZhengActivity.class);
        context.startActivity(intent);
    }
}
