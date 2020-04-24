package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newmodel.HomeYouPuModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeYouPu_Adapter extends RecyclerView.Adapter<HomeYouPu_Adapter.ViewHolder>{
    private Context context;
    private List<HomeYouPuModel.ObjBean> data;
    private SpImp spImp;
    public HomeYouPu_Adapter(List<HomeYouPuModel.ObjBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youpu, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeYouPu_Adapter.ViewHolder holder = new HomeYouPu_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        holder.tv_price.setText(data.get(position).getPrice());
        holder.tv_shangpin_name.setText(data.get(position).getGoodsName());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(data.get(position).getGoodsImg()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_big);
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
//                intent.putExtra("pfID", data.get(position).getPfID());
//                context.startActivity(intent);
            }
        });
        switch (data.get(position).getLevelName()){
            case "0":
                holder.iv_level.setImageResource(R.mipmap.level_qingtong);
                break;
            case "1":
                holder.iv_level.setImageResource(R.mipmap.level_baiyin);
                break;
            case "2":
                holder.iv_level.setImageResource(R.mipmap.level_huangjin);
                break;
            case "3":
                holder.iv_level.setImageResource(R.mipmap.level_bojin);
                break;
            case "4":
                holder.iv_level.setImageResource(R.mipmap.level_zuanshi);
                break;
            case "5":
                holder.iv_level.setImageResource(R.mipmap.level_huangguan);
                break;
        }
        switch (data.get(position).getStarNum()){
            case "0" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing);
                break;
            case "1" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star3.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "2" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "3" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing_gray);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "4" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing_gray);
                break;
            case "5" :
                holder.iv_star1.setImageResource(R.mipmap.pingxing);
                holder.iv_star2.setImageResource(R.mipmap.pingxing);
                holder.iv_star3.setImageResource(R.mipmap.pingxing);
                holder.iv_star4.setImageResource(R.mipmap.pingxing);
                holder.iv_star5.setImageResource(R.mipmap.pingxing);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_head,iv_big;
        TextView tv_name,tv_level,tv_shangpin_name,tv_price;
        ImageView iv_level;
        ImageView iv_star1,iv_star2,iv_star3,iv_star4,iv_star5;
        LinearLayout ll_all;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_all = itemView.findViewById(R.id.ll_all);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_big = itemView.findViewById(R.id.iv_big);
            tv_shangpin_name = itemView.findViewById(R.id.tv_shangpin_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            iv_level = itemView.findViewById(R.id.iv_level);

            iv_star1 = itemView.findViewById(R.id.iv_star1);
            iv_star2 = itemView.findViewById(R.id.iv_star2);
            iv_star3 = itemView.findViewById(R.id.iv_star3);
            iv_star4 = itemView.findViewById(R.id.iv_star4);
            iv_star5 = itemView.findViewById(R.id.iv_star5);
        }
    }
}
