package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class HomeYouPuModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"userID":"1","username":"大圣的铺子","usergrade":"1","userpic":"http://www.tongbanapp.com/uploads/header/2019/04/11/086069342517a5fd78c5dd535098480315549760838.png","levelName":"0","goodsName":"大西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","goodsID":"1","shopUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=1&myID=4","price":"0.01","starNum":"5","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=1&uid=4"},{"userID":"133","username":"鲁迅先生的铺子","usergrade":"2","userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","levelName":"0","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","goodsID":"2","shopUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=133&myID=4","price":"0.01","starNum":"5","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=2&uid=4"},{"userID":"1","username":"大圣的铺子","usergrade":"1","userpic":"http://www.tongbanapp.com/uploads/header/2019/04/11/086069342517a5fd78c5dd535098480315549760838.png","levelName":"0","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","goodsID":"3","shopUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=1&myID=4","price":"0.01","starNum":"5","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=3&uid=4"},{"userID":"133","username":"鲁迅先生的铺子","usergrade":"2","userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","levelName":"0","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","goodsID":"4","shopUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=133&myID=4","price":"0.01","starNum":"4","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=4&uid=4"}]
     */

    private int code;
    private String message;
    private List<ObjBean> obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * userID : 1
         * username : 大圣的铺子
         * usergrade : 1
         * userpic : http://www.tongbanapp.com/uploads/header/2019/04/11/086069342517a5fd78c5dd535098480315549760838.png
         * levelName : 0
         * goodsName : 大西瓜
         * goodsImg : http://www.tongbanapp.com/uploads/goods/20200204/1.jpg
         * goodsID : 1
         * shopUrl : http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=1&myID=4
         * price : 0.01
         * starNum : 5
         * goodsUrl : http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=1&uid=4
         */

        private String userID;
        private String username;
        private String usergrade;
        private String userpic;
        private String levelName;
        private String goodsName;
        private String goodsImg;
        private String goodsID;
        private String shopUrl;
        private String price;
        private String starNum;
        private String goodsUrl;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
        }

        public String getShopUrl() {
            return shopUrl;
        }

        public void setShopUrl(String shopUrl) {
            this.shopUrl = shopUrl;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStarNum() {
            return starNum;
        }

        public void setStarNum(String starNum) {
            this.starNum = starNum;
        }

        public String getGoodsUrl() {
            return goodsUrl;
        }

        public void setGoodsUrl(String goodsUrl) {
            this.goodsUrl = goodsUrl;
        }
    }
}
