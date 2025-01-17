package com.housaiying.qingting.common.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.housaiying.qingting.common.bean.PlayHistoryBean;
import com.housaiying.qingting.common.bean.PlayHistoryBean.ScheduleConverter;
import com.housaiying.qingting.common.bean.PlayHistoryBean.TrackConverter;
import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "PLAY_HISTORY_BEAN".
 */
public class PlayHistoryBeanDao extends AbstractDao<PlayHistoryBean, Long> {

    public static final String TABLENAME = "PLAY_HISTORY_BEAN";
    private final TrackConverter trackConverter = new TrackConverter();
    private final ScheduleConverter scheduleConverter = new ScheduleConverter();

    public PlayHistoryBeanDao(DaoConfig config) {
        super(config);
    }

    public PlayHistoryBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /**
     * Creates the underlying database table.
     */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists ? "IF NOT EXISTS " : "";
        db.execSQL("CREATE TABLE " + constraint + "\"PLAY_HISTORY_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: soundId
                "\"GROUP_ID\" INTEGER NOT NULL ," + // 1: groupId
                "\"KIND\" TEXT," + // 2: kind
                "\"PERCENT\" INTEGER NOT NULL ," + // 3: percent
                "\"DATATIME\" INTEGER NOT NULL ," + // 4: datatime
                "\"TRACK\" TEXT," + // 5: track
                "\"SCHEDULE\" TEXT);"); // 6: schedule
    }

    /**
     * Drops the underlying database table.
     */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PLAY_HISTORY_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, PlayHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getSoundId());
        stmt.bindLong(2, entity.getGroupId());

        String kind = entity.getKind();
        if (kind != null) {
            stmt.bindString(3, kind);
        }
        stmt.bindLong(4, entity.getPercent());
        stmt.bindLong(5, entity.getDatatime());

        Track track = entity.getTrack();
        if (track != null) {
            stmt.bindString(6, trackConverter.convertToDatabaseValue(track));
        }

        Schedule schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(7, scheduleConverter.convertToDatabaseValue(schedule));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, PlayHistoryBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getSoundId());
        stmt.bindLong(2, entity.getGroupId());

        String kind = entity.getKind();
        if (kind != null) {
            stmt.bindString(3, kind);
        }
        stmt.bindLong(4, entity.getPercent());
        stmt.bindLong(5, entity.getDatatime());

        Track track = entity.getTrack();
        if (track != null) {
            stmt.bindString(6, trackConverter.convertToDatabaseValue(track));
        }

        Schedule schedule = entity.getSchedule();
        if (schedule != null) {
            stmt.bindString(7, scheduleConverter.convertToDatabaseValue(schedule));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }

    @Override
    public PlayHistoryBean readEntity(Cursor cursor, int offset) {
        PlayHistoryBean entity = new PlayHistoryBean( //
                cursor.getLong(offset + 0), // soundId
                cursor.getLong(offset + 1), // groupId
                cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // kind
                cursor.getInt(offset + 3), // percent
                cursor.getLong(offset + 4), // datatime
                cursor.isNull(offset + 5) ? null : trackConverter.convertToEntityProperty(cursor.getString(offset + 5)), // track
                cursor.isNull(offset + 6) ? null : scheduleConverter.convertToEntityProperty(cursor.getString(offset + 6)) // schedule
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, PlayHistoryBean entity, int offset) {
        entity.setSoundId(cursor.getLong(offset + 0));
        entity.setGroupId(cursor.getLong(offset + 1));
        entity.setKind(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPercent(cursor.getInt(offset + 3));
        entity.setDatatime(cursor.getLong(offset + 4));
        entity.setTrack(cursor.isNull(offset + 5) ? null : trackConverter.convertToEntityProperty(cursor.getString(offset + 5)));
        entity.setSchedule(cursor.isNull(offset + 6) ? null : scheduleConverter.convertToEntityProperty(cursor.getString(offset + 6)));
    }

    @Override
    protected final Long updateKeyAfterInsert(PlayHistoryBean entity, long rowId) {
        entity.setSoundId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(PlayHistoryBean entity) {
        if (entity != null) {
            return entity.getSoundId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(PlayHistoryBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    /**
     * Properties of entity PlayHistoryBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property SoundId = new Property(0, long.class, "soundId", true, "_id");
        public final static Property GroupId = new Property(1, long.class, "groupId", false, "GROUP_ID");
        public final static Property Kind = new Property(2, String.class, "kind", false, "KIND");
        public final static Property Percent = new Property(3, int.class, "percent", false, "PERCENT");
        public final static Property Datatime = new Property(4, long.class, "datatime", false, "DATATIME");
        public final static Property Track = new Property(5, String.class, "track", false, "TRACK");
        public final static Property Schedule = new Property(6, String.class, "schedule", false, "SCHEDULE");
    }

}
