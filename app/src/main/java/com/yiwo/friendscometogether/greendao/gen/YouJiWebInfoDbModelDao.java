package com.yiwo.friendscometogether.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.yiwo.friendscometogether.dbmodel.YouJiWebInfoDbModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "YOU_JI_WEB_INFO_DB_MODEL".
*/
public class YouJiWebInfoDbModelDao extends AbstractDao<YouJiWebInfoDbModel, Long> {

    public static final String TABLENAME = "YOU_JI_WEB_INFO_DB_MODEL";

    /**
     * Properties of entity YouJiWebInfoDbModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Fm_id = new Property(1, String.class, "fm_id", false, "FM_ID");
        public final static Property Web_info = new Property(2, String.class, "web_info", false, "WEB_INFO");
    }


    public YouJiWebInfoDbModelDao(DaoConfig config) {
        super(config);
    }
    
    public YouJiWebInfoDbModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"YOU_JI_WEB_INFO_DB_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"FM_ID\" TEXT," + // 1: fm_id
                "\"WEB_INFO\" TEXT);"); // 2: web_info
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"YOU_JI_WEB_INFO_DB_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, YouJiWebInfoDbModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fm_id = entity.getFm_id();
        if (fm_id != null) {
            stmt.bindString(2, fm_id);
        }
 
        String web_info = entity.getWeb_info();
        if (web_info != null) {
            stmt.bindString(3, web_info);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, YouJiWebInfoDbModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fm_id = entity.getFm_id();
        if (fm_id != null) {
            stmt.bindString(2, fm_id);
        }
 
        String web_info = entity.getWeb_info();
        if (web_info != null) {
            stmt.bindString(3, web_info);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public YouJiWebInfoDbModel readEntity(Cursor cursor, int offset) {
        YouJiWebInfoDbModel entity = new YouJiWebInfoDbModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fm_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // web_info
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, YouJiWebInfoDbModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFm_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setWeb_info(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(YouJiWebInfoDbModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(YouJiWebInfoDbModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(YouJiWebInfoDbModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}