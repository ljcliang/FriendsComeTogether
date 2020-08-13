package com.yiwo.friendscometogether.newadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianYouJiShiPinModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebLocalActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebLocalActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebLocalActivity;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class Home_GuanZhu_YouJiShiPin_Adapter extends RecyclerView.Adapter<Home_GuanZhu_YouJiShiPin_Adapter.ViewHolder>{
    private Context context;
    private List<HomeGuanZhuModel.ObjBean.YjVideoBean> data;
    private SpImp spImp;
    public Home_GuanZhu_YouJiShiPin_Adapter(List<HomeGuanZhuModel.ObjBean.YjVideoBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_home_youji_0407_item, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        Home_GuanZhu_YouJiShiPin_Adapter.ViewHolder holder = new Home_GuanZhu_YouJiShiPin_Adapter.ViewHolder(view);
        Log.d("jaizai,","00000");
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        holder.tv_address.setText(data.get(position).getFmaddress());
        holder.tv_content.setText("    "+data.get(position).getFmaddress()+" · "+data.get(position).getFmtitle());

        holder.tv_look_num.setText(data.get(position).getFmlook());
        holder.tv_wucanxie_seenum.setText(data.get(position).getFmlook());
        holder.tv_wucanxie_pinglun_num.setText(data.get(position).getCNum());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        if (data.get(position).getPicList().size()>0){
            Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_big);
        }
        holder.iv_canyu1.setVisibility(View.GONE);
        holder.iv_canyu2.setVisibility(View.GONE);
        holder.iv_canyu3.setVisibility(View.GONE);
        holder.iv_canyu4.setVisibility(View.GONE);
        if (data.get(position).getInPerson().size()>0){
            holder.rl_canyuxiezuo.setVisibility(View.VISIBLE);
            holder.iv_canyu1.setVisibility(View.VISIBLE);
            holder.ll_wucanxie_seenum.setVisibility(View.GONE);
            Glide.with(context).load(data.get(position).getInPerson().get(0)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu1);
            if (data.get(position).getInPerson().size()>=5){
                holder.tv_canyu.setText("···"+ data.get(position).getInPersonNum()+"人参与写作");
            }else {
                holder.tv_canyu.setText(""+ data.get(position).getInPersonNum()+"人参与写作");
            }
        }else {
            holder.rl_canyuxiezuo.setVisibility(View.GONE);
            holder.ll_wucanxie_seenum.setVisibility(View.VISIBLE);
        }
        if (data.get(position).getInPerson().size()>1){
            holder.iv_canyu2.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getInPerson().get(1)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu2);
        }
        if (data.get(position).getInPerson().size()>2){
            holder.iv_canyu3.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getInPerson().get(2)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu3);
        }
        if (data.get(position).getInPerson().size()>3){
            holder.iv_canyu4.setVisibility(View.VISIBLE);
            Glide.with(context).load(data.get(position).getInPerson().get(3)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu4);
        }
        if (data.get(position).getIfCaptain().equals("1")){
            holder.iv_level.setVisibility(View.VISIBLE);
        }else {
            holder.iv_level.setVisibility(View.GONE);
        }
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
        if (data.get(position).getTp().equals("0")){
            holder.iv_play.setVisibility(View.GONE);
        }else {
            holder.iv_play.setVisibility(View.VISIBLE);
        }
        holder.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getTp().equals("0")){
                    intent.setClass(context, DetailsOfFriendsWebLocalActivity.class);
                    intent.putExtra("fmid", data.get(position).getFmID());
                    context.startActivity(intent);
                }else {
                    intent.setClass(context, VideoActivity.class);
                    intent.putExtra("videoUrl", data.get(position).getPalyUrl());
                    intent.putExtra("title", data.get(position).getFmtitle());
                    intent.putExtra("picUrl", data.get(position).getPicList().get(0));
                    intent.putExtra("vid", data.get(position).getFmID());
                    context.startActivity(intent);
                }
            }
        });
        if (TextUtils.isEmpty(data.get(position).getFmpartyID())){
            holder.rl_xiangguan_huodong.setVisibility(View.GONE);
        }else {
            holder.rl_xiangguan_huodong.setVisibility(View.VISIBLE);
        }
        holder.tv_huodong.setText(data.get(position).getPfInfo());
        holder.rl_xiangguan_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(data.get(position).getFmpartyID())){
                    intent.setClass(context, DetailsOfFriendTogetherWebLocalActivity.class);
                    intent.putExtra("pfID", data.get(position).getFmpartyID());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size(): 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_big;
        ImageView iv_head;
        TextView tv_name,tv_level,tv_address,tv_content,tv_huodong,tv_canyu,tv_look_num;
        ImageView iv_level,iv_canyu1,iv_canyu2,iv_canyu3,iv_canyu4,iv_play;
        RelativeLayout rl_all,rl_canyuxiezuo,rl_xiangguan_huodong;
        LinearLayout ll_wucanxie_seenum;
        TextView tv_wucanxie_seenum,tv_wucanxie_pinglun_num;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            iv_big = itemView.findViewById(R.id.iv_big);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_huodong = itemView.findViewById(R.id.tv_huodong);
            tv_canyu = itemView.findViewById(R.id.tv_canyu);
            tv_look_num = itemView.findViewById(R.id.tv_look_num);
            iv_level = itemView.findViewById(R.id.iv_level);
            iv_canyu1 = itemView.findViewById(R.id.iv_canyu1);
            iv_canyu2 = itemView.findViewById(R.id.iv_canyu2);
            iv_canyu3 = itemView.findViewById(R.id.iv_canyu3);
            iv_canyu4 = itemView.findViewById(R.id.iv_canyu4);
            iv_play = itemView.findViewById(R.id.iv_play);
            rl_xiangguan_huodong = itemView.findViewById(R.id.rl_xiangguan_huodong);
            rl_canyuxiezuo = itemView.findViewById(R.id.rl_canyuxiezuo);
            ll_wucanxie_seenum = itemView.findViewById(R.id.ll_wucanxie_seenum);
            tv_wucanxie_seenum = itemView.findViewById(R.id.tv_look_num_wucanxie);
            tv_wucanxie_pinglun_num= itemView.findViewById(R.id.tv_pinglun_num_wucanxie);
            rl_all = itemView.findViewById(R.id.rl_all);
        }
    }
}
