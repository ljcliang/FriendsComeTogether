package com.yiwo.friendscometogether.newmodel;

import java.util.List;

/**
 * Created by ljc on 2019/3/11.
 */

public class WoDeChaWenModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"ffID":"405","fmID":"2424","fftitle":"查问插文插文","ffcontect":"babawwww","ffptime":"2020-04-23 14:10:24","position":"","radio":"1","reason":"","piclist":[{"ffpID":"2128","pictitle":"","picurl":"http://www.tongbanapp.com/uploads/article/20200423/CR-6s6s8iDbqZUB.jpg"}],"fromUserPic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","newstitle":"云朵～","activity_name":""}]
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
         * ffID : 405
         * fmID : 2424
         * fftitle : 查问插文插文
         * ffcontect : babawwww
         * ffptime : 2020-04-23 14:10:24
         * position :
         * radio : 1
         * reason :
         * piclist : [{"ffpID":"2128","pictitle":"","picurl":"http://www.tongbanapp.com/uploads/article/20200423/CR-6s6s8iDbqZUB.jpg"}]
         * fromUserPic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * newstitle : 云朵～
         * activity_name :
         */

        private String ffID;
        private String fmID;
        private String fftitle;
        private String ffcontect;
        private String ffptime;
        private String position;
        private String radio;
        private String reason;
        private String fromUserPic;
        private String newstitle;
        private String activity_name;
        private List<PiclistBean> piclist;

        public String getFfID() {
            return ffID;
        }

        public void setFfID(String ffID) {
            this.ffID = ffID;
        }

        public String getFmID() {
            return fmID;
        }

        public void setFmID(String fmID) {
            this.fmID = fmID;
        }

        public String getFftitle() {
            return fftitle;
        }

        public void setFftitle(String fftitle) {
            this.fftitle = fftitle;
        }

        public String getFfcontect() {
            return ffcontect;
        }

        public void setFfcontect(String ffcontect) {
            this.ffcontect = ffcontect;
        }

        public String getFfptime() {
            return ffptime;
        }

        public void setFfptime(String ffptime) {
            this.ffptime = ffptime;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRadio() {
            return radio;
        }

        public void setRadio(String radio) {
            this.radio = radio;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getFromUserPic() {
            return fromUserPic;
        }

        public void setFromUserPic(String fromUserPic) {
            this.fromUserPic = fromUserPic;
        }

        public String getNewstitle() {
            return newstitle;
        }

        public void setNewstitle(String newstitle) {
            this.newstitle = newstitle;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public List<PiclistBean> getPiclist() {
            return piclist;
        }

        public void setPiclist(List<PiclistBean> piclist) {
            this.piclist = piclist;
        }

        public static class PiclistBean {
            /**
             * ffpID : 2128
             * pictitle :
             * picurl : http://www.tongbanapp.com/uploads/article/20200423/CR-6s6s8iDbqZUB.jpg
             */

            private String ffpID;
            private String pictitle;
            private String picurl;

            public String getFfpID() {
                return ffpID;
            }

            public void setFfpID(String ffpID) {
                this.ffpID = ffpID;
            }

            public String getPictitle() {
                return pictitle;
            }

            public void setPictitle(String pictitle) {
                this.pictitle = pictitle;
            }

            public String getPicurl() {
                return picurl;
            }

            public void setPicurl(String picurl) {
                this.picurl = picurl;
            }
        }
    }
}
