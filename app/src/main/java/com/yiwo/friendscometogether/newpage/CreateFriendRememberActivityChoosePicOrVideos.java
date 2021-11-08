package com.yiwo.friendscometogether.newpage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.MainActivity;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.newadapter.AllRememberViewpagerAdapter;
import com.yiwo.friendscometogether.newfragment.CreateFriendRememberNew_ChoosePicsFragment;
import com.yiwo.friendscometogether.wangyiyunshipin.TakeVideoFragment_new;
import com.yiwo.friendscometogether.widget.CanNotScollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class CreateFriendRememberActivityChoosePicOrVideos extends BaseActivity {

    public static final String EXTRA_FROM_UPLOAD_NOTIFY = "extra_from_upload_notify"; //由上传通知启动
    public static final String ONLY_ADD_VIDEO = "add_video";
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp)
    CanNotScollViewPager mViewPager;

    private FragmentManager mFragmentManager;
    private AllRememberViewpagerAdapter mViewPagerFragmentAdapter;
    private List<Fragment> fragmentList;

    private ArrayList<String> mTitleDataList;
    private SimplePagerTitleView simplePagerTitleView;
    private boolean onlyShowAddVideo = false;
    RxPermissions rxPermissions ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friend_remeber_choose_pic_or_video);
        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(CreateFriendRememberActivityChoosePicOrVideos.this);
        StatusBarUtils.setStatusBarTransparent(CreateFriendRememberActivityChoosePicOrVideos.this);
        onlyShowAddVideo = getIntent().getBooleanExtra(ONLY_ADD_VIDEO,false);
        mFragmentManager = getSupportFragmentManager();
        rxPermissions = new RxPermissions(CreateFriendRememberActivityChoosePicOrVideos.this);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO
                        , Manifest.permission.CAMERA)
                .subscribe(new io.reactivex.Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        subscribe(d);
                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            // 用户已经同意该权限
                            initData();
                        } else {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            AlertDialog.Builder builder = new AlertDialog.Builder(CreateFriendRememberActivityChoosePicOrVideos.this);
                            builder.setMessage("请开启相关权限后才可使用")
                                    .setCancelable(false)
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", CreateFriendRememberActivityChoosePicOrVideos.this.getPackageName(), null);
                                            intent.setData(uri);
                                            try {
                                                startActivity(intent);
                                                finish();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(onlyShowAddVideo){
            fragmentList.get(0).onActivityResult(requestCode, resultCode, data);
        }else {
            fragmentList.get(1).onActivityResult(requestCode, resultCode, data);
        }
        Log.d("onActivityResulton___","requestCode:"+requestCode+"///"+"resultCode:"+resultCode);
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        if (!onlyShowAddVideo){
            fragmentList.add(new CreateFriendRememberNew_ChoosePicsFragment());
        }
        fragmentList.add(new TakeVideoFragment_new());
//        fragmentList.add(new CreateFriendRememberNew_ChoosePicsFragment());
        mViewPagerFragmentAdapter = new AllRememberViewpagerAdapter(mFragmentManager, fragmentList);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setScanScroll(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    StatusBarUtils.setStatusBar(CreateFriendRememberActivityChoosePicOrVideos.this,Color.TRANSPARENT);
                }else {
                    StatusBarUtils.setStatusBar(CreateFriendRememberActivityChoosePicOrVideos.this,Color.parseColor("#f6f6f6"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTitleDataList = new ArrayList<>();
        if (!onlyShowAddVideo){
            mTitleDataList.add("相册");
        }
        mTitleDataList.add("视频");
//        mTitleDataList.add("拍照");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);  //ture 即标题平分屏幕宽度的模式
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mTitleDataList.get(index));
                //设置字体
                simplePagerTitleView.setTextSize(18);
//                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.parseColor("#d84c37"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
//        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
//        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        titleContainer.setDividerPadding(UIUtil.dip2px(this, 15));
//        titleContainer.setDividerDrawable(getResources().getDrawable(R.drawable.simple_splitter));
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }
}
