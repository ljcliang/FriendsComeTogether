package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class ShouCangShangPinModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"userID":"130","goodsID":"6","goodsName":"油条","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200813/53f122e4088bfa9ed5cd8aaf2354e8191355.jpg","username":"随便","userpic":"http://www.tongbanapp.com/uploads/header/2020/06/11/f74265fa3ef9608713d9d2099613347a15918651678.png","price":"0.01"},{"userID":"4","goodsID":"7","goodsName":"麻将","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200817/e783605d6ab4549f6ec0eba1b9b67efb6745.jpeg","username":"花生","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","price":"1.00"},{"userID":"7","goodsID":"8","goodsName":"芦荟凝胶","goodsImg":"http://www.tongbanapp.com/uploads/goods/20200820/a2a52ed837417cf2e4718f579022ae799704.png","username":"大叔爱健身","userpic":"http://www.tongbanapp.com/uploads/header/2019/05/02/917832687f100195d0591cacbc448bf3155680934216.png","price":"0.01"}]
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
         * userID : 130
         * goodsID : 6
         * goodsName : 油条
         * goodsImg : http://www.tongbanapp.com/uploads/goods/20200813/53f122e4088bfa9ed5cd8aaf2354e8191355.jpg
         * username : 随便
         * userpic : http://www.tongbanapp.com/uploads/header/2020/06/11/f74265fa3ef9608713d9d2099613347a15918651678.png
         * price : 0.01
         * spec : 1
         */

        private String userID;
        private String goodsID;
        private String goodsName;
        private String goodsImg;
        private String username;
        private String userpic;
        private String price;
        private String spec;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getGoodsID() {
            return goodsID;
        }

        public void setGoodsID(String goodsID) {
            this.goodsID = goodsID;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
