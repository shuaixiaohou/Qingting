package com.housaiying.qingting.common.event;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/18 13:58
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:页面跳转请求码
 */
public interface RequestCode {
    interface Common {
        //500开始
        int PIKE_IMAGE = 501;
        int PIKE_VIDEO = 502;
        int VIDEO_TRIMMER = 503;
    }

    interface Main {
        //1000开始
    }

    interface News {
        //2000开始
    }

    interface Find {
        //3000开始
    }

    interface Patrol {
        //4000开始
        int FACE_COMPARE = 4000;
        int OUT_PATROL = 4001;
    }
}
