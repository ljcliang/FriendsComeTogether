package com.yiwo.friendscometogether.newmodel;

import java.io.Serializable;
import java.util.List;

public class FabuLuXianChooseDateWithWebModel implements Serializable {

    private List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> datearr;

    public List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> getDatearr() {
        return datearr;
    }

    public void setDatearr(List<Fabu_Xiugai_LuXian_model.ObjBean.PhaseInfosBean> datearr) {
        this.datearr = datearr;
    }

}
