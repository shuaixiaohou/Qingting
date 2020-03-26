package com.housaiying.qingting.common.extra;


/**
 * Author: housaiying<br/>
 * Date: 2020/3/24 12:43<br/>
 * Description:用来存放局部变量,实现匿名内部类直接修改局部变量的情况
 */
public class RxField<F> {

    private F field;


    public F get() {
        return field;
    }

    public void set(F field) {
        this.field = field;
    }
}
