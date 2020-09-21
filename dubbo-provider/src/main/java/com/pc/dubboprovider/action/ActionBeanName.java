/**
 * Copyright (C) 2009-2016 Hangzhou 2Dfire Technology Co., Ltd. All rights reserved
 */
package com.pc.dubboprovider.action;


import org.apache.commons.lang.StringUtils;

/**
 */
public class ActionBeanName {

    /**
     * executor后缀
     */
    private static final String ACTION = "Action";
    /**
     * param后缀
     */
    private static final String PARAM = "Param";


    public static String getName(Class clazz) {
        String clazzName = StringUtils.replace(clazz.getSimpleName(), PARAM, ACTION);
        return StringUtils.uncapitalize(clazzName);
    }
}
