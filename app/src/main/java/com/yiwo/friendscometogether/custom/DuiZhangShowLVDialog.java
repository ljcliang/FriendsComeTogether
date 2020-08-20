package com.yiwo.friendscometogether.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.GuidePageAdapter;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.webpages.NoTitleWebActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2019/9/26.
 */

public class DuiZhangShowLVDialog extends Dialog {

    private Context context;
    private String info;
    private String userIcon;
    private OnGuanzhuListenner listenner;
    private boolean isFollow;
    private String lv_name;
    private int[] imageIdArray;//图片资源的数组
    private String[] strName;
    private List<View> viewList;//图片资源的集合
    private List<String> datas;
    public DuiZhangShowLVDialog(@NonNull Context context,String lv_name,List<String> datas) {
        super(context);
        this.context = context;
        this.lv_name = lv_name;
        this.datas = datas;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //解决进度弹窗出现时，状态栏变成黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);
                } catch (Exception e) {}
            }
        }


        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
////                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LOW_PROFILE;
//        this.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_duizhang_show_lv, null);
        setContentView(view);
        //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ScreenAdapterTools.getInstance().loadView(view);
        RelativeLayout rl = view.findViewById(R.id.rl);
        RelativeLayout rl_close = view.findViewById(R.id.rl_close);
        rl_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ViewPager viewPager = view.findViewById(R.id.vp);
        imageIdArray = new int[]{R.mipmap.show_duizhang_lv_qingtong, R.mipmap.show_duizhang_lv_baiyin, R.mipmap.show_duizhang_lv_huangjin,
                R.mipmap.show_duizhang_lv_bojin,R.mipmap.show_duizhang_lv_zuanshi,R.mipmap.show_duizhang_lv_wangguan};
        strName = new String[]{"青铜队长","白银队长","黄金队长","铂金队长","钻石队长","王冠队长"};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < imageIdArray.length; i++) {
            View view_page_item = LayoutInflater.from(context).inflate(R.layout.layout_duizhang_show, null);
            //new ImageView并设置全屏和图片资源
            ImageView imageView = view_page_item.findViewById(R.id.iv_bg);
//            imageView.setLayoutParams(params);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imageIdArray[i]);
            TextView tv_text_info = view_page_item.findViewById(R.id.tv_show_info);
            tv_text_info.setText(datas.get(i));
            TextView tv_level = view_page_item.findViewById(R.id.tv_level_name);
            tv_level.setText(strName[i]);
            //将ImageView加入到集合中
            viewList.add(view_page_item);
        }
        //View集合初始化好后，设置Adapter
        viewPager.setAdapter(new VpAdapter(viewList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        switch (lv_name){
            case "0":
                viewPager.setCurrentItem(0);
                break;
            case "1":
                viewPager.setCurrentItem(1);
                break;
            case "2":
                viewPager.setCurrentItem(2);
                break;
            case "3":
                viewPager.setCurrentItem(3);
                break;
            case "4":
                viewPager.setCurrentItem(4);
                break;
            case "5":
                viewPager.setCurrentItem(5);
                break;
        }
//        RelativeLayout btnGuanzhu = view.findViewById(R.id.btn_guanzhu);
//        TextView tvGuanzhu = view.findViewById(R.id.tv_guanzhu);
//        tvGuanzhu.setText(isFollow ? "已关注":"关 注");
//        btnGuanzhu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listenner.guanZhuListen(DuiZhangShowLVDialog.this);
//            }
//        });
//        rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
    }
    public interface OnGuanzhuListenner{
        void guanZhuListen(Dialog dialog);
    }
    class VpAdapter extends PagerAdapter{

        private List<View> viewList;

        public VpAdapter(List<View> viewList) {
            this.viewList = viewList;
        }
        /**
         * @return 返回页面的个数
         */
        @Override
        public int getCount() {
            if (viewList != null){
                return viewList.size();
            }
            return 0;
        }

        /**
         * 判断对象是否生成界面
         * @param view
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 初始化position位置的界面
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }
    }
}
