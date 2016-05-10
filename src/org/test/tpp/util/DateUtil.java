/**
* Project Name:tpp
* Date:2016年5月6日上午9:57:28
* Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
*/

package org.test.tpp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类 <br/>
 * date: 2016年5月6日 上午9:57:28 <br/>
 * @author jinjuma@yeah.net
 * @version 
 */
public class DateUtil {
	/**
	* 短日期格式
	*/
	public static final String DF_L = "yyyy-MM-dd";
	/**
	* 长日期格式
	*/
	public static final String DF_LL = "yyyy-MM-dd HH:mm:ss";
	/**
	* 获取格式化的当前时间字符串,默认格式：yyyy-MM-dd HH:mm:ss <br/>
	* @author jinjuma@yeah.net
	* @return 格式化的当前时间字符串
	*/
	public static String getCurrentDate(){
		return getCurrentDate(DF_LL);
	}
	/**
	* 获取格式化的当前时间字符串 <br/>
	* @author jinjuma@yeah.net
	* @param format 日期格式
	* @return 格式化的当前时间字符串
	*/
	public static String getCurrentDate(String format){
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}
}
