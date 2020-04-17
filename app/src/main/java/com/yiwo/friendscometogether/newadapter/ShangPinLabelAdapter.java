package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.newmodel.ShangPinLabelModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class ShangPinLabelAdapter extends RecyclerView.Adapter<ShangPinLabelAdapter.ViewHolder> {

    private Context context;
    private List<ShangPinLabelModel.ObjBean> data;
    public ShangPinLabelAdapter(List<ShangPinLabelModel.ObjBean> data) {
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_shangpin_label, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getName());
        if (data.get(position).isChecked()){
            holder.rl.setBackgroundResource(R.drawable.bg_shangpin_label_choosed);
            holder.tv.setTextColor(Color.parseColor("#d84c37"));
        }else {
            holder.rl.setBackgroundResource(R.drawable.bg_shangpin_label);
            holder.tv.setTextColor(Color.parseColor("#101010"));
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.get(position).isChecked()){
                    int checkedNum = 0 ;
                    for (ShangPinLabelModel.ObjBean bean : data){
                        if (bean.isChecked()) checkedNum++;
                    }
                    if (checkedNum>2){
                        Toast.makeText(context,"最多选择3个！",Toast.LENGTH_SHORT).show();
                    }else {
                        data.get(position).setChecked(true);
                        notifyDataSetChanged();
                    }
                }else {
                    data.get(position).setChecked(false);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;
        private RelativeLayout rl;
        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            rl = itemView.findViewById(R.id.rl);
        }
    }

}