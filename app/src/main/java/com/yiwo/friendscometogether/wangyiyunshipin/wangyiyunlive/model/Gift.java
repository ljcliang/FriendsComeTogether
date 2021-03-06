package com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.model;


import com.yiwo.friendscometogether.wangyiyunshipin.wangyiyunlive.constant.GiftType;

/**
 * 礼物
 * Created by hzxuwen on 2016/3/29.
 */
public class Gift {
    private GiftType giftType;
    private String title;
    private int count;
    private int imageId;
    private String integral;
    private boolean isChoosed = false;
    public Gift(GiftType giftType, String title, int count, int imageId,String integral) {
        super();
        this.giftType = giftType;
        this.title = title;
        this.count = count;
        this.imageId = imageId;
        this.integral = integral;
    }

    public void setGiftType(GiftType giftType) {
        this.giftType = giftType;
    }

    public GiftType getGiftType() {
        return giftType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}
