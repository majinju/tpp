/**
* Project Name:tpp
* Date:2016年5月6日下午4:13:32
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp.test;

import org.junit.Assert;
import org.junit.Test;
import org.test.tpp.exception.TppCmdException;

/**
 * 路线规划单元测试类 <br/>
 * date: 2016年5月6日 下午4:13:32 <br/>
 * @author jingma@iflytek.com
 * @version 0.0.1
 */
public class StartEndStopsTest extends TppTest{
	/**
	* 测试给定起点、终点、刚好经过站数量给出有多少种路线。 <br/>
	* @author jingma@iflytek.com
	 * @throws TppCmdException 
	*/
	@Test
	public void startEndStopsTest_1() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndStops(null);
	}
	@Test
	public void startEndStopsTest_2() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndStops("CC3qsa");
	}
	@Test
	public void startEndStopsTest_3() throws TppCmdException{
		Assert.assertEquals(0, tpp.startEndStops("CQ3"));
		Assert.assertEquals(1, tpp.startEndStops(" CC3"));
		Assert.assertEquals(3, tpp.startEndStops("AE4 "));
		Assert.assertEquals(3, tpp.startEndStops(" AD5 "));
	}
}
