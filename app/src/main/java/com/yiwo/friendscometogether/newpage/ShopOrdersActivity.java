package com.yiwo.friendscometogether.newpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.OrderPagerAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.newfragment.ShopAllOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopOrdersActivity extends BaseActivity {

    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.ll_btn_serch)
    LinearLayout llBtnSerch;
    @BindView(R.id.activity_my_order_tab)
    TabLayout mTb;
    @BindView(R.id.activity_my_order_viewpager)
    ViewPager mVp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private ShopAllOrderFragment allOrderFragment,allOrderFragment1,allOrderFragment2,allOrderFragment3,allOrderFragment4;

    private static final  String INDEX_KEY = "index";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_orders);
        ButterKnife.bind(this);
        init();
        mVp.setCurrentItem(getIntent().getIntExtra(INDEX_KEY,0));
    }
    public static void open(Context context,int index){
        Intent intent = new Intent();
        intent.setClass(context,ShopOrdersActivity.class);
        intent.putExtra(INDEX_KEY,index);
        context.startActivity(intent);
    }
    private void init() {
        initTitile();
        initFragment();
        //设置适配器
        mVp.setAdapter(new OrderPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        mTb.setupWithViewPager(mVp);
        mTb.getTabAt(0).select();
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                ShopAllOrderFragment fragment = (ShopAllOrderFragment) mFragmentList.get(i);
                if (fragment.hasChanged){
                    Log.d("changea=asdsad","//"+i);
                    fragment.refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("全部");
        mTitleList.add("待处理");
        mTitleList.add("已处理");
        mTitleList.add("已完成");
        mTitleList.add("退款");
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_FIXED);
        //tablayout获取集合中的名称
        for(int i = 0; i<mTitleList.size(); i++){
            mTb.addTab(mTb.newTab().setText(mTitleList.get(i)));
        }
    }
    private void initFragment() {
        mFragmentList = new ArrayList<>();
        allOrderFragment = ShopAllOrderFragment.newInstance(100);//全部订单
        allOrderFragment1 = ShopAllOrderFragment.newInstance(1);//待处理
        allOrderFragment2 = ShopAllOrderFragment.newInstance(2);//已处理
        allOrderFragment3 = ShopAllOrderFragment.newInstance(3);//已完成
        allOrderFragment4 = ShopAllOrderFragment.newInstance(4);//退款
        allOrderFragment.setListenner(new ShopAllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment1.setListenner(new ShopAllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment2.setListenner(new ShopAllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment3.setListenner(new ShopAllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment.hasChanged = true;
                allOrderFragment4.hasChanged = true;
            }
        });
        allOrderFragment4.setListenner(new ShopAllOrderFragment.DataChangeListenner() {
            @Override
            public void onDataChange(int type) {
                allOrderFragment1.hasChanged = true;
                allOrderFragment2.hasChanged = true;
                allOrderFragment3.hasChanged = true;
                allOrderFragment.hasChanged = true;
            }
        });
        mFragmentList.add(allOrderFragment);
        mFragmentList.add(allOrderFragment1);
        mFragmentList.add(allOrderFragment2);
        mFragmentList.add(allOrderFragment3);
        mFragmentList.add(allOrderFragment4);
    }
    public void refreshAll(){
        Log.d("sadasda", "refresh");
        switch (mVp.getCurrentItem()){
            case 0:
                allOrderFragment.refresh();
                break;
            case 1:
                allOrderFragment1.refresh();
                break;
            case 2:
                allOrderFragment2.refresh();
                break;
            case 3:
                allOrderFragment3.refresh();
                break;
            case 4:
                allOrderFragment4.refresh();
                break;

        }
    }
    @OnClick({R.id.rl_return, R.id.ll_btn_serch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_return:
                onBackPressed();
                break;
            case R.id.ll_btn_serch:
                mVp.setCurrentItem(0);
                allOrderFragment.refresh(edtSearch.getText().toString());
                break;
        }
    }
}
