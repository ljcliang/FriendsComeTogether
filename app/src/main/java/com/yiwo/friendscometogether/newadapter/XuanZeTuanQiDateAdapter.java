package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.XuanZeTuanQiModel;

import java.util.List;

/**
 * Created by ljc on 2019/1/9.
 */

public class XuanZeTuanQiDateAdapter extends RecyclerView.Adapter<XuanZeTuanQiDateAdapter.ViewHolder> {

    private Context context;
    private List<XuanZeTuanQiModel.ObjBean.PhaseListBean> data;
    private OnClickListenner onClickListenner;
    public XuanZeTuanQiDateAdapter(List<XuanZeTuanQiModel.ObjBean.PhaseListBean> data,OnClickListenner listenner){
        this.data = data;
        this.onClickListenner = listenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_xuanzetuanqi_timetext, parent, false);
        XuanZeTuanQiDateAdapter.ViewHolder holder = new XuanZeTuanQiDateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getPhase_begin_time());
        if (data.get(position).isChecked()){
            holder.tv.setBackgroundResource(R.drawable.bg_d84c37_30px);
        }else {
            holder.tv.setBackgroundResource(R.drawable.bg_d8d8d8_30px);
        }
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenner.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
    public interface OnClickListenner{
        void onClick(int i);
    }
}
