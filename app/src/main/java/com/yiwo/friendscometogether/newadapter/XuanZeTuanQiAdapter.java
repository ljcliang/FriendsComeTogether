package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.XuanZeTuanQiModel;

import java.util.List;

/**
 * Created by ljc on 2019/1/9.
 */

public class XuanZeTuanQiAdapter extends RecyclerView.Adapter<XuanZeTuanQiAdapter.ViewHolder> {

    private Context context;
    private List<XuanZeTuanQiModel.ObjBean> data;
    private OnDateCheckedListenner onDateCheckedListenner;
    public XuanZeTuanQiAdapter(List<XuanZeTuanQiModel.ObjBean> data,OnDateCheckedListenner onDateCheckedListenner){
        this.data = data;
        this.onDateCheckedListenner = onDateCheckedListenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_xuanzetuanqi, parent, false);
        XuanZeTuanQiAdapter.ViewHolder holder = new XuanZeTuanQiAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(data.get(position).getPftitle());
        XuanZeTuanQiDateAdapter xuanZeTuanQiDateAdapter = new XuanZeTuanQiDateAdapter(data.get(position).getPhaseList(), new XuanZeTuanQiDateAdapter.OnClickListenner() {
            @Override
            public void onClick(int i) {
                onDateCheckedListenner.onDateChecked(position,i);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rv.setLayoutManager(manager);
        holder.rv.setAdapter(xuanZeTuanQiDateAdapter);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;
        RecyclerView rv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
            rv = itemView.findViewById(R.id.rv);
        }
    }
    public interface OnDateCheckedListenner{
        void onDateChecked(int pfPosition,int phasePosition);
    }
}
