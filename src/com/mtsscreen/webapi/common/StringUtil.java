package com.mtsscreen.webapi.common;

import java.util.Arrays;

/**
 * @ClassName: StringUtil
 * @Description: 字符串转换共???
 * @author 赵峰???
 * @date 2014???8???22??? 下午3:54:26
 * @version 1.0
 */
public class StringUtil
{
    /**
     * ???查文字列???<code>null</code???
     * 
     * @param str
     *            文字???
     * @return true或???false
     */
    public static boolean isEmpty(String str)
    {
        if (str == null || str.length() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static  boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
	public static boolean useList(String[] arr, String targetValue) {
	    return Arrays.asList(arr).contains(targetValue);
	}
}
