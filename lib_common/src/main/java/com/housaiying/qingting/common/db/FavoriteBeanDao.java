package com.housaiying.qingting.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.housaiying.qingting.common.bean.FavoriteBean.TrackConverter;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import com.housaiying.qingting.common.bean.FavoriteBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "FAVORITE_BEAN".
*/
public class FavoriteBeanDao extends AbstractDao<FavoriteBean, Long> {

    public static final String TABLENAME = "FAVORITE_BEAN";

    /**
     * Properties of entity FavoriteBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TrackId = new Property(0, long.class, "trackId", true, "_id");
        public final static Property Track = new Property(1, String.class, "track", false, "TRACK");
        public final static Property Datetime = new Property(2, long.class, "datetime", false, "DATETIME");
    }

    private final TrackConverter trackConverter = new TrackConverter();

    public FavoriteBeanDao(DaoConfig config) {
        super(config);
    }
    
    public FavoriteBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FAVORITE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: trackId
                "\"TRACK\" TEXT," + // 1: track
                "\"DATETIME\" INTEGER NOT NULL );"); // 2: datetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FAVORITE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FavoriteBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getTrackId());
 
        Track track = entity.getTrack();
        if (track != null) {
            stmt.bindString(2, trackConverter.convertToDatabaseValue(track));
        }
        stmt.bindLong(3, entity.getDatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FavoriteBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getTrackId());
 
        Track track = entity.getTrack();
        if (track != null) {
            stmt.bindString(2, trackConverter.convertToDatabaseValue(track));
        }
        stmt.bindLong(3, entity.getDatetime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public FavoriteBean readEntity(Cursor cursor, int offset) {
        FavoriteBean entity = new FavoriteBean( //
            cursor.getLong(offset + 0), // trackId
            cursor.isNull(offset + 1) ? null : trackConverter.convertToEntityProperty(cursor.getString(offset + 1)), // track
            cursor.getLong(offset + 2) // datetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FavoriteBean entity, int offset) {
        entity.setTrackId(cursor.getLong(offset + 0));
        entity.setTrack(cursor.isNull(offset + 1) ? null : trackConverter.convertToEntityProperty(cursor.getString(offset + 1)));
        entity.setDatetime(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(FavoriteBean entity, long rowId) {
        entity.setTrackId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(FavoriteBean entity) {
        if(entity != null) {
            return entity.getTrackId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(FavoriteBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
