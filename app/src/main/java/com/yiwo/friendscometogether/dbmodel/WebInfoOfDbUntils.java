package com.yiwo.friendscometogether.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DaoSession;
import com.yiwo.friendscometogether.greendao.gen.YouJiWebInfoDbModelDao;

public class WebInfoOfDbUntils {
    //    //数据库
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    YouJiWebInfoDbModelDao youJiWebInfoDbModelDao;

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
    }

    /**
     * 向友记INfo数据库中添加单个数据(如果有就更新数据 )
     * @param model
     */
    public boolean insertYouJiModel(YouJiWebInfoDbModel model){
        Log.d("插入友记数据：",model.getFm_id()+"::::\n"+model.getWeb_info());
        if (hasThisId(model.getFm_id())){
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
    public boolean hasThisId(String fmId){
        boolean hasThisId = youJiWebInfoDbModelDao.queryBuilder()
                .where(YouJiWebInfoDbModelDao.Properties.Fm_id.eq(fmId)).build()
                .list().size()>0;
        return  hasThisId;
    }
    public YouJiWebInfoDbModel queryYouJi(String fmId){
        YouJiWebInfoDbModel model_old;
        if (hasThisId(fmId)){
             model_old = youJiWebInfoDbModelDao.queryBuilder()
                    .where(YouJiWebInfoDbModelDao.Properties.Fm_id.eq(fmId)
                    ).build()
                    .list().get(0);
        }else {
            model_old = null;
        }
        return model_old;
    }
}
