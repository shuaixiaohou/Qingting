package com.housaiying.qingting.common.bean;

import com.ximalaya.ting.android.opensdk.model.announcer.AnnouncerCategory;

import java.util.Objects;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/11 10:43
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class AnnouncerCategoryBean extends AnnouncerCategory {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnouncerCategoryBean that = (AnnouncerCategoryBean) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
