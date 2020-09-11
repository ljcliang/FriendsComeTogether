package com.yiwo.friendscometogether.newadapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.custom.TitleMessageOkDialog;
import com.yiwo.friendscometogether.newmodel.Fabu_Xiugai_LuXian_model;
import com.yiwo.friendscometogether.sp.SpImp;
import com.yiwo.friendscometogether.utils.CashierInputFilter;

import java.util.List;

/**
 * Created by Administrator on 2018/12/18.
 */

public class FaBuLuXianQiShuAdapter_1 extends RecyclerView.Adapter<FaBuLuXianQiShuAdapter_1.ViewHolder> {

    private Context context;
    private List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> data;
    private SpImp spImp;
    private EventListenner listenner;
    public FaBuLuXianQiShuAdapter_1(List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> data, EventListenner listenner) {
        this.data = data;
        this.listenner = listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        spImp = new SpImp(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_luxian_qishu_1, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.llAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onclick();
            }
        });
        if (position > 0){
//            holder.rl_btn_delete.setVisibility(View.VISIBLE);
            holder.rl_btn_delete.setVisibility(View.GONE);
        }else {
            holder.rl_btn_delete.setVisibility(View.GONE);
        }
//        holder.rl_btn_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listenner.deleteItem(position);
//            }
//        });
        InputFilter[] filters = {new CashierInputFilter()};
        holder.edt_input_price.setFilters(filters);
        holder.edt_input_fenxiaoticheng.setFilters(filters);
        holder.edt_input_price.setTag(position);
        holder.edt_input_fenxiaoticheng.setTag(position);
        holder.edt_input_price.setText(data.get(position).getPhase_price());
        holder.edt_input_fenxiaoticheng.setText(data.get(position).getUserBonus());
        holder.tv_input_fatuanshijian_end.setText(data.get(position).getEnd_time());
        holder.tv_input_fatuanshijian_start.setText(data.get(position).getBegin_time());
        holder.tv_input_baomingjiezhi.setText(data.get(position).getSign_up_over_time());
//        holder.tv_input_fatuanshijian_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listenner.changeStartTime(position);
//            }
//        });
//        holder.tv_input_fatuanshijian_end.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listenner.changeEndTime(position);
//            }
//        });
//        holder.tv_input_baomingjiezhi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listenner.changeApplyTime(position);
//            }
//        });
//        holder.ll_woshi_daidui.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                listenner.changeWoShiDaiDui(position);
//            }
//        });
        if (position == 0){
//            holder.iv_tishi_fenxiaoticheng.setVisibility(View.VISIBLE);
            holder.iv_tishi_fenxiaoticheng.setVisibility(View.GONE);
        }else {
            holder.iv_tishi_fenxiaoticheng.setVisibility(View.GONE);
        }
        holder.iv_woshi_daidui_check.setVisibility(data.get(position).getIfCaptain().equals("1")? View.VISIBLE: View.GONE);
        holder.iv_tishi_fenxiaoticheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleMessageOkDialog titleMessageOkDialog1 = new TitleMessageOkDialog(context, "",
                        "最小分销提成为行程售价的1%" , "知道了", new TitleMessageOkDialog.OnBtnClickListenner() {
                    @Override
                    public void onclick(Dialog dialog) {
                        dialog.dismiss();
                    }
                });
                titleMessageOkDialog1.show();
            }
        });
        holder.edt_input_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((int)holder.edt_input_price.getTag() == position) {///设置tag解决错乱问题
//                    listenner.changePrice(position,s.toString());
                }
            }
        });
//        holder.edt_input_price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                EditText et=(EditText)view;
//                if (!b) {// 失去焦点
//                    et.setHint(et.getTag().toString());
//                } else {
//                    String hint=et.getHint().toString();
//                    et.setTag(hint);//保存预设字
//                    et.setHint(null);
//                }
//            }
//        });
        holder.edt_input_fenxiaoticheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((int)holder.edt_input_fenxiaoticheng.getTag() == position) {
//                    listenner.changeFenXiaoTiCheng(position,s.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rl_btn_delete;//删除此项
        EditText edt_input_price,edt_input_fenxiaoticheng;
        TextView tv_input_fatuanshijian_start,tv_input_fatuanshijian_end,tv_input_baomingjiezhi;
        LinearLayout ll_woshi_daidui,llAll;
        ImageView iv_tishi_fenxiaoticheng,iv_woshi_daidui_check;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_btn_delete = itemView.findViewById(R.id.rl_btn_minus);
            edt_input_price = itemView.findViewById(R.id.edt_input_price);
            edt_input_fenxiaoticheng = itemView.findViewById(R.id.edt_input_fenxiaoticheng);
            tv_input_fatuanshijian_start = itemView.findViewById(R.id.tv_input_fatuanshijian_start);
            tv_input_fatuanshijian_end = itemView.findViewById(R.id.tv_input_fatuanshijian_end);
            tv_input_baomingjiezhi = itemView.findViewById(R.id.tv_input_baomingjiezhi);
            ll_woshi_daidui = itemView.findViewById(R.id.ll_woshi_daidui);
            iv_tishi_fenxiaoticheng = itemView.findViewById(R.id.iv_tishi_fenxiaoticheng);
            iv_woshi_daidui_check = itemView.findViewById(R.id.iv_woshi_daidui_check);
            llAll = itemView.findViewById(R.id.ll_all);
        }
    }
    public interface EventListenner{
        void deleteItem(int pos);
        void changeStartTime(int pos);
        void changeEndTime(int pos);
        void changeApplyTime(int pos);
        void changeWoShiDaiDui(int pos);
        void changePrice(int pos, String string);
        void changeFenXiaoTiCheng(int pos, String string);
        void onclick();
    }

}
