package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftConstant;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftType;
import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model.Gift;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzxuwen on 2016/3/29.
 */
public class GiftAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Gift> gifts;
    private boolean isAudience;

//    public GiftAdapter(Context context) {
//        super();
//        gifts = new ArrayList<Gift>();
//        inflater = LayoutInflater.from(context);
//        for (int i = 0; i < GiftConstant.images.length; i++) {
//            Gift gift = new Gift(GiftType.typeOfValue(i), GiftConstant.titles[i], 1, GiftConstant.images[i]);
//            gifts.add(gift);
//        }
//    }

    public GiftAdapter(List<Gift> gifts, Context context,boolean isAudience) {
        super();
        this.gifts = gifts;
        this.isAudience = isAudience;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (null != gifts) {
            return gifts.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return gifts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gift_item, null);
            viewHolder = new ViewHolder();
            viewHolder.root = convertView.findViewById(R.id.root);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);
            viewHolder.integral = convertView.findViewById(R.id.tv_integral);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(gifts.get(position).getTitle());
        viewHolder.image.setImageResource(gifts.get(position).getImageId());
        if (isAudience){
            viewHolder.count.setVisibility(View.GONE);
        }else {
            viewHolder.integral.setVisibility(View.GONE);
        }
        viewHolder.count.setText("*"+gifts.get(position).getCount() + "");
        if (gifts.get(position).isChoosed()){
            Log.d("asdasdasd","gifts.get(position).isChoosed()"+position+"YES");
            viewHolder.root.setBackgroundResource(R.drawable.item_border_selected);
        } else {
            Log.d("asdasdasd","gifts.get(position).isChoosed()"+position+"YES");
            viewHolder.root.setBackgroundResource(R.drawable.item_border);
        }
        viewHolder.integral.setText(gifts.get(position).getIntegral() + "瞳币");
        return convertView;
    }
}

class ViewHolder {
    public TextView title;
    public ImageView image;
    public TextView count;
    public TextView integral;
    public LinearLayout root;
}
