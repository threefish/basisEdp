package com.sgaop.common.beetl.function;

import org.beetl.core.Context;
import org.beetl.core.Function;

import java.util.Date;

public class DateFunction implements Function {

    /**
     * @param paras beetl传递的参数
     * @param ctx
     * @return
     */
    @Override
    public Object call(Object[] paras, Context ctx) {
        return new Date().getTime();
    }
}