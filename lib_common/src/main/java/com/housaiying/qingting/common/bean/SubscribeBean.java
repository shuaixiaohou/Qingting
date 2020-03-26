package com.housaiying.qingting.common.bean;

import com.google.gson.Gson;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/20 9:33
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
@Entity
public class SubscribeBean {
    @Id
    private
    long albumId;
    @Convert(converter = AlbumConverter.class, columnType = String.class)
    private
    Album album;
    private long datetime;


    @Generated(hash = 400642637)
    public SubscribeBean(long albumId, Album album, long datetime) {
        this.albumId = albumId;
        this.album = album;
        this.datetime = datetime;
    }

    @Generated(hash = 781367487)
    public SubscribeBean() {
    }


    public long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public Album getAlbum() {
        return this.album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public long getDatetime() {
        return this.datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public static class AlbumConverter implements PropertyConverter<Album, String> {
        @Override
        public Album convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            return new Gson().fromJson(databaseValue, Album.class);
        }

        @Override
        public String convertToDatabaseValue(Album entityProperty) {
            if (entityProperty == null) {
                return null;
            }
            return new Gson().toJson(entityProperty);
        }
    }
}
