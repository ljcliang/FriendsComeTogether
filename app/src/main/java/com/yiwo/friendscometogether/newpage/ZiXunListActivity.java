package com.yiwo.friendscometogether.newpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.yiwo.friendscometogether.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZiXunListActivity extends AppCompatActivity {


    @BindView(R.id.rl_back) 
    RelativeLayout rlBack;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_xun_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

    }

    @OnClick(R.id.rl_back)
    public void onViewClicked() {
    }
}
