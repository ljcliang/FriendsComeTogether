package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;

import java.util.List;

public class ZiXunTouTiaoAdapter extends RecyclerView.Adapter<ZiXunTouTiaoAdapter.ViewHolder> {
    private Context context;
    private List<String> data ;
    public ZiXunTouTiaoAdapter(List<String> list){
        this.data = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_hometuijian_zixuntoutiao,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_title.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size()>0 ? data.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title,tv_look_num,tv_pinglun_num;
        ImageView iv0,iv1,iv2;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_look_num =  itemView.findViewById(R.id.tv_look_num);
            tv_pinglun_num =  itemView.findViewById(R.id.tv_pinglun_num);
            iv0 =  itemView.findViewById(R.id.iv0);
            iv1 =  itemView.findViewById(R.id.iv1);
            iv2 =  itemView.findViewById(R.id.iv2);
        }
    }
}
