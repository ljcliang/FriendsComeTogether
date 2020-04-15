package com.yiwo.friendscometogether.newadapter.fabushangpinadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import com.yiwo.friendscometogether.model.UserFocusModel;
import com.yiwo.friendscometogether.newmodel.FabuShangPinUpLoadModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.sp.SpImp;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class FaBuShangPinJiaGeAdapter extends RecyclerView.Adapter<FaBuShangPinJiaGeAdapter.ViewHolder> {

    private Context context;
    private List<FabuShangPinUpLoadModel.SpecBean> data;
    private SpImp spImp;
    private DeleteItemListenner listenner;
    public FaBuShangPinJiaGeAdapter(List<FabuShangPinUpLoadModel.SpecBean> data,DeleteItemListenner listenner) {
        this.data = data;
        this.listenner = listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fabushangpin_price, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position > 0){
            holder.rl_btn_delete.setVisibility(View.VISIBLE);
        }else {
            holder.rl_btn_delete.setVisibility(View.GONE);
        }
        holder.rl_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.deleteItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_btn_delete;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_btn_delete = itemView.findViewById(R.id.rl_btn_minus);
        }
    }
    public interface DeleteItemListenner{
        void deleteItem(int pos);
    }

}
