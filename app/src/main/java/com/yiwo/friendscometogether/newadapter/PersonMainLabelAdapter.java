package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.model.KVMode;

import java.util.List;

/**
 * Created by Administrator on 2019/1/8.
 */

public class PersonMainLabelAdapter extends RecyclerView.Adapter<PersonMainLabelAdapter.ViewHolder> {

    private Context context;
    private List<PersonMainLabelModel> data;
    public PersonMainLabelAdapter(List<PersonMainLabelModel> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_person_main_label, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("labellabelabel","SIZE:::"+data.size());
        if (!data.get(position).isSameMine()){
            holder.tv.setBackgroundResource(R.drawable.bg_d8d8d8_border_30px);
            holder.tv.setTextColor(Color.parseColor("#666666"));
        }else {
            holder.tv.setBackgroundResource(R.drawable.bg_d84c37_30px);
            holder.tv.setTextColor(Color.WHITE);
        }
        holder.tv.setText(data.get(position).getName());
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

}
