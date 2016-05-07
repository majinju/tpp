/**
 * Project Name:tpp
 * Date:2016年5月6日上午9:35:29
 * Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
 */

package org.test.tpp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;
import org.test.tpp.exception.TppException;

/**
 * H2数据库操作工具类<br/>
 * date: 2016年5月6日 上午9:35:29 <br/>
 * 
 * @author jingma@iflytek.com
 * @version
 */
public class H2Util {

	/**
	 * h2数据库
	 */
	private static Server server;

	/**
	 * 一直持有该连接，此处不考虑并发等情况。
	 */
	private static Connection conn = null;

	/**
	 * 启动数据 <br/>
	 * 
	 * @author jingma@iflytek.com
	 */
	public static void start() {
		try {
			LogUtil.info("正在启动h2...");
			server = Server.createWebServer(
				new String[]{}).start();
			LogUtil.info("启动成功：" + server.getStatus());
		} catch (SQLException e) {
			LogUtil.error("启动h2出错",e);
			throw new TppException("启动h2出错",e);
		}
	}

	/**
	 * 停止数据 <br/>
	 * 
	 * @author jingma@iflytek.com
	 */
	public static void stop() {
		if (server != null) {
			LogUtil.info("正在关闭h2...");
			server.stop();
			server = null;
			LogUtil.info("关闭成功.");
		}
	}
	/**
	 * 获取单例的数据库连接<br/>
	 * 
	 * @author jingma@iflytek.com
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getSingleConn() {
		if (conn == null) {
			conn = getConn();
		}
		return conn;
	}

	/**
	 * 获取数据库连接 <br/>
	 * 正式使用都是使用连接池（阿里的Druid数据库连接池不错，有很好的监控支持），一般不需要自己维护数据库连接。<br/>
	 * 
	 * @author jingma@iflytek.com
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static Connection getConn(){
		try {
			Class.forName("org.h2.Driver");
			// connect to h2
			Connection conn = DriverManager.getConnection(
					"jdbc:h2:mem:test", "sa", "sa");
			return conn;
		} catch (Exception e) {
			LogUtil.error("获取数据库连接出错",e);
			throw new TppException("获取数据库连接出错",e);
		}
	}
	/**
	 * 关闭数据库连接 <br/>
	 * 
	 * @author jingma@iflytek.com
	 * @param conn 要关闭的连接
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LogUtil.error("关闭h2出错",e);
			}
		}
	}
	/**
	 * 关闭数据库游标<br/>
	 * 
	 * @author jingma@iflytek.com
	 * @param stat 要关闭的游标
	 */
	public static void close(Statement stat) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				LogUtil.error("关闭数据库游标出错",e);
			}
		}
	}
	/**
	 * 关闭数据库结果集<br/>
	 * 
	 * @author jingma@iflytek.com
	 * @param rs 要关闭的结果集
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogUtil.error("关闭数据库结果集出错",e);
			}
		}
	}
	/**
	 * 关闭数据库连接 <br/>
	 * 
	 * @author jingma@iflytek.com
	 */
	public static void close() {
		close(conn);
		conn = null;
		stop();
	}

}
