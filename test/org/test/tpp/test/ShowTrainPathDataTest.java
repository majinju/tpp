/**
* Project Name:tpp
* Date:2016年5月6日下午4:13:32
* Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
*/

package org.test.tpp.test;

import org.junit.Test;
import org.test.tpp.exception.TppCmdException;

/**
 * 路线规划单元测试类 <br/>
 * date: 2016年5月6日 下午4:13:32 <br/>
 * @author jinjuma@yeah.net
 * @version 0.0.1
 */
public class ShowTrainPathDataTest extends TppTest{
	
	/**
	* 显示火车路线数据 。 <br/>
	* @author jinjuma@yeah.net
	 * @throws TppCmdException 
	*/
	@Test
	public void showTrainPathDataTest_1() throws TppCmdException{
		tpp.showTrainPathData();
	}
}
