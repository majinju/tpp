/**
* Project Name:tpp
* Date:2016年5月6日上午9:53:13
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp.util;


/**
 * 为不引入Log相关jar，这里简单模拟了日志工具 <br/>
 * date: 2016年5月6日 上午9:53:13 <br/>
 * @author jingma@iflytek.com
 * @version 
 */
public class LogUtil {
	/**
	* 打印调试日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	*/
	public static void debug(String msg){
		debug(msg,null);
	}
	/**
	* 打印调试日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	* @param t 相关异常
	*/
	public static void debug(String msg,Throwable t){
		System.out.println(DateUtil.getCurrentDate()+" DEBUG "+msg);
		if(t != null){
			System.out.print(DateUtil.getCurrentDate()+" ");
			t.printStackTrace();
		}
	}
	/**
	* 打印一般日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	*/
	public static void info(String msg){
		info(msg,null);
	}
	/**
	* 打印一般日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	* @param t 相关异常
	*/
	public static void info(String msg,Throwable t){
		System.out.println(DateUtil.getCurrentDate()+" INFO "+msg);
		if(t != null){
			System.out.print(DateUtil.getCurrentDate()+" ");
			t.printStackTrace();
		}
	}
	/**
	* 打印错误日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	*/
	public static void error(String msg){
		error(msg,null);
	}
	/**
	* 打印错误日志信息 <br/>
	* @author jingma@iflytek.com
	* @param msg 要打印的消息
	* @param t 相关异常
	*/
	public static void error(String msg,Throwable t){
		System.out.println(DateUtil.getCurrentDate()+" ERROR "+msg);
		if(t != null){
			System.out.print(DateUtil.getCurrentDate()+" ");
			t.printStackTrace();
		}
	}
}
