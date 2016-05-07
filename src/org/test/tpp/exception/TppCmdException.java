/**
* Project Name:tpp
* Date:2016年5月6日上午10:12:20
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp.exception;

/**
 * 铁路路线规划命令异常 <br/>
 * date: 2016年5月6日 上午10:12:20 <br/>
 * @author jingma@iflytek.com
 * @version 
 */
public class TppCmdException extends Exception {

	private static final long serialVersionUID = 3160417017733764699L;
	/**
	* Creates a new instance of TppException.
	* @param message 消息
	*/
	public TppCmdException(String message) {
		super(message);
	}

    /**
    * Creates a new instance of TppException.
    * @param message 消息
    * @param cause 相关异常
    */
    public TppCmdException(String message, Throwable cause) {
        super(message, cause);
    }
}
