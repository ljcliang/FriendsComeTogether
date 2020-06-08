package com.yiwo.friendscometogether.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.czp.searchmlist.FlowLayout;
import com.czp.searchmlist.R.id;
import com.czp.searchmlist.R.layout;
import com.czp.searchmlist.SearchOldDataAdapter;
import com.czp.searchmlist.selfSearchGridView;
import com.yiwo.friendscometogether.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MySearchLayout extends LinearLayout {
    private String msearch_hint;
    Context context;
    private ImageView ib_searchtext_delete;
    private EditText et_searchtext_search;
    private LinearLayout searchview;
    private Button bt_text_search_back;
    private TextView tvclearolddata;
    private selfSearchGridView gridviewolddata;
    private SearchOldDataAdapter OldDataAdapter;
    private ArrayList<String> OldDataList = new ArrayList();
    FlowLayout hotflowLayout;
    private String backtitle = "取消";
    private String searchtitle = "搜索";
    private OnClickListener TextViewItemListener;
    private int countOldDataSize = 15;
    private MySearchLayout.setSearchCallBackListener sCBlistener;

    public MySearchLayout(Context context) {
        super(context);
        this.context = context;
        this.InitView();
    }

    public MySearchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.msearch_hint = "输入搜索内容";
        this.InitView();
    }

    public MySearchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.InitView();
    }

    private void InitView() {
        this.backtitle = "取消";
        this.searchtitle = "搜索";
        this.searchview = (LinearLayout) LayoutInflater.from(this.context).inflate(layout.msearchlayout, (ViewGroup)null);
        this.addView(this.searchview);
        this.ib_searchtext_delete = (ImageView)this.searchview.findViewById(id.ib_searchtext_delete);
        this.et_searchtext_search = (EditText)this.searchview.findViewById(id.et_searchtext_search);
        this.et_searchtext_search.setBackgroundResource(R.drawable.bg_search_edt);
        this.et_searchtext_search.setHint(this.msearch_hint);
        this.bt_text_search_back = (Button)this.searchview.findViewById(id.buttonback);
        this.tvclearolddata = (TextView)this.searchview.findViewById(id.tvclearolddata);
        this.gridviewolddata = (selfSearchGridView)this.searchview.findViewById(id.gridviewolddata);
        this.gridviewolddata.setSelector(new ColorDrawable(0));
        this.hotflowLayout = (FlowLayout)this.searchview.findViewById(id.id_flowlayouthot);
        this.setLinstener();
    }

    private void setLinstener() {
        this.ib_searchtext_delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MySearchLayout.this.et_searchtext_search.setText("");
            }
        });
        this.et_searchtext_search.addTextChangedListener(new MySearchLayout.MyTextWatcher());
        this.et_searchtext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 3 && (event == null || event.getKeyCode() != 66)) {
                    return false;
                } else {
                    String searchtext = MySearchLayout.this.et_searchtext_search.getText().toString().trim();
                    MySearchLayout.this.executeSearch_and_NotifyDataSetChanged(searchtext);
                    return true;
                }
            }
        });
        this.bt_text_search_back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String searchtext = MySearchLayout.this.et_searchtext_search.getText().toString().trim();
                if (MySearchLayout.this.bt_text_search_back.getText().toString().equals(MySearchLayout.this.searchtitle)) {
                    MySearchLayout.this.executeSearch_and_NotifyDataSetChanged(searchtext);
                } else if (MySearchLayout.this.sCBlistener != null) {
                    MySearchLayout.this.sCBlistener.Back();
                }

            }
        });
        this.tvclearolddata.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (MySearchLayout.this.sCBlistener != null) {
                    MySearchLayout.this.OldDataList.clear();
                    MySearchLayout.this.OldDataAdapter.notifyDataSetChanged();
                    MySearchLayout.this.sCBlistener.ClearOldData();
                }

            }
        });
        this.TextViewItemListener = new OnClickListener() {
            public void onClick(View v) {
                String string = ((TextView)v).getText().toString();
                MySearchLayout.this.executeSearch_and_NotifyDataSetChanged(string);
            }
        };
        this.gridviewolddata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MySearchLayout.this.sCBlistener != null) {
                    MySearchLayout.this.sCBlistener.Search(((String)MySearchLayout.this.OldDataList.get(position)).trim());
                }

            }
        });
    }

    public void initData(List<String> olddatalist, List<String> hotdata, MySearchLayout.setSearchCallBackListener sCb) {
        this.SetCallBackListener(sCb);
        this.hotflowLayout.removeAllViews();
        this.OldDataList.clear();
        if (olddatalist != null) {
            this.OldDataList.addAll(olddatalist);
        }

        this.OldDataAdapter = new SearchOldDataAdapter(this.context, this.OldDataList);
        this.gridviewolddata.setAdapter(this.OldDataAdapter);
        LayoutInflater mInflater = LayoutInflater.from(this.context);

        for(int i = 0; i < hotdata.size(); ++i) {
            TextView tv = (TextView)mInflater.inflate(layout.suosou_item, this.hotflowLayout, false);
            tv.setText((CharSequence)hotdata.get(i));
            tv.setOnClickListener(this.TextViewItemListener);
            tv.setBackgroundResource(R.drawable.bg_d8d8d8_30px);
            tv.getBackground().setLevel(this.MyRandom(1, 5));
            this.hotflowLayout.addView(tv);
        }

    }

    private void executeSearch_and_NotifyDataSetChanged(String str) {
        if (this.sCBlistener != null && !str.equals("")) {
            if (this.OldDataList.size() <= 0 || !((String)this.OldDataList.get(0)).equals(str)) {
                if (this.OldDataList.size() == this.countOldDataSize && this.OldDataList.size() > 0) {
                    this.OldDataList.remove(this.OldDataList.size() - 1);
                }

                this.OldDataList.add(0, str);
                this.OldDataAdapter.notifyDataSetChanged();
                this.sCBlistener.SaveOldData(this.OldDataList);
            }

            this.sCBlistener.Search(str);
        }

    }

    public int MyRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    private void SetCallBackListener(MySearchLayout.setSearchCallBackListener sCb) {
        this.sCBlistener = sCb;
    }

    public interface setSearchCallBackListener {
        void Search(String var1);

        void Back();

        void ClearOldData();

        void SaveOldData(ArrayList<String> var1);
    }

    private class MyTextWatcher implements TextWatcher {
        private MyTextWatcher() {
        }

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                MySearchLayout.this.ib_searchtext_delete.setVisibility(VISIBLE);
                MySearchLayout.this.bt_text_search_back.setText(MySearchLayout.this.searchtitle);
            } else {
                MySearchLayout.this.ib_searchtext_delete.setVisibility(GONE);
                MySearchLayout.this.bt_text_search_back.setText(MySearchLayout.this.backtitle);
            }

        }
    }
}
