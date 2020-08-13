package com.yiwo.friendscometogether.model;

import java.util.List;

/**
 * Created by Administrator on 2018/7/24.
 */

public class UserModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"headeimg":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生的瞳伴店铺","sex":"0","useraddress":"黑龙江省-哈尔滨市","userautograph":"嗷呜～","userbirthday":"1995-02-05","usertime":"2019-04-11 14:12:32","usercodeok":"已认证","usermarry":"1","usergrade":"1","sign":"1","vip":"0","news":"0","Friendnote":"13","Focusonnews":"37","Activitymessage":"0","type":"0","levelName":"0","message":["青铜：获得开启商铺，发布行程功能。","白银：累计发布50篇友记，15个视频，获得100个赞、50个评论，完成带队任务5次，获得平台线路带队功能。","黄金：累计发布100篇友记，20个视频，获得200个赞、100个评论，完成带队任务10次，获得直播功能。","铂金：累计发布200篇友记，30个视频，获得500个赞、200个评论，完成带队任务15次。","钻石：累计发布300篇友记，50个视频，获得800个赞、300个评论，完成带队任务30次。","王冠：累计发布500篇友记，80个视频，获得1000个赞、500个评论，完成带队任务50次。"]}
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
         * headeimg : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * username : 花生的瞳伴店铺
         * sex : 0
         * useraddress : 黑龙江省-哈尔滨市
         * userautograph : 嗷呜～
         * userbirthday : 1995-02-05
         * usertime : 2019-04-11 14:12:32
         * usercodeok : 已认证
         * usermarry : 1
         * usergrade : 1
         * sign : 1
         * vip : 0
         * news : 0
         * Friendnote : 13
         * Focusonnews : 37
         * Activitymessage : 0
         * type : 0
         * levelName : 0
         * message : ["青铜：获得开启商铺，发布行程功能。","白银：累计发布50篇友记，15个视频，获得100个赞、50个评论，完成带队任务5次，获得平台线路带队功能。","黄金：累计发布100篇友记，20个视频，获得200个赞、100个评论，完成带队任务10次，获得直播功能。","铂金：累计发布200篇友记，30个视频，获得500个赞、200个评论，完成带队任务15次。","钻石：累计发布300篇友记，50个视频，获得800个赞、300个评论，完成带队任务30次。","王冠：累计发布500篇友记，80个视频，获得1000个赞、500个评论，完成带队任务50次。"]
         */

        private String headeimg;
        private String username;
        private String sex;
        private String useraddress;
        private String userautograph;
        private String userbirthday;
        private String usertime;
        private String usercodeok;
        private String usermarry;
        private String usergrade;
        private String sign;
        private String vip;
        private String news;
        private String Friendnote;
        private String Focusonnews;
        private String Activitymessage;
        private String type;
        private String levelName;
        private List<String> message;

        public String getHeadeimg() {
            return headeimg;
        }

        public void setHeadeimg(String headeimg) {
            this.headeimg = headeimg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUseraddress() {
            return useraddress;
        }

        public void setUseraddress(String useraddress) {
            this.useraddress = useraddress;
        }

        public String getUserautograph() {
            return userautograph;
        }

        public void setUserautograph(String userautograph) {
            this.userautograph = userautograph;
        }

        public String getUserbirthday() {
            return userbirthday;
        }

        public void setUserbirthday(String userbirthday) {
            this.userbirthday = userbirthday;
        }

        public String getUsertime() {
            return usertime;
        }

        public void setUsertime(String usertime) {
            this.usertime = usertime;
        }

        public String getUsercodeok() {
            return usercodeok;
        }

        public void setUsercodeok(String usercodeok) {
            this.usercodeok = usercodeok;
        }

        public String getUsermarry() {
            return usermarry;
        }

        public void setUsermarry(String usermarry) {
            this.usermarry = usermarry;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getNews() {
            return news;
        }

        public void setNews(String news) {
            this.news = news;
        }

        public String getFriendnote() {
            return Friendnote;
        }

        public void setFriendnote(String Friendnote) {
            this.Friendnote = Friendnote;
        }

        public String getFocusonnews() {
            return Focusonnews;
        }

        public void setFocusonnews(String Focusonnews) {
            this.Focusonnews = Focusonnews;
        }

        public String getActivitymessage() {
            return Activitymessage;
        }

        public void setActivitymessage(String Activitymessage) {
            this.Activitymessage = Activitymessage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public List<String> getMessage() {
            return message;
        }

        public void setMessage(List<String> message) {
            this.message = message;
        }
    }
}
