package com.yiwo.friendscometogether.newpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.adapter.SearchListAdapter;
import com.yiwo.friendscometogether.base.BaseActivity;
import com.yiwo.friendscometogether.model.AllSearchModel;
import com.yiwo.friendscometogether.model.SearchListModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newadapter.HomeSearchHistoryAdapter;
import com.yiwo.friendscometogether.newadapter.HomeSearchHotAdapter;
import com.yiwo.friendscometogether.pages.SearchListActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.widget.FlowLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity {

    @BindView(R.id.v_huodong)
    View vHuodong;
    @BindView(R.id.rl_btn_huodong)
    RelativeLayout rlBtnHuodong;
    @BindView(R.id.v_shangpin)
    View vShangpin;
    @BindView(R.id.rl_btn_shangpin)
    RelativeLayout rlBtnShangpin;
    @BindView(R.id.v_wenzhang)
    View vWenzhang;
    @BindView(R.id.rl_btn_wenzhang)
    RelativeLayout rlBtnWenzhang;
    @BindView(R.id.v_shipin)
    View vShipin;
    @BindView(R.id.rl_btn_shipin)
    RelativeLayout rlBtnShipin;
    @BindView(R.id.rv_zuijin)
    RecyclerView rvZuijin;
    @BindView(R.id.rv_hot)
    RecyclerView rvHot;
    @BindView(R.id.rl_search_list)
    RelativeLayout rlSearchList;
    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.rl_close)
    RelativeLayout rlClose;
    @BindView(R.id.tv_btn_search)
    TextView tvBtnSearch;
    @BindView(R.id.iv_clear_edt)
    ImageView ivClearEdt;
    @BindView(R.id.rv_search_list)
    RecyclerView rvSearchList;


    //历史记录
    String shareData = "";
    List<String> listHistory;
    HomeSearchHistoryAdapter historyAdapter;
    List<SearchListModel.ObjBean> listSearch = new ArrayList<>();
    //搜索
    private String type = "1";//0,友聚活动，1友记文章,2视频
    private  SearchListAdapter searchListAdapter;

    //热门搜索
    private List<String> listHot;
    private HomeSearchHotAdapter hotAdapter;
    private SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
//        type = getIntent().getStringExtra("type");
        spImp = new SpImp(HomeSearchActivity.this);

        initData();
        chooseType(1);
    }

    private void initData() {
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!TextUtils.isEmpty(edt.getText().toString())){
                        hideKeyboard(edt.getWindowToken());
                        search();
                        return true;
                    }

                }
                return false;
            }
        });
        shareData = spImp.getSearch();
        if (TextUtils.isEmpty(shareData) || shareData.equals("")) {
            listHistory = new ArrayList();
        } else {
            listHistory = new ArrayList<>(Arrays.asList(shareData.split(",")));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvZuijin.setLayoutManager(manager);
        historyAdapter = new HomeSearchHistoryAdapter(listHistory, new HomeSearchHistoryAdapter.onClickListen() {
            @Override
            public void onClick(int i) {
                edt.setText(listHistory.get(i));
                search();
            }
        });
        rvZuijin.setAdapter(historyAdapter);

        FlowLayoutManager manager1 = new FlowLayoutManager() {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvHot.setLayoutManager(manager1);
        listHot = new ArrayList<>();
        hotAdapter = new HomeSearchHotAdapter(listHot, new HomeSearchHotAdapter.onClickListen() {
            @Override
            public void onClick(int i) {
                edt.setText(listHot.get(i));
                search();
            }
        });
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    tvBtnSearch.setVisibility(View.GONE);
                    ivClearEdt.setVisibility(View.GONE);
                    rlClose.setVisibility(View.VISIBLE);
                    rlSearchList.setVisibility(View.GONE);
                } else {
                    tvBtnSearch.setVisibility(View.VISIBLE);
                    ivClearEdt.setVisibility(View.VISIBLE);
                    rlClose.setVisibility(View.GONE);
                }
            }
        });
        rvHot.setAdapter(hotAdapter);
        ViseHttp.POST(NetConfig.allSearchUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.allSearchUrl))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                AllSearchModel model = gson.fromJson(data, AllSearchModel.class);
                                for (int i = 0; i < model.getObj().size(); i++) {
                                    listHot.add(model.getObj().get(i).getTitle());
                                }
                                listHot.add("热门热门搜索");
                                listHot.add("热门");
                                listHot.add("热门热");
                                listHot.add("热门热门搜索");
                                hotAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearchList.setLayoutManager(manager2);
        searchListAdapter = new SearchListAdapter(listSearch);
        rvSearchList.setAdapter(searchListAdapter);
    }

    private void search() {
        String s = edt.getText().toString();
        Log.i("11111111", s);
        //保存搜索历史
        shareData = "";
        boolean hasThisStr = false;
        for (String str : listHistory) {
            if (str.equals(s)) {
                hasThisStr = true;
                break;
            }
        }
        if (!hasThisStr) listHistory.add(0, s);
        if (listHistory.size() > 5) {
            listHistory.remove(listHistory.size() - 1);
        }
        historyAdapter.notifyDataSetChanged();

        for (int i = 0; i < listHistory.size(); i++) {
            if (i == listHistory.size() - 1) {
                shareData = shareData + listHistory.get(i);
            } else {
                shareData = shareData + listHistory.get(i) + ",";
            }
        }
        Log.e("2333", shareData);
        spImp.setSearch(shareData);
        ViseHttp.POST(NetConfig.searchFriendTogetherUrl)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.searchFriendTogetherUrl))
                .addParam("page", "1")
                .addParam("activity_name", s)
                .addParam("type", type)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        SearchListModel model = new Gson().fromJson(data, SearchListModel.class);
                        if (model.getCode() == 200) {
                            if (model.getObj().size() == 0) {
                                toToast(HomeSearchActivity.this, "暂无搜索结果");
                                listSearch.clear();
                                searchListAdapter.notifyDataSetChanged();
                            } else {
                                rlSearchList.setVisibility(View.VISIBLE);
                                listSearch.clear();
                                listSearch.addAll(model.getObj());
                                searchListAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(HomeSearchActivity.this, errMsg);
                    }
                });
    }

    @OnClick({R.id.rl_btn_huodong, R.id.rl_btn_shangpin, R.id.rl_btn_wenzhang, R.id.rl_btn_shipin, R.id.tv_btn_clear_zuijin, R.id.rl_close, R.id.iv_clear_edt, R.id.tv_btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_btn_huodong:
                chooseType(0);
                break;
            case R.id.rl_btn_wenzhang:
                chooseType(1);
                break;
            case R.id.rl_btn_shipin:
                chooseType(2);
                break;
            case R.id.rl_btn_shangpin:
                chooseType(3);
                break;
            case R.id.rl_close:
                onBackPressed();
                break;
            case R.id.tv_btn_clear_zuijin:
                spImp.setSearch("");
                shareData = "";
                listHistory.clear();
                historyAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_clear_edt:
                edt.setText("");
                break;
            case R.id.tv_btn_search:
                search();
                break;
        }
    }

    private void chooseType(int type) {
        this.type = type + "";
        vHuodong.setVisibility(View.GONE);
        vWenzhang.setVisibility(View.GONE);
        vShipin.setVisibility(View.GONE);
        vShangpin.setVisibility(View.GONE);
        switch (type) {
            case 0:
                vHuodong.setVisibility(View.VISIBLE);
                break;
            case 1:
                vWenzhang.setVisibility(View.VISIBLE);
                break;
            case 2:
                vShipin.setVisibility(View.VISIBLE);
                break;
            case 3:
                vShangpin.setVisibility(View.VISIBLE);
                break;
        }
        if (!TextUtils.isEmpty(edt.getText().toString())){
            search();
        }
    }
}
