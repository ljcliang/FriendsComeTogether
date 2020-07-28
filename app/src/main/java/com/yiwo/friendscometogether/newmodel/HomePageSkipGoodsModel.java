package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class HomePageSkipGoodsModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"kefu":"0","gid":"24","goodsName":"红包","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/c254efcc66bb814680e04ea4231bc6e65654.jpg","price":"0.01","preferential":"0.01","userID":"130","username":"随便","userpic":"http://www.tongbanapp.com/uploads/header/2020/06/11/f74265fa3ef9608713d9d2099613347a15918651678.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"1","sf":"1","starNum":"4"},{"kefu":"0","gid":"20","goodsName":"苹果12345","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/43fb832d2364918fc5fcca3d6b61c8a31099.jpg","price":"10.00","preferential":"10.00","userID":"130","username":"随便","userpic":"http://www.tongbanapp.com/uploads/header/2020/06/11/f74265fa3ef9608713d9d2099613347a15918651678.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"1","sf":"1","starNum":"4"},{"kefu":"0","gid":"19","goodsName":"测试编辑商品修改1","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200428/6c9a7bdae077db74f5e367a6849c733c4630.jpg","price":"99.00","preferential":"99.00","userID":"4","username":"花生","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"1","sf":"1","starNum":"4"},{"kefu":"0","gid":"18","goodsName":"康师傅老坛酸菜面","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200427/9b2ab2939cb5c8a4eeff093ba5dee8f05059.jpeg","price":"3.00","preferential":"3.00","userID":"7","username":"大叔爱健身","userpic":"http://www.tongbanapp.com/uploads/header/2019/05/02/917832687f100195d0591cacbc448bf3155680934216.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"0","sf":"1","starNum":"4"},{"kefu":"0","gid":"15","goodsName":"炖猫","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/a07dfacdc84109f9fd9f91aa4371e95c9161.jpg","price":"123.00","preferential":"123.00","userID":"4","username":"花生","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"1","sf":"1","starNum":"4"},{"kefu":"0","gid":"14","goodsName":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200417/87078c4fb1765cb00aabf243e25787298614.jpg","price":"1.00","preferential":"1.00","userID":"4","username":"花生","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","shenfen":"领队","usercontract":"1","levelName":"0","usergrade":"1","sf":"1","starNum":"4"},{"kefu":"0","gid":"4","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.01","preferential":"0.01","userID":"133","username":"鲁迅先生","userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","shenfen":"Lv.2","usercontract":"0","levelName":"0","usergrade":"2","sf":"0","starNum":"3"},{"kefu":"0","gid":"3","goodsName":"苹果","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/pg1.jpg","price":"0.01","preferential":"0.01","userID":"1","username":"大圣","userpic":"http://www.tongbanapp.com/uploads/header/2019/04/11/086069342517a5fd78c5dd535098480315549760838.png","shenfen":"Lv.0","usercontract":"0","levelName":"0","usergrade":"0","sf":"0","starNum":"5"},{"kefu":"0","gid":"2","goodsName":"西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.01","preferential":"0.01","userID":"133","username":"鲁迅先生","userpic":"http://www.tongbanapp.com/uploads/header/2020/01/10/2d97242b826a618467a34c643b1a2e9c15786299672.png","shenfen":"Lv.2","usercontract":"0","levelName":"0","usergrade":"2","sf":"0","starNum":"5"},{"kefu":"0","gid":"1","goodsName":"大西瓜","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200204/1.jpg","price":"0.01","preferential":"0.01","userID":"1","username":"大圣","userpic":"http://www.tongbanapp.com/uploads/header/2019/04/11/086069342517a5fd78c5dd535098480315549760838.png","shenfen":"Lv.0","usercontract":"0","levelName":"0","usergrade":"0","sf":"0","starNum":"3"}]
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
         * kefu : 0
         * gid : 24
         * goodsName : 红包
         * goodsImg : http://www.tongbanapp.com/uploads/goods/20200428/c254efcc66bb814680e04ea4231bc6e65654.jpg
         * price : 0.01
         * preferential : 0.01
         * userID : 130
         * username : 随便
         * userpic : http://www.tongbanapp.com/uploads/header/2020/06/11/f74265fa3ef9608713d9d2099613347a15918651678.png
         * shenfen : 领队
         * usercontract : 1
         * levelName : 0
         * usergrade : 1
         * sf : 1
         * starNum : 4
         */

        private String kefu;
        private String gid;
        private String goodsName;
        private String goodsImg;
        private String price;
        private String preferential;
        private String userID;
        private String username;
        private String userpic;
        private String shenfen;
        private String usercontract;
        private String levelName;
        private String usergrade;
        private String sf;
        private String starNum;

        public String getKefu() {
            return kefu;
        }

        public void setKefu(String kefu) {
            this.kefu = kefu;
        }

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPreferential() {
            return preferential;
        }

        public void setPreferential(String preferential) {
            this.preferential = preferential;
        }

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

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getShenfen() {
            return shenfen;
        }

        public void setShenfen(String shenfen) {
            this.shenfen = shenfen;
        }

        public String getUsercontract() {
            return usercontract;
        }

        public void setUsercontract(String usercontract) {
            this.usercontract = usercontract;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getSf() {
            return sf;
        }

        public void setSf(String sf) {
            this.sf = sf;
        }

        public String getStarNum() {
            return starNum;
        }

        public void setStarNum(String starNum) {
            this.starNum = starNum;
        }
    }
}
