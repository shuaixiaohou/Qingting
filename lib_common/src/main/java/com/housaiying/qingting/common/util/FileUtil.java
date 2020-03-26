package com.housaiying.qingting.common.util;

import com.blankj.utilcode.util.FileUtils;

import java.util.List;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/7 9:51
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class FileUtil {
    public static void deleteFiles(List<String> paths) {
        for (int i = 0; i < paths.size(); i++) {
            FileUtils.delete(paths.get(i));
        }
    }
}
