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
import com.yiwo.friendscometogether.newmodel.HomeTuiJianModel;
import com.yiwo.friendscometogether.newmodel.HomeTuiJianYouJiShiPinModel;
import com.yiwo.friendscometogether.newpage.PersonMainActivity1;
import com.yiwo.friendscometogether.pages.ArticleCommentActivity;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.VideoActivity;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.tongban_emoticon.String2HtmlTextTools;
import com.yiwo.friendscometogether.utils.TokenUtils;
import com.yiwo.friendscometogether.utils.ViewUtil;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendTogetherWebActivity;
import com.yiwo.friendscometogether.webpages.DetailsOfFriendsWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ljc on 2020/3/25.
 */

public class HomeTuiJian_ReMenZhaoMu_Adapter extends RecyclerView.Adapter<HomeTuiJian_ReMenZhaoMu_Adapter.ViewHolder>{
    private Context context;
    private List<HomeTuiJianModel.ObjBean.YouJiBean> data;
    private SpImp spImp;
    public HomeTuiJian_ReMenZhaoMu_Adapter(List<HomeTuiJianModel.ObjBean.YouJiBean> data){
        this.data = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_youji_shipin_0326, parent, false);
//        ScreenAdapterTools.getInstance().loadView(view);
        HomeTuiJian_ReMenZhaoMu_Adapter.ViewHolder holder = new HomeTuiJian_ReMenZhaoMu_Adapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Intent intent = new Intent();
        holder.tv_name.setText(data.get(position).getUsername());
        holder.tv_level.setText("Lv."+data.get(position).getUsergrade());
        holder.tv_address.setText(data.get(position).getFmaddress());
        holder.tv_content.setText(data.get(position).getFmtitle());
        if (data.get(position).getPlList().size()>0){
            holder.tv_pinglun_name1.setText(data.get(position).getPlList().get(0).getUsername()+"：");
//            holder.tv_pinglun_comment1.setText(data.get(position).getPlList().get(0).getFctitle());
            String2HtmlTextTools.tvSetHtmlForImage(context,holder.tv_pinglun_comment1,data.get(position).getPlList().get(0).getFctitle());
            holder.ll_pinglun_comment1.setVisibility(View.VISIBLE);
            holder.tv_pinglun_num.setVisibility(View.VISIBLE);
        }else {
            holder.ll_pinglun_comment1.setVisibility(View.GONE);
            holder.tv_pinglun_num.setVisibility(View.GONE);
        }
        if (data.get(position).getPlList().size()>1){
            holder.tv_pinglun_name2.setText(data.get(position).getPlList().get(1).getUsername()+"：");
//            holder.tv_pinglun_comment2.setText(data.get(position).getPlList().get(1).getFctitle());
            String2HtmlTextTools.tvSetHtmlForImage(context,holder.tv_pinglun_comment2,data.get(position).getPlList().get(1).getFctitle());
            holder.ll_pinglun_comment2.setVisibility(View.VISIBLE);
        }else {
            holder.ll_pinglun_comment2.setVisibility(View.GONE);
        }

        holder.tv_look_num.setText(data.get(position).getFmlook());
        holder.tv_comment_num.setText(data.get(position).getCNum());
        holder.tv_pinglun_num.setText("全部"+data.get(position).getCNum()+"条评论");
        Glide.with(context).load(data.get(position).getUserpic()).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_head);
        holder.iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("person_id", data.get(position).getUserID());
                intent.setClass(context, PersonMainActivity1.class);
                context.startActivity(intent);
            }
        });
        holder.ll_pinlun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(spImp.getUID()) || spImp.getUID().equals("0")) {
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    intent.setClass(context, ArticleCommentActivity.class);
                    intent.putExtra("id", data.get(position).getFmID());
                    context.startActivity(intent);
                }
            }
        });
        if (data.get(position).getPicList().size()>0){
            Glide.with(context).load(data.get(position).getPicList().get(0)).apply(new RequestOptions().error(R.mipmap.zanwutupian).placeholder(R.mipmap.zanwutupian)).into(holder.iv_big);
        }
        if (data.get(position).getPicList().size()>1){
            Glide.with(context).load(data.get(position).getPicList().get(1)).apply(new RequestOptions().error(R.mipmap.picnull).placeholder(R.mipmap.picnull)).into(holder.iv_small_1);
        }
        if (data.get(position).getPicList().size()>2){
            Glide.with(context).load(data.get(position).getPicList().get(2)).apply(new RequestOptions().error(R.mipmap.picnull).placeholder(R.mipmap.picnull)).into(holder.iv_small_2);
        }
        if (data.get(position).getPicList().size()>3){
            Glide.with(context).load(data.get(position).getPicList().get(3)).apply(new RequestOptions().error(R.mipmap.picnull).placeholder(R.mipmap.picnull)).into(holder.iv_small_3);
        }

        holder.iv_canyu1.setVisibility(View.GONE);
        holder.iv_canyu2.setVisibility(View.GONE);
        holder.iv_canyu3.setVisibility(View.GONE);
        holder.iv_canyu4.setVisibility(View.GONE);
        holder.iv_canyu5.setVisibility(View.GONE);


        holder.ll_canyuxiezuo.setVisibility(View.GONE);

//        if (data.get(position).getInPerson().size()>0){
//            holder.ll_canyuxiezuo.setVisibility(View.VISIBLE);
//            holder.iv_canyu1.setVisibility(View.VISIBLE);
//            Glide.with(context).load(data.get(position).getInPerson().get(0)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu1);
//            if (data.get(position).getInPerson().size()>=5){
//                holder.tv_canyu.setText("···"+ data.get(position).getInPersonNum()+"人参与写作");
//            }else {
//                holder.tv_canyu.setText(""+ data.get(position).getInPersonNum()+"人参与写作");
//            }
//        }else {
//            holder.ll_canyuxiezuo.setVisibility(View.GONE);
//        }
//        if (data.get(position).getInPerson().size()>1){
//            holder.iv_canyu2.setVisibility(View.VISIBLE);
//            Glide.with(context).load(data.get(position).getInPerson().get(1)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu2);
//        }
//        if (data.get(position).getInPerson().size()>2){
//            holder.iv_canyu3.setVisibility(View.VISIBLE);
//            Glide.with(context).load(data.get(position).getInPerson().get(2)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu3);
//        }
//        if (data.get(position).getInPerson().size()>3){
//            holder.iv_canyu4.setVisibility(View.VISIBLE);
//            Glide.with(context).load(data.get(position).getInPerson().get(3)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu4);
//        }
//        if (data.get(position).getInPerson().size()>4){
//            holder.iv_canyu5.setVisibility(View.VISIBLE);
//            Glide.with(context).load(data.get(position).getInPerson().get(4)).apply(new RequestOptions().error(R.mipmap.my_head).placeholder(R.mipmap.my_head)).into(holder.iv_canyu5);
//        }
//
//        if (data.get(position).getIfCaptain().equals("1")){
//            holder.iv_level.setVisibility(View.VISIBLE);
//        }else {
//            holder.iv_level.setVisibility(View.GONE);
//        }
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
        holder.iv_play.setVisibility(View.GONE);
//        if (data.get(position).getTp().equals("0")){
//            holder.iv_play.setVisibility(View.GONE);
//        }else {
//            holder.iv_play.setVisibility(View.VISIBLE);
//        }
        holder.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    intent.setClass(context, DetailsOfFriendsWebActivity.class);
                    intent.putExtra("fmid", data.get(position).getFmID());
                    context.startActivity(intent);
//                if (data.get(position).getTp().equals("0")){
//                    intent.setClass(context, DetailsOfFriendsWebActivity.class);
//                    intent.putExtra("fmid", data.get(position).getFmID());
//                    context.startActivity(intent);
//                }else {
//                    intent.setClass(context, VideoActivity.class);
//                    intent.putExtra("videoUrl", data.get(position).getPalyUrl());
//                    intent.putExtra("title", data.get(position).getFmtitle());
//                    intent.putExtra("picUrl", data.get(position).getPicList().get(0));
//                    intent.putExtra("vid", data.get(position).getFmID());
//                    context.startActivity(intent);
//                }
            }
        });
        if (TextUtils.isEmpty(data.get(position).getFmpartyID())){
            holder.ll_xiangguan_huodong.setVisibility(View.GONE);
        }else {
            holder.ll_xiangguan_huodong.setVisibility(View.VISIBLE);
        }
        holder.tv_huodong.setText(data.get(position).getPftitle());
        holder.ll_xiangguan_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(data.get(position).getFmpartyID())){
                    intent.setClass(context, DetailsOfFriendTogetherWebActivity.class);
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
        ImageView iv_small_1;
        ImageView iv_small_2;
        ImageView iv_small_3;
        ImageView iv_big;
        ImageView iv_head;
        TextView tv_name,tv_level,tv_address,tv_content,tv_huodong,tv_canyu,tv_pinglun_num,tv_look_num,
                            tv_comment_num,tv_pinglun_name1,tv_pinglun_comment1,tv_pinglun_name2,tv_pinglun_comment2;
        ImageView iv_level,iv_guanzhu,iv_canyu1,iv_canyu2,iv_canyu3,iv_canyu4,iv_canyu5,iv_play;
        LinearLayout ll_pinglun_comment1,ll_pinglun_comment2,ll_pinlun,ll_xiangguan_huodong,ll_canyuxiezuo,ll_all;
        RelativeLayout rl_guanzhu;
        int smallIvWidth;
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
            tv_pinglun_num = itemView.findViewById(R.id.tv_pinglun_num);
            tv_look_num = itemView.findViewById(R.id.tv_look_num);
            tv_comment_num = itemView.findViewById(R.id.tv_comment_num);
            tv_pinglun_name1 = itemView.findViewById(R.id.tv_pinglun_name1);
            tv_pinglun_comment1 = itemView.findViewById(R.id.tv_pinglun_comment1);
            tv_pinglun_name2 = itemView.findViewById(R.id.tv_pinglun_name2);
            tv_pinglun_comment2 = itemView.findViewById(R.id.tv_pinglun_comment2);
            iv_level = itemView.findViewById(R.id.iv_level);
            iv_guanzhu = itemView.findViewById(R.id.iv_guanzhu);
            iv_canyu1 = itemView.findViewById(R.id.iv_canyu1);
            iv_canyu2 = itemView.findViewById(R.id.iv_canyu2);
            iv_canyu3 = itemView.findViewById(R.id.iv_canyu3);
            iv_canyu4 = itemView.findViewById(R.id.iv_canyu4);
            iv_canyu5 = itemView.findViewById(R.id.iv_canyu5);
            iv_play = itemView.findViewById(R.id.iv_play);
            ll_pinglun_comment1 = itemView.findViewById(R.id.ll_comment1);
            ll_pinglun_comment2 = itemView.findViewById(R.id.ll_comment2);
            ll_pinlun = itemView.findViewById(R.id.ll_pinlun);
            ll_xiangguan_huodong = itemView.findViewById(R.id.ll_xiangguan_huodong);
            ll_canyuxiezuo = itemView.findViewById(R.id.ll_canyuxiezuo);
            rl_guanzhu = itemView.findViewById(R.id.rl_guanzhu);
            ll_all= itemView.findViewById(R.id.ll_top);

            iv_small_1 = itemView.findViewById(R.id.iv_small_1);
            final ViewGroup.LayoutParams layoutParams = iv_small_1.getLayoutParams();
            ViewUtil.getViewWidth(iv_small_1, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    smallIvWidth = width;
                    Log.d("iv_small_1",layoutParams.width+"////"+layoutParams.height);
                    iv_small_1.setLayoutParams(layoutParams);
                }
            });
            iv_small_2 = itemView.findViewById(R.id.iv_small_2);
            ViewUtil.getViewWidth(iv_small_2, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    Log.d("iv_small_2",layoutParams.width+"////"+layoutParams.height);
                    iv_small_2.setLayoutParams(layoutParams);
                }
            });
            iv_small_3 = itemView.findViewById(R.id.iv_small_3);
            ViewUtil.getViewWidth(iv_small_3, new ViewUtil.OnViewListener() {
                @Override
                public void onView(int width, int height) {
                    layoutParams.width = width;
                    layoutParams.height = width;
                    Log.d("iv_small_2",layoutParams.width+"////"+layoutParams.height);
                    iv_small_3.setLayoutParams(layoutParams);
                }
            });
        }
    }
}
