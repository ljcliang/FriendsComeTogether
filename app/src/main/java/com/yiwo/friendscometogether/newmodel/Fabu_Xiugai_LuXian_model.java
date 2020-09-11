package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

public class Fabu_Xiugai_LuXian_model {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"pfID":"116","pftitle":"goingmingon","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20200902/5f981a22fccb413374c4c0fb7907e4b34578.jpg","pfaddress":"taitaitai","sex":"0","min_num":"1","max_num":"10","single":"0","age_begin":"1","age_end":"10","uid":"4","pfexplain":"","go_address":"哈尔滨市道外区先锋路东棵街铁路","keyWord":"演唱会","activity_ts":"wopwoqwporwo","buyNeedKnow":"","wenXinTiShi":"","feiYongBaoHan":"","feiYongBuHan":"","activityLabel":"","phaseInfos":[{"phase_id":"536","sign_up_over_time":"2020-09-05","begin_time":"2020-09-08","end_time":"2020-09-12","phase_price":"0.01","ifCaptain":"1","userBonus":"50.00"}]}
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
         * pfID : 116
         * pftitle : goingmingon
         * pfpic : http://www.tongbanapp.com/uploads/xingcheng/20200902/5f981a22fccb413374c4c0fb7907e4b34578.jpg
         * pfaddress : taitaitai
         * sex : 0
         * min_num : 1
         * max_num : 10
         * single : 0
         * age_begin : 1
         * age_end : 10
         * uid : 4
         * pfexplain :
         * go_address : 哈尔滨市道外区先锋路东棵街铁路
         * keyWord : 演唱会
         * activity_ts : wopwoqwporwo
         * buyNeedKnow :
         * wenXinTiShi :
         * feiYongBaoHan :
         * feiYongBuHan :
         * activityLabel :
         * phaseInfos : [{"phase_id":"536","sign_up_over_time":"2020-09-05","begin_time":"2020-09-08","end_time":"2020-09-12","phase_price":"0.01","ifCaptain":"1","userBonus":"50.00"}]
         */

        private String pfID;
        private String pftitle;
        private String pfpic;
        private String pfaddress;
        private String sex;
        private String min_num;
        private String max_num;
        private String single;
        private String age_begin;
        private String age_end;
        private String uid;
        private String pfexplain;
        private String go_address;
        private String keyWord;
        private String activity_ts;
        private String buyNeedKnow;
        private String wenXinTiShi;
        private String feiYongBaoHan;
        private String feiYongBuHan;
        private String activityLabel;
        private List<PhaseInfosBean> phaseInfos;

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getPftitle() {
            return pftitle;
        }

        public void setPftitle(String pftitle) {
            this.pftitle = pftitle;
        }

        public String getPfpic() {
            return pfpic;
        }

        public void setPfpic(String pfpic) {
            this.pfpic = pfpic;
        }

        public String getPfaddress() {
            return pfaddress;
        }

        public void setPfaddress(String pfaddress) {
            this.pfaddress = pfaddress;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMin_num() {
            return min_num;
        }

        public void setMin_num(String min_num) {
            this.min_num = min_num;
        }

        public String getMax_num() {
            return max_num;
        }

        public void setMax_num(String max_num) {
            this.max_num = max_num;
        }

        public String getSingle() {
            return single;
        }

        public void setSingle(String single) {
            this.single = single;
        }

        public String getAge_begin() {
            return age_begin;
        }

        public void setAge_begin(String age_begin) {
            this.age_begin = age_begin;
        }

        public String getAge_end() {
            return age_end;
        }

        public void setAge_end(String age_end) {
            this.age_end = age_end;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getPfexplain() {
            return pfexplain;
        }

        public void setPfexplain(String pfexplain) {
            this.pfexplain = pfexplain;
        }

        public String getGo_address() {
            return go_address;
        }

        public void setGo_address(String go_address) {
            this.go_address = go_address;
        }

        public String getKeyWord() {
            return keyWord;
        }

        public void setKeyWord(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getActivity_ts() {
            return activity_ts;
        }

        public void setActivity_ts(String activity_ts) {
            this.activity_ts = activity_ts;
        }

        public String getBuyNeedKnow() {
            return buyNeedKnow;
        }

        public void setBuyNeedKnow(String buyNeedKnow) {
            this.buyNeedKnow = buyNeedKnow;
        }

        public String getWenXinTiShi() {
            return wenXinTiShi;
        }

        public void setWenXinTiShi(String wenXinTiShi) {
            this.wenXinTiShi = wenXinTiShi;
        }

        public String getFeiYongBaoHan() {
            return feiYongBaoHan;
        }

        public void setFeiYongBaoHan(String feiYongBaoHan) {
            this.feiYongBaoHan = feiYongBaoHan;
        }

        public String getFeiYongBuHan() {
            return feiYongBuHan;
        }

        public void setFeiYongBuHan(String feiYongBuHan) {
            this.feiYongBuHan = feiYongBuHan;
        }

        public String getActivityLabel() {
            return activityLabel;
        }

        public void setActivityLabel(String activityLabel) {
            this.activityLabel = activityLabel;
        }

        public List<PhaseInfosBean> getPhaseInfos() {
            return phaseInfos;
        }

        public void setPhaseInfos(List<PhaseInfosBean> phaseInfos) {
            this.phaseInfos = phaseInfos;
        }

        public static class PhaseInfosBean implements Serializable {
            /**
             * phase_id : 536
             * sign_up_over_time : 2020-09-05
             * begin_time : 2020-09-08
             * end_time : 2020-09-12
             * phase_price : 0.01
             * ifCaptain : 1
             * userBonus : 50.00
             */

            private String phase_id = "0";
            private String sign_up_over_time = "";
            private String begin_time = "";
            private String end_time = "";
            private String phase_price = "";
            private String ifCaptain = "1";
            private String userBonus = "";

            public String getPhase_id() {
                return phase_id;
            }

            public void setPhase_id(String phase_id) {
                this.phase_id = phase_id;
            }

            public String getSign_up_over_time() {
                return sign_up_over_time;
            }

            public void setSign_up_over_time(String sign_up_over_time) {
                this.sign_up_over_time = sign_up_over_time;
            }

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getPhase_price() {
                return phase_price;
            }

            public void setPhase_price(String phase_price) {
                this.phase_price = phase_price;
            }

            public String getIfCaptain() {
                return ifCaptain;
            }

            public void setIfCaptain(String ifCaptain) {
                this.ifCaptain = ifCaptain;
            }

            public String getUserBonus() {
                return userBonus;
            }

            public void setUserBonus(String userBonus) {
                this.userBonus = userBonus;
            }
        }
    }
}
