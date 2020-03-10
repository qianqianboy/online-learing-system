package com.qianqian.edu.common.util;

import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Calendar;

/**
 * @author minsiqian
 * @date 2020/2/27 18:22
 */
public class XSSFDateUtil extends DateUtil {
    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }
}