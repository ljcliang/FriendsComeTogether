package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class DuiZhangZhuanShuModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"mesMission":"","mesQuestion":"","mesPictxt":"","mesArea":"","mesGuess":"","mesGroup":"","mesWx":"","mesBank":"","goodsShop":"http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=4&myID=4","comeInInfo":"http://www.tongbanapp.com/index.php/action/ac_captainMission/comeInInfo?uid=4","info":"芽庄市是越南中南部沿海城市，以其质朴的海滩和卓越的潜水环境迅速成为受欢迎的国际旅游目的地，依山傍海、林木郁郁葱葱，芽庄湾是世界最美丽的海湾之一。","shareUrl":"http://www.tongbanapp.com/index.php/action/ac_activity/youJuWebShare?pfID=85","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20191223/18aa8a62c5bf43ee78302fad6aa25c21.jpg","pftitle":"玩转芽庄8日游","phase_id":"523","pfID":"85","beginTime":"2020-12-31","endTime":"2020-12-31","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png","username":"花生","usergrade":"1","levelName":"0","shopName":[{"id":"1","shopname":"哈尔滨友来友约文化传媒有限公司","checkIn":"1"},{"id":"15","shopname":"哈尔滨观光国际旅行社有限公司","checkIn":"1"}],"zbTime":"2025-01-01 00:00","jindu":"0%","ifover":"0","mission":[{"valname":"0/1","name":"完成带队出团","type":"0","button":"","status":""},{"valname":"0/5","name":"发布相应图文","type":"1","button":"去完成","status":"0"},{"valname":"0/2","name":"发布相应视频","type":"2","button":"去完成","status":"0"},{"valname":"0/20分钟","name":"完成一次直播","type":"3","button":"去完成","status":"0"},{"valname":"0/1","name":"分享带队活动","type":"4","button":"去分享","status":"0"}]}
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
         * mesMission :
         * mesQuestion :
         * mesPictxt :
         * mesArea :
         * mesGuess :
         * mesGroup :
         * mesWx :
         * mesBank :
         * goodsShop : http://www.tongbanapp.com/index.php/action/ac_goods/goodsList?uid=4&myID=4
         * comeInInfo : http://www.tongbanapp.com/index.php/action/ac_captainMission/comeInInfo?uid=4
         * info : 芽庄市是越南中南部沿海城市，以其质朴的海滩和卓越的潜水环境迅速成为受欢迎的国际旅游目的地，依山傍海、林木郁郁葱葱，芽庄湾是世界最美丽的海湾之一。
         * shareUrl : http://www.tongbanapp.com/index.php/action/ac_activity/youJuWebShare?pfID=85
         * pfpic : http://www.tongbanapp.com/uploads/xingcheng/20191223/18aa8a62c5bf43ee78302fad6aa25c21.jpg
         * pftitle : 玩转芽庄8日游
         * phase_id : 523
         * pfID : 85
         * beginTime : 2020-12-31
         * endTime : 2020-12-31
         * userpic : http://www.tongbanapp.com/uploads/header/2019/12/31/ab17a31d0c6d8acc7fa5d64847390bee157776891312.png
         * username : 花生
         * usergrade : 1
         * levelName : 0
         * shopName : [{"id":"1","shopname":"哈尔滨友来友约文化传媒有限公司","checkIn":"1"},{"id":"15","shopname":"哈尔滨观光国际旅行社有限公司","checkIn":"1"}]
         * zbTime : 2025-01-01 00:00
         * jindu : 0%
         * ifover : 0
         * mission : [{"valname":"0/1","name":"完成带队出团","type":"0","button":"","status":""},{"valname":"0/5","name":"发布相应图文","type":"1","button":"去完成","status":"0"},{"valname":"0/2","name":"发布相应视频","type":"2","button":"去完成","status":"0"},{"valname":"0/20分钟","name":"完成一次直播","type":"3","button":"去完成","status":"0"},{"valname":"0/1","name":"分享带队活动","type":"4","button":"去分享","status":"0"}]
         */

        private String mesMission;
        private String mesQuestion;
        private String mesPictxt;
        private String mesArea;
        private String mesGuess;
        private String mesGroup;
        private String mesWx;
        private String mesBank;
        private String goodsShop;
        private String comeInInfo;
        private String info;
        private String shareUrl;
        private String pfpic;
        private String pftitle;
        private String phase_id;
        private String pfID;
        private String beginTime;
        private String endTime;
        private String userpic;
        private String username;
        private String usergrade;
        private String levelName;
        private String zbTime;
        private String jindu;
        private String ifover;
        private List<ShopNameBean> shopName;
        private List<MissionBean> mission;

        public String getMesMission() {
            return mesMission;
        }

        public void setMesMission(String mesMission) {
            this.mesMission = mesMission;
        }

        public String getMesQuestion() {
            return mesQuestion;
        }

        public void setMesQuestion(String mesQuestion) {
            this.mesQuestion = mesQuestion;
        }

        public String getMesPictxt() {
            return mesPictxt;
        }

        public void setMesPictxt(String mesPictxt) {
            this.mesPictxt = mesPictxt;
        }

        public String getMesArea() {
            return mesArea;
        }

        public void setMesArea(String mesArea) {
            this.mesArea = mesArea;
        }

        public String getMesGuess() {
            return mesGuess;
        }

        public void setMesGuess(String mesGuess) {
            this.mesGuess = mesGuess;
        }

        public String getMesGroup() {
            return mesGroup;
        }

        public void setMesGroup(String mesGroup) {
            this.mesGroup = mesGroup;
        }

        public String getMesWx() {
            return mesWx;
        }

        public void setMesWx(String mesWx) {
            this.mesWx = mesWx;
        }

        public String getMesBank() {
            return mesBank;
        }

        public void setMesBank(String mesBank) {
            this.mesBank = mesBank;
        }

        public String getGoodsShop() {
            return goodsShop;
        }

        public void setGoodsShop(String goodsShop) {
            this.goodsShop = goodsShop;
        }

        public String getComeInInfo() {
            return comeInInfo;
        }

        public void setComeInInfo(String comeInInfo) {
            this.comeInInfo = comeInInfo;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPhase_id() {
            return phase_id;
        }

        public void setPhase_id(String phase_id) {
            this.phase_id = phase_id;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
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

        public String getZbTime() {
            return zbTime;
        }

        public void setZbTime(String zbTime) {
            this.zbTime = zbTime;
        }

        public String getJindu() {
            return jindu;
        }

        public void setJindu(String jindu) {
            this.jindu = jindu;
        }

        public String getIfover() {
            return ifover;
        }

        public void setIfover(String ifover) {
            this.ifover = ifover;
        }

        public List<ShopNameBean> getShopName() {
            return shopName;
        }

        public void setShopName(List<ShopNameBean> shopName) {
            this.shopName = shopName;
        }

        public List<MissionBean> getMission() {
            return mission;
        }

        public void setMission(List<MissionBean> mission) {
            this.mission = mission;
        }

        public static class ShopNameBean {
            /**
             * id : 1
             * shopname : 哈尔滨友来友约文化传媒有限公司
             * checkIn : 1
             */

            private String id;
            private String shopname;
            private String checkIn;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getShopname() {
                return shopname;
            }

            public void setShopname(String shopname) {
                this.shopname = shopname;
            }

            public String getCheckIn() {
                return checkIn;
            }

            public void setCheckIn(String checkIn) {
                this.checkIn = checkIn;
            }
        }

        public static class MissionBean {
            /**
             * valname : 0/1
             * name : 完成带队出团
             * type : 0
             * button :
             * status :
             */

            private String valname;
            private String name;
            private String type;
            private String button;
            private String status;

            public String getValname() {
                return valname;
            }

            public void setValname(String valname) {
                this.valname = valname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getButton() {
                return button;
            }

            public void setButton(String button) {
                this.button = button;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
