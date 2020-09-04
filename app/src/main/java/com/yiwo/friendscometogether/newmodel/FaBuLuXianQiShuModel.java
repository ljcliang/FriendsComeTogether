package com.yiwo.friendscometogether.newmodel;

public class FaBuLuXianQiShuModel {

    /**
     *
     *
     * phase_price : 120
     * userBonus : 0
     * begin_time : 2020-08-13
     * end_time : 2020-08-15
     * sign_up_over_time : 2020-08-10
     * ifCaptain : 0
     * phase_price价格    userBonus提成    begin_time开始时间    end_time结束时间    sign_up_over_time报名截止时间 ifCaptain是否我是带队 0否  1是
     */

    private String phase_price = "";
    private String userBonus = "";
    private String begin_time = "";
    private String end_time = "";
    private String sign_up_over_time = "";
    private String ifCaptain = "1";

    public String getPhase_price() {
        return phase_price;
    }

    public void setPhase_price(String phase_price) {
        this.phase_price = phase_price;
    }

    public String getUserBonus() {
        return userBonus;
    }

    public void setUserBonus(String userBonus) {
        this.userBonus = userBonus;
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

    public String getSign_up_over_time() {
        return sign_up_over_time;
    }

    public void setSign_up_over_time(String sign_up_over_time) {
        this.sign_up_over_time = sign_up_over_time;
    }

    public String getIfCaptain() {
        return ifCaptain;
    }

    public void setIfCaptain(String ifCaptain) {
        this.ifCaptain = ifCaptain;
    }
}
