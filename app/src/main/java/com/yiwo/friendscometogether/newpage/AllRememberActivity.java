package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.newadapter.AllRememberViewpagerAdapter;
import com.yiwo.friendscometogether.newfragment.AllChawenFragment;
import com.yiwo.friendscometogether.newfragment.MyRememberFragment;

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
import butterknife.OnClick;

public class AllRememberActivity extends BaseActivity {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.tv_wodeyouji)
    TextView tvWodeyouji;
    @BindView(R.id.rl_wodeyouji)
    RelativeLayout rlWodeyouji;
    @BindView(R.id.tv_canxieyouji)
    TextView tvCanxieyouji;
    @BindView(R.id.rl_canxieyouji)
    RelativeLayout rlCanxieyouji;
    private Context context = AllRememberActivity.this;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.vp)
    ViewPager mViewPager;

    private FragmentManager mFragmentManager;
    private AllRememberViewpagerAdapter mViewPagerFragmentAdapter;
    private List<Fragment> fragmentList;
    private MyRememberFragment myRememberFragment;
    private AllChawenFragment allChawenFragment;
    private ArrayList<String> mTitleDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_remember);

        ScreenAdapterTools.getInstance().loadView(getWindow().getDecorView());
        ButterKnife.bind(AllRememberActivity.this);

        mFragmentManager = getSupportFragmentManager();

        initData();

    }

    private void initData() {

        fragmentList = new ArrayList<>();
        myRememberFragment = new MyRememberFragment();
        allChawenFragment = new AllChawenFragment();
        fragmentList.add(myRememberFragment);
        fragmentList.add(allChawenFragment);
        mViewPagerFragmentAdapter = new AllRememberViewpagerAdapter(mFragmentManager, fragmentList);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tvWodeyouji.setTextColor(Color.parseColor("#D84C37"));
                        tvCanxieyouji.setTextColor(Color.parseColor("#333333"));
                        myRememberFragment.setKeyWord(edtSearch.getText().toString());
                        allChawenFragment.setKeyWord("");
                        break;
                    case 1:
                        tvWodeyouji.setTextColor(Color.parseColor("#333333"));
                        tvCanxieyouji.setTextColor(Color.parseColor("#D84C37"));
                        allChawenFragment.setKeyWord(edtSearch.getText().toString());
                        myRememberFragment.setKeyWord("");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add(" 我的友记 ");
        mTitleDataList.add(" 参写友记 ");

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                //设置字体
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setText(mTitleDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#101010"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#101010"));
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
                indicator.setColors(Color.parseColor("#d84c37"));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(100);
                indicator.setRoundRadius(5);
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

    @OnClick({R.id.rl_back,R.id.tv_search,R.id.rl_wodeyouji, R.id.rl_canxieyouji})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_wodeyouji:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rl_canxieyouji:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tv_search:
                Log.d("asddsada",mViewPager.getCurrentItem()+"");
                switch (mViewPager.getCurrentItem()){
                    case 0:
                        myRememberFragment.setKeyWord(edtSearch.getText().toString());
                        myRememberFragment.searchData();
                        break;
                    case 1:
                        allChawenFragment.setKeyWord(edtSearch.getText().toString());
                        allChawenFragment.searchData();
                        break;
                }
                break;
        }
    }

}
