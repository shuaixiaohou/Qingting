package com.housaiying.qingting.common.aop;

import com.thomas.aspectj.OkAspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: housaiying<br/>
 * Date: 2020/3/15 14:36<br/>
 * Email: 1071931588@qq.com<br/>
 * Description:
 */
@OkAspectj
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {
}
