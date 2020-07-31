package com.yiwo.friendscometogether.dbmodel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class YouJuHuoDongWebInfoDbModel {
    @Id(autoincrement = true)
    private Long id;
    private String pf_id;
    private String web_info;
    public String getWeb_info() {
        return this.web_info;
    }
    public void setWeb_info(String web_info) {
        this.web_info = web_info;
    }
    public String getPf_id() {
        return this.pf_id;
    }
    public void setPf_id(String pf_id) {
        this.pf_id = pf_id;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 503812999)
    public YouJuHuoDongWebInfoDbModel(Long id, String pf_id, String web_info) {
        this.id = id;
        this.pf_id = pf_id;
        this.web_info = web_info;
    }
    @Generated(hash = 147816419)
    public YouJuHuoDongWebInfoDbModel() {
    }
}
