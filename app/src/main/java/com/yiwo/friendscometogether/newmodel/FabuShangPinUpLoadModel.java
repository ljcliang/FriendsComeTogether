package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class FabuShangPinUpLoadModel {

    /**
     * goodsName : 苹果
     * goodsInfo : 红色的苹果
     * spec : [{"oldPrice":20,"nowPrice":10,"spec":"横撑黄绿蓝靛紫","allNum":100},{"oldPrice":20,"nowPrice":10,"spec":"横撑黄绿蓝靛紫","allNum":100}]
     * service : 1,2,3
     * tag : 1,2,3
     */

    private String goodsName;
    private String goodsInfo;
    private String service;
    private String tag;
    private List<SpecBean> spec;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public static class SpecBean {
        /**
         * oldPrice : 20
         * nowPrice : 10
         * spec : 横撑黄绿蓝靛紫
         * allNum : 100
         */

        private int oldPrice;
        private int nowPrice;
        private String spec;
        private int allNum;

        public int getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(int oldPrice) {
            this.oldPrice = oldPrice;
        }

        public int getNowPrice() {
            return nowPrice;
        }

        public void setNowPrice(int nowPrice) {
            this.nowPrice = nowPrice;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public int getAllNum() {
            return allNum;
        }

        public void setAllNum(int allNum) {
            this.allNum = allNum;
        }
    }
}
