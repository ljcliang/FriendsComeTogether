package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class GuanzhuDuizhangDaiduiListModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"usergrade":"1","levelName":"0","userID":"1335","username":"5326_1335","userpic":"http://www.tongbanapp.com/uploads/header/2019/12/20/9496944d6d5c0762abd047cf154e7cfc15768327383.png","phase_begin_time":"01月08日","phase_over_time":"01月15日","pfID":"85","pftitle":"玩转芽庄8日游","pfaddress":"越南芽庄","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20191223/e42561aa4ce75bea45504b740befec9e.jpg","listImg":"uploads/xingcheng/20191223/e42561aa4ce75bea45504b740befec9e.jpg","ifCaptain":"1","gz":"1"},{"usergrade":"1","levelName":"0","userID":"361","username":"雨哥","userpic":"http://www.tongbanapp.com/uploads/header/2019/10/11/a63fcdaa786225e73f5ba4013fb2e5d4157079215415.png","phase_begin_time":"12月18日","phase_over_time":"12月25日","pfID":"78","pftitle":"哈尔滨飞越芽庄8日游","pfaddress":"越南芽庄","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20200121/65f08342ae3d0b12c37d78862a7623ae.jpg","listImg":"uploads/xingcheng/20200121/65f08342ae3d0b12c37d78862a7623ae.jpg","ifCaptain":"1","gz":"1"},{"usergrade":"1","levelName":"0","userID":"7","username":"大叔爱健身","userpic":"http://www.tongbanapp.com/uploads/header/2019/05/02/917832687f100195d0591cacbc448bf3155680934216.png","phase_begin_time":"04月12日","phase_over_time":"04月12日","pfID":"1","pftitle":"二龙山爬山运动会","pfaddress":"二龙山","pfpic":"http://www.tongbanapp.com/uploads/xingcheng/20190411/0680c545143508829eacc1ce64b91d8b.jpg","listImg":"","ifCaptain":"1","gz":"1"}]
     */

    private int code;
    private String message;
    private List<HomeGuanZhuModel.ObjBean.CaptainPfBean> obj;

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

    public List<HomeGuanZhuModel.ObjBean.CaptainPfBean> getObj() {
        return obj;
    }

    public void setObj(List<HomeGuanZhuModel.ObjBean.CaptainPfBean> obj) {
        this.obj = obj;
    }

}
