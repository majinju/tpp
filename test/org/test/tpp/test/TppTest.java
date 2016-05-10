/**
 * Project Name:tpp
 * Date:2016年5月6日下午4:13:32
 * Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
 */

package org.test.tpp.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.test.tpp.TrainPathPlan;
import org.test.tpp.exception.TppCmdException;
import org.test.tpp.util.H2Util;
import org.test.tpp.util.LogUtil;

/**
 * 路线规划单元测试类 <br/>
 * date: 2016年5月6日 下午4:13:32 <br/>
 * 
 * @author jinjuma@yeah.net
 * @version 0.0.1
 */
public class TppTest {
	/**
	 * 铁路网路线数据
	 */
	// public static final String pathData =
	// "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
	public static final String pathData = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
	/**
	 * 路线规划对象
	 */
	protected TrainPathPlan tpp;
	/**
	* 用于异常测试
	*/
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * 测试初始化操作 <br/>
	 * 
	 * @author jinjuma@yeah.net
	 * @throws TppCmdException
	 */
	@Before
	public void init() {
		if (tpp == null) {
			tpp = new TrainPathPlan();
			tpp.initDatabase();
			try {
				tpp.importTrainPathData(pathData);
			} catch (TppCmdException e) {
				LogUtil.error("导入数据错误", e);
			}
		}
	}
	/**
	 * 测试结束操作 <br/>
	 * 
	 * @author jinjuma@yeah.net
	 */
	@After
	public void after() {
		H2Util.close();
		tpp = null;
	}
}
