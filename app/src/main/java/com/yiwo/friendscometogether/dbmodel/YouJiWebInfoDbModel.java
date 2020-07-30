package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class YouJiWebInfoDbModel {
    @Id(autoincrement = true)
    private Long id;
    private String fm_id;
    private String web_info;
    public String getWeb_info() {
        return this.web_info;
    }
    public void setWeb_info(String web_info) {
        this.web_info = web_info;
    }
    public String getFm_id() {
        return this.fm_id;
    }
    public void setFm_id(String fm_id) {
        this.fm_id = fm_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 9995121)
    public YouJiWebInfoDbModel(Long id, String fm_id, String web_info) {
        this.id = id;
        this.fm_id = fm_id;
        this.web_info = web_info;
    }
    @Generated(hash = 2137059119)
    public YouJiWebInfoDbModel() {
    }
}
