package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class ShopHomeModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"userInfo":{"userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","username":"鲁迅先生的铺子","usergrade":"2","levelName":"0","likeUser":"0","shareUrl":"http://www.tongbanapp.com/index.php/wxweb/wx_goods/goodsList?uid=133","shareInfo":"欢迎进店铺逛逛","bgm":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png"},"goodsList":[{"gid":"2","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","preferential":"0.01","price":"0.01","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=2&uid=5","commentNum":"1"}]}
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * userInfo : {"userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","username":"鲁迅先生的铺子","usergrade":"2","levelName":"0","likeUser":"0","shareUrl":"http://www.tongbanapp.com/index.php/wxweb/wx_goods/goodsList?uid=133","shareInfo":"欢迎进店铺逛逛","bgm":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png"}
         * goodsList : [{"gid":"2","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","preferential":"0.01","price":"0.01","goodsUrl":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=2&uid=5","commentNum":"1"}]
         */

        private UserInfoBean userInfo;
        private List<GoodsListBean> goodsList;

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class UserInfoBean {
            /**
             * userpic : http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png
             * username : 鲁迅先生的铺子
             * usergrade : 2
             * levelName : 0
             * likeUser : 0
             * shareUrl : http://www.tongbanapp.com/index.php/wxweb/wx_goods/goodsList?uid=133
             * shareInfo : 欢迎进店铺逛逛
             * bgm : http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png
             */

            private String userpic;
            private String username;
            private String usergrade;
            private String levelName;
            private String likeUser;
            private String shareUrl;
            private String shareInfo;
            private String bgm;

            public String getUserpic() {
                return userpic;
            }

            public void setUserpic(String userpic) {
                this.userpic = userpic;
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

            public String getLevelName() {
                return levelName;
            }

            public void setLevelName(String levelName) {
                this.levelName = levelName;
            }

            public String getLikeUser() {
                return likeUser;
            }

            public void setLikeUser(String likeUser) {
                this.likeUser = likeUser;
            }

            public String getShareUrl() {
                return shareUrl;
            }

            public void setShareUrl(String shareUrl) {
                this.shareUrl = shareUrl;
            }

            public String getShareInfo() {
                return shareInfo;
            }

            public void setShareInfo(String shareInfo) {
                this.shareInfo = shareInfo;
            }

            public String getBgm() {
                return bgm;
            }

            public void setBgm(String bgm) {
                this.bgm = bgm;
            }
        }

        public static class GoodsListBean {
            /**
             * gid : 2
             * goodsName : 西瓜
             * goodsImg : http://www.tongbanapp.com/uploads/goods/20200204/1.jpg
             * preferential : 0.01
             * price : 0.01
             * goodsUrl : http://www.tongbanapp.com/index.php/action/ac_goods/goodsInfo?goodsID=2&uid=5
             * commentNum : 1
             */

            private String gid;
            private String goodsName;
            private String goodsImg;
            private String preferential;
            private String price;
            private String goodsUrl;
            private String commentNum;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
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

            public String getPreferential() {
                return preferential;
            }

            public void setPreferential(String preferential) {
                this.preferential = preferential;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoodsUrl() {
                return goodsUrl;
            }

            public void setGoodsUrl(String goodsUrl) {
                this.goodsUrl = goodsUrl;
            }

            public String getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(String commentNum) {
                this.commentNum = commentNum;
            }
        }
    }
}
