package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/26.
 */

public class UserRememberModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"fmID":"2424","fmtitle":"云朵～","fmpic":"http://www.tongbanapp.com/uploads/article/20200421/0-d0f72cea9667c70f5bb8861e420c209b8151.jpeg","fmgotime":"","fmendtime":"","percapitacost":"","fmlook":"7","fmfavorite":"0","fmtime":"2020-04-21 13:47","fm_small_img":"uploads/articlesmall/20200421/CR-MwYp4RMFQyn0.jpeg","fmpartyID":"0","inNum":"0","pftitle":"测试假数据测试假数据测试假数据"}]
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
         * fmID : 2424
         * fmtitle : 云朵～
         * fmpic : http://www.tongbanapp.com/uploads/article/20200421/0-d0f72cea9667c70f5bb8861e420c209b8151.jpeg
         * fmgotime :
         * fmendtime :
         * percapitacost :
         * fmlook : 7
         * fmfavorite : 0
         * fmtime : 2020-04-21 13:47
         * fm_small_img : uploads/articlesmall/20200421/CR-MwYp4RMFQyn0.jpeg
         * fmpartyID : 0
         * inNum : 0
         * pftitle : 测试假数据测试假数据测试假数据
         */

        private String fmID;
        private String fmtitle;
        private String fmpic;
        private String fmgotime;
        private String fmendtime;
        private String percapitacost;
        private String fmlook;
        private String fmfavorite;
        private String fmtime;
        private String fm_small_img;
        private String fmpartyID;
        private String inNum;
        private String pftitle;

        public String getFmID() {
            return fmID;
        }

        public void setFmID(String fmID) {
            this.fmID = fmID;
        }

        public String getFmtitle() {
            return fmtitle;
        }

        public void setFmtitle(String fmtitle) {
            this.fmtitle = fmtitle;
        }

        public String getFmpic() {
            return fmpic;
        }

        public void setFmpic(String fmpic) {
            this.fmpic = fmpic;
        }

        public String getFmgotime() {
            return fmgotime;
        }

        public void setFmgotime(String fmgotime) {
            this.fmgotime = fmgotime;
        }

        public String getFmendtime() {
            return fmendtime;
        }

        public void setFmendtime(String fmendtime) {
            this.fmendtime = fmendtime;
        }

        public String getPercapitacost() {
            return percapitacost;
        }

        public void setPercapitacost(String percapitacost) {
            this.percapitacost = percapitacost;
        }

        public String getFmlook() {
            return fmlook;
        }

        public void setFmlook(String fmlook) {
            this.fmlook = fmlook;
        }

        public String getFmfavorite() {
            return fmfavorite;
        }

        public void setFmfavorite(String fmfavorite) {
            this.fmfavorite = fmfavorite;
        }

        public String getFmtime() {
            return fmtime;
        }

        public void setFmtime(String fmtime) {
            this.fmtime = fmtime;
        }

        public String getFm_small_img() {
            return fm_small_img;
        }

        public void setFm_small_img(String fm_small_img) {
            this.fm_small_img = fm_small_img;
        }

        public String getFmpartyID() {
            return fmpartyID;
        }

        public void setFmpartyID(String fmpartyID) {
            this.fmpartyID = fmpartyID;
        }

        public String getInNum() {
            return inNum;
        }

        public void setInNum(String inNum) {
            this.inNum = inNum;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }
    }
}
