package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class GoodsWebInfoDbModel {
    @Id(autoincrement = true)
    private Long id;
    private String good_id;
    private String web_info;
    public String getWeb_info() {
        return this.web_info;
    }
    public void setWeb_info(String web_info) {
        this.web_info = web_info;
    }
    public String getGood_id() {
        return this.good_id;
    }
    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 123350233)
    public GoodsWebInfoDbModel(Long id, String good_id, String web_info) {
        this.id = id;
        this.good_id = good_id;
        this.web_info = web_info;
    }
    @Generated(hash = 641858476)
    public GoodsWebInfoDbModel() {
    }
}
