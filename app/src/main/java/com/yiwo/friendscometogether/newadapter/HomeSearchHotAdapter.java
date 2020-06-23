package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class HomeSearchHotAdapter extends RecyclerView.Adapter<HomeSearchHotAdapter.ViewHolder> {

    private Context context;
    private List<String> data;
    private onClickListen listen;
    public HomeSearchHotAdapter(List<String> data,onClickListen listen) {
        this.listen = listen;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_search_hot, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listen.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    public interface onClickListen{
        void onClick(int i);
    }
}
