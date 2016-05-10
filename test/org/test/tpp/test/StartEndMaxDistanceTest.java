/**
* Project Name:tpp
* Date:2016年5月6日下午4:13:32
* Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
*/

package org.test.tpp.test;

import org.junit.Assert;
import org.junit.Test;
import org.test.tpp.exception.TppCmdException;

/**
 * 路线规划单元测试类 <br/>
 * date: 2016年5月6日 下午4:13:32 <br/>
 * @author jinjuma@yeah.net
 * @version 0.0.1
 */
public class StartEndMaxDistanceTest extends TppTest{
	
	/**
	* 测试给定起点、终点、小于最大路线距离给出有多少种路线。 <br/>
	* @author jinjuma@yeah.net
	 * @throws TppCmdException 
	*/
	@Test
	public void startEndMaxDistanceTest_1() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMaxDistance(null);
	}
	@Test
	public void startEndMaxDistanceTest_2() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMaxDistance("CC30qsa");
	}
	@Test
	public void startEndMaxDistanceTest_3() throws TppCmdException{
		Assert.assertEquals(0, tpp.startEndMaxDistance("CQ30"));
		Assert.assertEquals(7, tpp.startEndMaxDistance(" CC30"));
		Assert.assertEquals(7, tpp.startEndMaxDistance("AE21 "));
		Assert.assertEquals(4, tpp.startEndMaxDistance(" AD25 "));
	}
}
