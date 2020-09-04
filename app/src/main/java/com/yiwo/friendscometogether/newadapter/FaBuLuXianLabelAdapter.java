package com.yiwo.friendscometogether.newadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.UserLabelModel;
import com.yiwo.friendscometogether.newmodel.ShangPinLabelModel;

import java.util.List;

/**
 * Created by Administrator on 2018/12/24.
 */

public class FaBuLuXianLabelAdapter extends RecyclerView.Adapter<FaBuLuXianLabelAdapter.ViewHolder> {

    private Context context;
    private List<UserLabelModel.ObjBean> data;
    private OnItemChoosed onItemChoosed;
    public FaBuLuXianLabelAdapter(List<UserLabelModel.ObjBean> data, OnItemChoosed onItemChoosedlistenner) {
        this.data = data;
        this.onItemChoosed = onItemChoosedlistenner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fabuluxian_label, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv.setText(data.get(position).getLname());
        if (data.get(position).isChoose()){
            holder.rl.setBackgroundResource(R.drawable.bg_shangpin_label_choosed);
            holder.tv.setTextColor(Color.parseColor("#d84c37"));
        }else {
            holder.rl.setBackgroundResource(R.drawable.bg_shangpin_label);
            holder.tv.setTextColor(Color.parseColor("#101010"));
        }
        holder.rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemChoosed.onLongClick(position);
                return false;
            }
        });
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChoosed.onChoosed(position);
//                if (!data.get(position).isChecked()){
//                    int checkedNum = 0 ;
//                    for (ShangPinLabelModel.ObjBean bean : data){
//                        if (bean.isChecked()) checkedNum++;
//                    }
//                    if (checkedNum>2){
//                        Toast.makeText(context,"最多选择3个！",Toast.LENGTH_SHORT).show();
//                    }else {
//                        data.get(position).setChecked(true);
//                        notifyDataSetChanged();
//                    }
//                }else {
//                    data.get(position).setChecked(false);
//                    notifyDataSetChanged();
//                }
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
    public interface OnItemChoosed{
        void onChoosed(int pos);
        void onLongClick(int pos);
    }
}
