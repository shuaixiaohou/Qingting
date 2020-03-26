package com.housaiying.qingting.home.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.alibaba.android.arouter.launcher.ARouter;
import com.housaiying.qingting.common.Constants;
import com.housaiying.qingting.common.event.KeyCode;
import com.housaiying.qingting.common.util.RouterUtil;

/**
 * Author: housaiying
 * <br/>Date: 2020/3/9 9:07
 * <br/>Email: 1194959365@qq.com
 * <br/>Description:
 */
public class RadioCategoryItem extends androidx.appcompat.widget.AppCompatTextView {
    public RadioCategoryItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnClickListener(view ->
                RouterUtil.navigateTo(ARouter.getInstance().build(Constants.Router.Home.F_RADIO_LIST)
                        .withInt(KeyCode.Home.TYPE, Integer.parseInt(getTag().toString()))
                        .withString(KeyCode.Home.TITLE, getText().toString())));
    }

}
