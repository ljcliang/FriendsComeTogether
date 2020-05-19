package com.yiwo.friendscometogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.netease.nim.uikit.business.recent.RecentContactsFragment;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/16.
 */

public class ChatFragment extends BaseFragment{
    View rootView;

    @BindView(R.id.rl_my_friend)
    RelativeLayout rlMyFriend;
//    @BindView(R.id.refresh_layout)
//    RefreshLayout refreshLayout;
    private String account;
    private String token;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat,null);
//        ScreenAdapterTools.getInstance().loadView(rootView);

        ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());
        refreshRecentContactsFragment();
//        initRefresh();
        return rootView;
    }
//
//    private void initRefresh() {
//        refreshLayout.setEnableLoadMore(false);
//        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshRecentContactsFragment();
//                refreshLayout.finishRefresh(1000);
//            }
//        });
//    }

    public void refreshRecentContactsFragment() {
        RecentContactsFragment fragment = new RecentContactsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.replace(R.id.fragment_contacts, fragment);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
//        refreshRecentContactsFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refreshRecentContactsFragment();
    }

    @OnClick({R.id.rl_my_friend})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_my_friend:
                uid = spImp.getUID();
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFriendActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }
}
