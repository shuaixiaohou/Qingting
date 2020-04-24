package com.housaiying.qingting.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.housaiying.qingting.common.bean.SearchHistoryBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SEARCH_HISTORY_BEAN".
*/
public class SearchHistoryBeanDao extends AbstractDao<SearchHistoryBean, Long> {

    public static final String TABLENAME = "SEARCH_HISTORY_BEAN";

    /**
     * Properties of entity SearchHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Keyword = new Property(1, String.class, "keyword", false, "KEYWORD");
        public final static Property Datatime = new Property(2, long.class, "datatime", false, "DATATIME");
    }


    public SearchHistoryBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SearchHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SEARCH_HISTORY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"KEYWORD\" TEXT," + // 1: keyword
                "\"DATATIME\" INTEGER NOT NULL );"); // 2: datatime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SEARCH_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String keyword = entity.getKeyword();
        if (keyword != null) {
            stmt.bindString(2, keyword);
        }
        stmt.bindLong(3, entity.getDatatime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SearchHistoryBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String keyword = entity.getKeyword();
        if (keyword != null) {
            stmt.bindString(2, keyword);
        }
        stmt.bindLong(3, entity.getDatatime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SearchHistoryBean readEntity(Cursor cursor, int offset) {
        SearchHistoryBean entity = new SearchHistoryBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // keyword
            cursor.getLong(offset + 2) // datatime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SearchHistoryBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setKeyword(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDatatime(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SearchHistoryBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SearchHistoryBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SearchHistoryBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
