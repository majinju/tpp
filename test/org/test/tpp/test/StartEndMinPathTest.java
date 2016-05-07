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
public class StartEndMinPathTest extends TppTest{
	/**
	* 测试给定起点、终点、刚好经过站数量给出有多少种路线。 <br/>
	* @author jingma@iflytek.com
	 * @throws TppCmdException 
	*/
	@Test
	public void startEndMinPathTest_1() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMinPath(null);
	}
	@Test
	public void startEndMinPathTest_2() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.startEndMinPath("CCqsa");
	}
	@Test
	public void startEndMinPathTest_3() throws TppCmdException{
		Assert.assertEquals(-1, tpp.startEndMinPath("CQ"));
		Assert.assertEquals(9, tpp.startEndMinPath(" AC"));
		Assert.assertEquals(9, tpp.startEndMinPath("BB "));
		Assert.assertEquals(5, tpp.startEndMinPath(" AD "));
	}
}
