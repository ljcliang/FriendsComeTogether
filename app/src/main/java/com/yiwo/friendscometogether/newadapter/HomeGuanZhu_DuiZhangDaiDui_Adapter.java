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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.newmodel.HomeGuanZhuModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.ViewUtil;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeGuanZhu_DuiZhangDaiDui_Adapter extends RecyclerView.Adapter<HomeGuanZhu_DuiZhangDaiDui_Adapter.ViewHolder>{
    private Context context;
    private List<HomeGuanZhuModel.ObjBean.CaptainPfBean> data;
    private SpImp spImp;
    public HomeGuanZhu_DuiZhangDaiDui_Adapter(List<HomeGuanZhuModel.ObjBean.CaptainPfBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_guanzhu_duizhangdaidui, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeGuanZhu_DuiZhangDaiDui_Adapter.ViewHolder holder = new HomeGuanZhu_DuiZhangDaiDui_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(data.get(position).getPfpic()).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_big);
        holder.tv_time.setText(data.get(position).getPhase_begin_time()+"~"+data.get(position).getPhase_over_time());
        holder.tv_address.setText(data.get(position).getPfaddress());
        holder.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
                intent.putExtra("pfID", data.get(position).getPfID());
                context.startActivity(intent);
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
        if (data.get(position).getGz().equals("1")){
            holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_r);
        }else {
            holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_g);
        }
        holder.rl_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(spImp.getUID()) && !spImp.getUID().equals("0")) {
                    if(data.get(position).getGz().equals("0")){
                        ViseHttp.POST(NetConfig.userFocusUrl)
                                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.userFocusUrl))
                                .addParam("uid", spImp.getUID())
                                .addParam("likeId", data.get(position).getUserID())
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getInt("code") == 200) {
                                                data.get(position).setGz("1");
                                                notifyDataSetChanged();
                                                holder.iv_guanzhu.setImageResource(R.mipmap.hert_0324_r);
                                                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                                            }else if(jsonObject.getInt("code") == 400){

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    }
                }else {
                    intent.setClass(context, LoginActivity.class);
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
        ImageView iv_head,iv_big;
        TextView tv_name,tv_level,tv_address,tv_time;
        ImageView iv_level,iv_guanzhu;
        RelativeLayout rl_guanzhu,rl_all;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_all = itemView.findViewById(R.id.rl_all);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_level = itemView.findViewById(R.id.tv_level);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_big = itemView.findViewById(R.id.iv_big);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_time = itemView.findViewById(R.id.tv_time);
            iv_level = itemView.findViewById(R.id.iv_level);
            iv_guanzhu = itemView.findViewById(R.id.iv_guanzhu);
            rl_guanzhu = itemView.findViewById(R.id.rl_guanzhu);
        }
    }
}
