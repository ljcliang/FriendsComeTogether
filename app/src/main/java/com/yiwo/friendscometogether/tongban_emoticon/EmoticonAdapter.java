package com.yiwo.friendscometogether.tongban_emoticon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.GuanZhuWoDeModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

public class EmoticonAdapter extends RecyclerView.Adapter<EmoticonAdapter.ViewHolder> {

    private Context context;
    private List<EmoticonModel> data;
    private SpImp spImp;
    private OnClickListenner listenner;
    public EmoticonAdapter(List<EmoticonModel> data,OnClickListenner listenner) {
        this.data = data;
        this.listenner = listenner;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_emoticon, parent, false);
        ScreenAdapterTools.getInstance().loadView(view);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.iv.setImageResource(data.get(position).getResources());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        LinearLayout ll;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            ll = itemView.findViewById(R.id.ll);
        }
    }
    public interface  OnClickListenner{
        void onClick(int postion);
    }
}
