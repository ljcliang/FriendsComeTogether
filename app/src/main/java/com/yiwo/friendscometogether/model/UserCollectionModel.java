package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */

public class UserCollectionModel {
    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"fID":"67","ftype":"2","ftableid":"2111","ftitle":"2020 0108 芽庄第一飞","fpic":"http://www.tongbanapp.com/uploads/article/20200109/0-2cf4cb1d8e3eb4ae7b077763c00ddfec9682.jpeg","ftime":"2020-01-09 10:08:03","fmlook":"1246","fmpartyID":"0","pftitle":"无","inNum":0,"userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生","usergrade":"1","levelName":"0"}]
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
         * fID : 67
         * ftype : 2
         * ftableid : 2111
         * ftitle : 2020 0108 芽庄第一飞
         * fpic : http://www.tongbanapp.com/uploads/article/20200109/0-2cf4cb1d8e3eb4ae7b077763c00ddfec9682.jpeg
         * ftime : 2020-01-09 10:08:03
         * fmlook : 1246
         * fmpartyID : 0
         * pftitle : 无
         * inNum : 0
         * userpic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * username : 花生
         * usergrade : 1
         * levelName : 0
         */

        private String fID;
        private String ftype;
        private String ftableid;
        private String ftitle;
        private String fpic;
        private String ftime;
        private String fmlook;
        private String fmpartyID;
        private String pftitle;
        private int inNum;
        private String userpic;
        private String username;
        private String usergrade;
        private String levelName;

        public String getFID() {
            return fID;
        }

        public void setFID(String fID) {
            this.fID = fID;
        }

        public String getFtype() {
            return ftype;
        }

        public void setFtype(String ftype) {
            this.ftype = ftype;
        }

        public String getFtableid() {
            return ftableid;
        }

        public void setFtableid(String ftableid) {
            this.ftableid = ftableid;
        }

        public String getFtitle() {
            return ftitle;
        }

        public void setFtitle(String ftitle) {
            this.ftitle = ftitle;
        }

        public String getFpic() {
            return fpic;
        }

        public void setFpic(String fpic) {
            this.fpic = fpic;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getFmlook() {
            return fmlook;
        }

        public void setFmlook(String fmlook) {
            this.fmlook = fmlook;
        }

        public String getFmpartyID() {
            return fmpartyID;
        }

        public void setFmpartyID(String fmpartyID) {
            this.fmpartyID = fmpartyID;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public int getInNum() {
            return inNum;
        }

        public void setInNum(int inNum) {
            this.inNum = inNum;
        }

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
    }
}
