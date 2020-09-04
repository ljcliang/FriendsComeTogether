package com.yiwo.friendscometogether.newmodel;

public class GoodShareModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"username":"花生的铺子","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","userautograph":"嗷呜～","shareUrl":"http://www.tongbanapp.com/index.php/wxweb/wx_goods/goodsList?uid=4"}
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
         * username : 花生的铺子
         * userpic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * userautograph : 嗷呜～
         * shareUrl : http://www.tongbanapp.com/index.php/wxweb/wx_goods/goodsList?uid=4
         */

        private String username;
        private String userpic;
        private String userautograph;
        private String shareUrl;

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

        public String getUserautograph() {
            return userautograph;
        }

        public void setUserautograph(String userautograph) {
            this.userautograph = userautograph;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }
    }
}
