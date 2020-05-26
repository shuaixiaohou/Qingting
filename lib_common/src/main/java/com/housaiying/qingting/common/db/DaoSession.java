package com.housaiying.qingting.common.db;

import com.housaiying.qingting.common.bean.FavoriteBean;
import com.housaiying.qingting.common.bean.PlayHistoryBean;
import com.housaiying.qingting.common.bean.SearchHistoryBean;
import com.housaiying.qingting.common.bean.SubscribeBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 *
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig favoriteBeanDaoConfig;
    private final DaoConfig playHistoryBeanDaoConfig;
    private final DaoConfig searchHistoryBeanDaoConfig;
    private final DaoConfig subscribeBeanDaoConfig;

    private final FavoriteBeanDao favoriteBeanDao;
    private final PlayHistoryBeanDao playHistoryBeanDao;
    private final SearchHistoryBeanDao searchHistoryBeanDao;
    private final SubscribeBeanDao subscribeBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        favoriteBeanDaoConfig = daoConfigMap.get(FavoriteBeanDao.class).clone();
        favoriteBeanDaoConfig.initIdentityScope(type);

        playHistoryBeanDaoConfig = daoConfigMap.get(PlayHistoryBeanDao.class).clone();
        playHistoryBeanDaoConfig.initIdentityScope(type);

        searchHistoryBeanDaoConfig = daoConfigMap.get(SearchHistoryBeanDao.class).clone();
        searchHistoryBeanDaoConfig.initIdentityScope(type);

        subscribeBeanDaoConfig = daoConfigMap.get(SubscribeBeanDao.class).clone();
        subscribeBeanDaoConfig.initIdentityScope(type);

        favoriteBeanDao = new FavoriteBeanDao(favoriteBeanDaoConfig, this);
        playHistoryBeanDao = new PlayHistoryBeanDao(playHistoryBeanDaoConfig, this);
        searchHistoryBeanDao = new SearchHistoryBeanDao(searchHistoryBeanDaoConfig, this);
        subscribeBeanDao = new SubscribeBeanDao(subscribeBeanDaoConfig, this);

        registerDao(FavoriteBean.class, favoriteBeanDao);
        registerDao(PlayHistoryBean.class, playHistoryBeanDao);
        registerDao(SearchHistoryBean.class, searchHistoryBeanDao);
        registerDao(SubscribeBean.class, subscribeBeanDao);
    }

    public void clear() {
        favoriteBeanDaoConfig.clearIdentityScope();
        playHistoryBeanDaoConfig.clearIdentityScope();
        searchHistoryBeanDaoConfig.clearIdentityScope();
        subscribeBeanDaoConfig.clearIdentityScope();
    }

    public FavoriteBeanDao getFavoriteBeanDao() {
        return favoriteBeanDao;
    }

    public PlayHistoryBeanDao getPlayHistoryBeanDao() {
        return playHistoryBeanDao;
    }

    public SearchHistoryBeanDao getSearchHistoryBeanDao() {
        return searchHistoryBeanDao;
    }

    public SubscribeBeanDao getSubscribeBeanDao() {
        return subscribeBeanDao;
    }

}
