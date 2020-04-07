package com.yiwo.friendscometogether.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.imagepreview.Consts;
import com.yiwo.friendscometogether.imagepreview.ImagePreviewActivity;
import com.yiwo.friendscometogether.model.FriendsTogetherDetailsModel;
import com.yiwo.friendscometogether.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/19.
 */

public class FriendsTogetherDetailsItemAdapter extends RecyclerView.Adapter<FriendsTogetherDetailsItemAdapter.ViewHolder> {
    List<FriendsTogetherDetailsModel.ObjBean.InfoListBean.ImageListBean> data;
    private Context context;

    public FriendsTogetherDetailsItemAdapter(List<FriendsTogetherDetailsModel.ObjBean.InfoListBean.ImageListBean> data){
        this.data=data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_pic,parent,false);
        ScreenAdapterTools.getInstance().loadView(view);
        FriendsTogetherDetailsItemAdapter.ViewHolder holder = new FriendsTogetherDetailsItemAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getPic()).apply(new RequestOptions().placeholder(R.mipmap.zanwutupian).error(R.mipmap.zanwutupian)).into(holder.item_picIv);
        if (data.get(position).getText_info().equals("")){
            holder.item_titleTv.setVisibility(View.GONE);
        }
        holder.item_titleTv.setText(data.get(position).getText_info());
        holder.item_picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> urlList = new ArrayList<>();
                for (int i = 0; i<data.size(); i++){
                    urlList.add(data.get(i).getPic());
                }
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, position);
                intent.putExtra(Consts.START_IAMGE_POSITION, position);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                context.startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_picIv;
        private TextView item_titleTv;
        public ViewHolder(View itemView) {
            super(itemView);
            item_picIv = (itemView).findViewById(R.id.item_picIv);
            item_titleTv = (itemView).findViewById(R.id.item_titleTv);
        }
    }
}
