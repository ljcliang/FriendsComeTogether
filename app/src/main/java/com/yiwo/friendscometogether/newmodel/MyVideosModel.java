package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2019/7/5.
 */

public class MyVideosModel {


    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"vID":"240","vname":"12312312331号","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/da2b7450-0773-433f-a21e-d33f187b51bc.mp4","vtime":"2020-08-17 11:19","img":"http://vodsmnjjkoj.nosdn.127.net/da2b7450-0773-433f-a21e-d33f187b51bc_1_0_0.jpg","address":"威海","pfID":"7","gid":"7","gname":"麻将","praise_num":"0","comment_num":"0"},{"vID":"238","vname":"威海刘公岛","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/a7bfb2c0-b24d-454c-b48a-6f77901b3e75.mp4","vtime":"2020-08-10 14:35","img":"http://vodsmnjjkoj.nosdn.127.net/a7bfb2c0-b24d-454c-b48a-6f77901b3e75_1_0_0.jpg","address":"威海","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"3"}]
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

    public static class ObjBean implements Serializable {
        /**
         * vID : 240
         * vname : 12312312331号
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/da2b7450-0773-433f-a21e-d33f187b51bc.mp4
         * vtime : 2020-08-17 11:19
         * img : http://vodsmnjjkoj.nosdn.127.net/da2b7450-0773-433f-a21e-d33f187b51bc_1_0_0.jpg
         * address : 威海
         * pfID : 7
         * gid : 7
         * gname : 麻将
         * praise_num : 0
         * comment_num : 0
         * gl_type ：1
         */

        private String vID;
        private String vname;
        private String vurl;
        private String vtime;
        private String img;
        private String address;
        private String pfID;
        private String gid;
        private String gname;
        private String praise_num;
        private String comment_num;
        private String gl_type;

        public String getVID() {
            return vID;
        }

        public void setVID(String vID) {
            this.vID = vID;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getVtime() {
            return vtime;
        }

        public void setVtime(String vtime) {
            this.vtime = vtime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }

        public String getGl_type() {
            return gl_type;
        }

        public void setGl_type(String gl_type) {
            this.gl_type = gl_type;
        }
    }
}
