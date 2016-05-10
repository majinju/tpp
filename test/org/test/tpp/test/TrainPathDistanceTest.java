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
public class TrainPathDistanceTest extends TppTest {

	/**
	* 测试根据给定路线计算距离 <br/>
	* @author jinjuma@yeah.net
	 * @throws TppCmdException 
	*/
	@Test
	public void trainPathDistanceTest_1() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.trainPathDistance(null);
	}
	@Test
	public void trainPathDistanceTest_2() throws TppCmdException{
		thrown.expect(TppCmdException.class);
		tpp.trainPathDistance("A-B-Caa");
	}
	@Test
	public void trainPathDistanceTest_3() throws TppCmdException{
		Assert.assertEquals(0, tpp.trainPathDistance("A-B-C-M"));
		Assert.assertEquals(9, tpp.trainPathDistance(" A-B-C "));
		Assert.assertEquals(9, tpp.trainPathDistance("A- B- C"));
		Assert.assertEquals(5, tpp.trainPathDistance("A -D"));
		Assert.assertEquals(13, tpp.trainPathDistance("A-D-C"));
		Assert.assertEquals(22, tpp.trainPathDistance("A-E-B-C-D"));
		Assert.assertEquals(0, tpp.trainPathDistance("A-E-D"));
	}
}
