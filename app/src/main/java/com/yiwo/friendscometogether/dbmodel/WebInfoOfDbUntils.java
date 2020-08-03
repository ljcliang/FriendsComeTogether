package com.yiwo.friendscometogether.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.GoodsWebInfoDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.YouJiWebInfoDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.YouJuHuoDongWebInfoDbModelDao;
import com.yiwo.friendscometogether.newmodel.YouJuWebModel;

public class WebInfoOfDbUntils {
    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    YouJiWebInfoDbModelDao youJiWebInfoDbModelDao;
    YouJuHuoDongWebInfoDbModelDao youJuHuoDongWebInfoDbModelDao;
    GoodsWebInfoDbModelDao goodsWebInfoDbModelDao;
    private Context context;
    public WebInfoOfDbUntils(Context c){
        this.context = c;
        setDatabase();
    }
    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(context, "usergive-db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        youJiWebInfoDbModelDao = mDaoSession.getYouJiWebInfoDbModelDao();
        youJuHuoDongWebInfoDbModelDao = mDaoSession.getYouJuHuoDongWebInfoDbModelDao();
        goodsWebInfoDbModelDao = mDaoSession.getGoodsWebInfoDbModelDao();
    }

    /**
     * 向友记INfo数据库中添加单个数据(如果有就更新数据 )
     * @param model
     */
    public boolean insertYouJiModel(YouJiWebInfoDbModel model){
        Log.d("插入友记数据：",model.getFm_id()+"::::\n"+model.getWeb_info());
        if (hasThisId_YouJi(model.getFm_id())){
            YouJiWebInfoDbModel model_old = queryYouJi(model.getFm_id());
            if (model_old != null){
                model_old.setWeb_info(model.getWeb_info());
                youJiWebInfoDbModelDao.update(model_old);
                return true;
            }else {
                return false;
            }
        }else {
            youJiWebInfoDbModelDao.insert(model);
            return true;
        }
    }
    /**
     * 向友聚活动INfo数据库中添加单个数据(如果有就更新数据 )
     * @param model
     */
    public boolean insertYouJuHuoDongModel(YouJuHuoDongWebInfoDbModel model){
        Log.d("插入活动数据：",model.getPf_id()+"::::\n"+model.getWeb_info());
        if (hasThisId_HuoDong(model.getPf_id())){
            YouJuHuoDongWebInfoDbModel model_old = queryYouJu(model.getPf_id());
            if (model_old != null){
                model_old.setWeb_info(model.getWeb_info());
                youJuHuoDongWebInfoDbModelDao.update(model_old);
                return true;
            }else {
                return false;
            }
        }else {
            youJuHuoDongWebInfoDbModelDao.insert(model);
            return true;
        }
    }

    /**
     * 向商品INfo数据库中添加单个数据(如果有就更新数据 )
     * @param model
     */
    public boolean insertGoodModel(GoodsWebInfoDbModel model){
        Log.d("插入商品数据：",model.getGood_id()+"::::\n"+model.getWeb_info());
        if (hasThisId_Goods(model.getGood_id())){
            GoodsWebInfoDbModel model_old = queryGood(model.getGood_id());
            if (model_old != null){
                model_old.setWeb_info(model.getWeb_info());
                goodsWebInfoDbModelDao.update(model_old);
                return true;
            }else {
                return false;
            }
        }else {
            goodsWebInfoDbModelDao.insert(model);
            return true;
        }
    }

    public boolean hasThisId_YouJi(String fmId){
        boolean hasThisId = youJiWebInfoDbModelDao.queryBuilder()
                .where(YouJiWebInfoDbModelDao.Properties.Fm_id.eq(fmId)).build()
                .list().size()>0;
        return  hasThisId;
    }
    public boolean hasThisId_HuoDong(String pf_id){
        boolean hasThisId = youJuHuoDongWebInfoDbModelDao.queryBuilder()
                .where(YouJuHuoDongWebInfoDbModelDao.Properties.Pf_id.eq(pf_id)).build()
                .list().size()>0;
        return  hasThisId;
    }
    public boolean hasThisId_Goods(String goodId){
        boolean hasThisId = goodsWebInfoDbModelDao.queryBuilder()
                .where(GoodsWebInfoDbModelDao.Properties.Good_id.eq(goodId)).build()
                .list().size()>0;
        return  hasThisId;
    }
    public YouJiWebInfoDbModel queryYouJi(String fmId){
        YouJiWebInfoDbModel model_old;
        if (hasThisId_YouJi(fmId)){
             model_old = youJiWebInfoDbModelDao.queryBuilder()
                    .where(YouJiWebInfoDbModelDao.Properties.Fm_id.eq(fmId)
                    ).build()
                    .list().get(0);
        }else {
            model_old = null;
        }
        return model_old;
    }
    public YouJuHuoDongWebInfoDbModel queryYouJu(String pfId){
        YouJuHuoDongWebInfoDbModel model_old;
        if (hasThisId_HuoDong(pfId)){
            model_old = youJuHuoDongWebInfoDbModelDao.queryBuilder()
                    .where(YouJuHuoDongWebInfoDbModelDao.Properties.Pf_id.eq(pfId)
                    ).build()
                    .list().get(0);
        }else {
            model_old = null;
        }
        return model_old;
    }
    public GoodsWebInfoDbModel queryGood(String goodId){
        GoodsWebInfoDbModel model_old;
        if (hasThisId_Goods(goodId)){
            model_old = goodsWebInfoDbModelDao.queryBuilder()
                    .where(GoodsWebInfoDbModelDao.Properties.Good_id.eq(goodId)
                    ).build()
                    .list().get(0);
        }else {
            model_old = null;
        }
        return model_old;
    }
}
