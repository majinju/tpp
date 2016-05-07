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
public class StartEndMaxStopsTest extends TppTest{
	/**
	* 测试给定起点、终点、最大经过站数量给出有多少种路线 <br/>
	* @author jingma@iflytek.com
	 * @throws TppCmdException 
	*/
	@Test
	public void startEndMaxStopsTest_1() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMaxStops(null);
	}
	@Test
	public void startEndMaxStopsTest_2() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMaxStops("CC3qsa");
	}
	@Test
	public void startEndMaxStopsTest_3() throws TppCmdException{
		Assert.assertEquals(0, tpp.startEndMaxStops("CQ3 "));
		Assert.assertEquals(2, tpp.startEndMaxStops(" CC3"));
		Assert.assertEquals(7, tpp.startEndMaxStops(" AE4 "));
		Assert.assertEquals(7, tpp.startEndMaxStops(" AD5"));
	}
}
