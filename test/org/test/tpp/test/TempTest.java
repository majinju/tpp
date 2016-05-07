/**
* Project Name:tpp
* Date:2016年5月6日上午10:57:15
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * 临时技术验证使用 <br/>
 * date: 2016年5月6日 上午10:57:15 <br/>
 * @author jingma@iflytek.com
 * @version 
 */
public class TempTest {
	/**
	* 测试正则匹配 <br/>
	* @author jingma@iflytek.com
	*/
	@Test
	public void matchTest(){
		String regex = "(\\s*[A-Z]{2}[0-9]+\\s*,\\s*)+";
		String data = "AB5,BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
		Assert.assertTrue((data+",").matches(regex));
		Assert.assertFalse(("321,").matches(regex));
		Assert.assertFalse(("A21,").matches(regex));
		Assert.assertFalse(("A2C,").matches(regex));
		Assert.assertFalse(("AB2马静,").matches(regex));
		Assert.assertFalse(("）AB2, ").matches(regex));
	}
}
