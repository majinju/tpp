/**
* Project Name:tpp
* Date:2016年5月6日上午10:57:15
* Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
*/

package org.test.tpp.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

/**
 * 临时技术验证使用 <br/>
 * date: 2016年5月6日 上午10:57:15 <br/>
 * @author jinjuma@yeah.net
 * @version 
 */
public class TempTest {
	/**
	* 测试正则匹配 <br/>
	* @author jinjuma@yeah.net
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
	/**
	* 堆测试 <br/>
	* @author jinjuma@yeah.net
	*/
	@Test
	public void stackTest(){
		Stack<String> s = new Stack<String>();
		s.push("1");
		s.push("2");
		s.push("3");
		while(!s.isEmpty()){
			System.out.println(s.pop());
		}
	}
	/**
	* 队列测试 <br/>
	* @author jinjuma@yeah.net
	*/
	@Test
	public void queueTest(){
		Queue<String> s = new LinkedList<String>();
		s.add("1");
		s.add("2");
		s.add("3");
		while(!s.isEmpty()){
			System.out.println(s.remove());
		}
	}
}
