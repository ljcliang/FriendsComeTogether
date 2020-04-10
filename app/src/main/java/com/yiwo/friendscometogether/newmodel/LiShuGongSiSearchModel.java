package com.yiwo.friendscometogether.newmodel;

import java.util.List;

public class LiShuGongSiSearchModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","shopname":"哈尔滨友来友约文化传媒有限公司","checkIn":"0"},{"id":"15","shopname":"哈尔滨观光国际旅行社有限公司","checkIn":"1"}]
     */

    private int code;
    private String message;
    private List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> obj;

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

    public List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> getObj() {
        return obj;
    }

    public void setObj(List<DuiZhangZhuanShuModel.ObjBean.ShopNameBean> obj) {
        this.obj = obj;
    }

}
