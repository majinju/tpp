/**
* Project Name:tpp
* Date:2016年5月6日上午10:37:43
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.test.tpp.exception.TppCmdException;
import org.test.tpp.exception.TppException;
import org.test.tpp.util.H2Util;
import org.test.tpp.util.LogUtil;

/**
 * 火车路线规划主要实现 <br/>
 * date: 2016年5月6日 上午10:37:43 <br/>
 * @author jingma@iflytek.com
 * @version 0.0.1
 */
public class TrainPathPlan {
	/**
	* 通过起点找终点
	*/
	private static final String QUERY_END_BY_START = "SELECT END,DISTANCE FROM T_TRAIN_PATH T WHERE T.START=?";
	/**
	* 插入铁路路线数据SQL
	*/
	public static final String INSERT_SQL = "INSERT INTO T_TRAIN_PATH (START,END,DISTANCE) VALUES (?,?,?)";
	/**
	* 删除铁路路线数据SQL
	*/
	public static final String DELETE_SQL = "DELETE FROM T_TRAIN_PATH T WHERE T.START=? AND T.END=?";
	/**
	* 判断城市是否存在
	*/
	public static final String EXISTS_CITY_SQL = "SELECT 1 FROM T_CITY T WHERE T.CODE=?";
	/**
	* 判断城市是否存在
	*/
	public static final String TRAIN_PATH_DISTANCE_SQL = "SELECT DISTANCE FROM T_TRAIN_PATH T WHERE T.START=? and T.END=?";
	/**
	* 初始化数据库<br/>
	* @author jingma@iflytek.com
	* @return
	*/
	public boolean initDatabase(){
		Statement stat = null;
        try {
    		Connection conn = H2Util.getSingleConn();
			stat = conn.createStatement();
			//创建火车线路表
			stat.execute("create table T_TRAIN_PATH(START VARCHAR(255),END VARCHAR(255),DISTANCE NUMBER)");
			//创建城市表
			stat.execute("create table T_CITY(CODE VARCHAR(255))");
			return true;
		} catch (SQLException e) {
			throw new TppException("初始化数据库失败",e);
		}finally{
			H2Util.close(stat);
		}
	}
	/**
	* 导入火车路线数据 <br/>
	* 通过如下格式提供铁路图（AB5-->A到B单向铁路距离5个单位）：<br/>
	* AB5,BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
	* @author jingma@iflytek.com
	* @param data 数据字符串
	* @return 导入路线数
	 * @throws TppCmdException 
	*/
	public int importTrainPathData(String data) throws TppCmdException{
		if(data==null){
			throw new TppCmdException("数据字符串为空！");
		}
		if(!(data+",").matches("(\\s*[A-Z]{2}[0-9]+\\s*,)+")){
			throw new TppCmdException("#"+data+"#数据字符串格式不正确！");
		}
		String[] dataArr = data.split(",");
		Connection conn = H2Util.getSingleConn();
		PreparedStatement ps = null;
		Statement stat = null;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(INSERT_SQL);
			for(String tp:dataArr){
				char[] ca = tp.trim().toCharArray();
				ps.setString(1, ca[0]+"");
				ps.setString(2, ca[1]+"");
				ps.setLong(3, Integer.parseInt(tp.trim().substring(2)));
				ps.executeUpdate();
			}
			conn.commit();
			conn.setAutoCommit(true);
			updateCityData();
			LogUtil.debug("导入了"+dataArr.length+"条路线。");
			return dataArr.length;
		} catch (SQLException e) {
			throw new TppCmdException("导入火车路线数据 出错",e);
		}finally{
			H2Util.close(stat);
			H2Util.close(ps);
		}
	}
	/**
	* 更新城市数据 <br/>
	* @author jingma@iflytek.com
	* @throws TppCmdException 
	*/
	private void updateCityData() throws TppCmdException{
		Connection conn = H2Util.getSingleConn();
		Statement stat = null;
		try {
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			stat.executeUpdate("DELETE FROM T_CITY");
			stat.executeUpdate("INSERT INTO T_CITY (SELECT DISTINCT START FROM T_TRAIN_PATH UNION SELECT DISTINCT END FROM T_TRAIN_PATH )");
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new TppCmdException("导入火车路线数据 出错",e);
		}finally{
			H2Util.close(stat);
		}
	}
	/**
	* 显示火车路线数据 <br/>
	* @author jingma@iflytek.com
	 * @throws TppCmdException 
	*/
	public void showTrainPathData() throws TppCmdException{
		Connection conn = H2Util.getSingleConn();
		Statement stat = null;
		try {
			stat = conn.createStatement();
			LogUtil.debug("t_train_path表数据");
			printResult(stat.executeQuery("select * from t_train_path"));
			LogUtil.debug("t_city表数据");
			printResult(stat.executeQuery("select * from t_city"));
		} catch (SQLException e) {
			throw new TppCmdException("显示火车路线数据 出错",e);
		}finally{
			H2Util.close(stat);
		}
	}
	/**
	* 删除火车路线数据 <br/>
	* 通过如下格式提供要删除的铁线路（AB-->A到B单向铁路）：<br/>
	* AB,BC<br/>
	* @author jingma@iflytek.com
	* @param data 数据字符串
	* @return 导入路线数
	 * @throws TppCmdException 
	*/
	public int deleteTrainPathData(String data) throws TppCmdException{
		if(data==null){
			throw new TppCmdException("数据字符串为空！");
		}
		if(!(data+",").matches("(\\s*[A-Z]{2}\\s*,)+")){
			throw new TppCmdException("#"+data+"#数据字符串格式不正确！");
		}
		String[] dataArr = data.split(",");
		Connection conn = H2Util.getSingleConn();
		PreparedStatement ps = null;
		int sum = 0;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(DELETE_SQL);
			for(String tp:dataArr){
				char[] ca = tp.trim().toCharArray();
				ps.setString(1, ca[0]+"");
				ps.setString(2, ca[1]+"");
				sum += ps.executeUpdate();
			}
			conn.commit();
			conn.setAutoCommit(true);
			updateCityData();
			LogUtil.debug("删除了："+sum+"条数据");
			return sum;
		} catch (SQLException e) {
			throw new TppCmdException("删除火车路线数据 出错",e);
		}finally{
			H2Util.close(ps);
		}
	}
	/**
	* 根据给定路线计算距离<br/>
	* @author jingma@iflytek.com
	* @param path 路线
	* @return 距离
	 * @throws TppCmdException 
	*/
	public long trainPathDistance(String path) throws TppCmdException{
		if(path==null){
			throw new TppCmdException("条件字符串为空！");
		}
		if(!(path+"-").matches("(\\s*[A-Z]{1}\\s*-)+")){
			throw new TppCmdException("#"+path+"#条件字符串格式不正确！");
		}
		String[] cityArr = path.split("-");
		if(!isValidCity(cityArr)){
			return 0;
		}
		long sum = 0l;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection conn = H2Util.getSingleConn();
			ps = conn.prepareStatement(TRAIN_PATH_DISTANCE_SQL);
			for(int i=1;i<cityArr.length;i++){
				ps.setString(1, cityArr[i-1].trim());
				ps.setString(2, cityArr[i].trim());
				rs = ps.executeQuery();
				if(rs.next()){
					sum += rs.getLong(1);
				}else{
					LogUtil.debug(cityArr[i-1]+"-"+cityArr[i]+"路线不存在");
					LogUtil.debug(path+"路线错误");
					return 0;
				}
				rs.close();
			}
			LogUtil.debug(path+"路线距离："+sum);
			return sum;
		}catch(Exception e){
			throw new TppCmdException("根据给定路线计算距离失败",e);
		}finally{
			H2Util.close(ps);
			H2Util.close(rs);
		}
	}
	/**
	* 给定起点、终点、最大经过站数量给出有多少种路线 <br/>
	* 参数格式：AB3 --> A到B最多经过3个站（不包括起点）
	* @author jingma@iflytek.com
	* @param param 计算需要的参数
	* @return 路线数量
	 * @throws TppCmdException 
	*/
	public long startEndMaxStops(String param) throws TppCmdException{
		List<PathPanelPoint> result = startEndMaxStopsList(param);
		printPPPList(param,result);
		LogUtil.debug(param+"对应的线路数量："+result.size());
		return result.size();
	}
	/**
	* 给定起点、终点、刚好经过站数量给出有多少种路线。<br/>
	* 参数格式：AB3 --> A到B最多经过3个站（不包括起点）
	* @author jingma@iflytek.com
	* @param param 计算需要的参数
	* @return 路线数量
	 * @throws TppCmdException 
	*/
	public long startEndStops(String param) throws TppCmdException{
		List<PathPanelPoint> result = startEndMaxStopsList(param);
		//路线数量
		long sum = 0l;
		//经过站数量
		int size = Integer.parseInt(param.trim().substring(2));
		for(PathPanelPoint ppp:result){
			if(ppp.getDepth()==size){
				sum++;
				LogUtil.debug(param+"匹配的路线"+sum+"："+getPath(ppp));
			}
		}
		LogUtil.debug(param+"对应的线路数量："+sum);
		return sum;
	}
	/**
	* 给定起点、终点、最大经过站数量给出全部路线 <br/>
	* 参数格式：AB3 --> A到B最多经过3个站（不包括起点）
	* @author jingma@iflytek.com
	* @param param 计算需要的参数
	* @return 路线列表，失败时返回null
	 * @throws TppCmdException 
	*/
	public List<PathPanelPoint> startEndMaxStopsList(String param) throws TppCmdException{
		if(param==null){
			throw new TppCmdException("参数为空！");
		}
		if(!(param).matches("(\\s*[A-Z]{2}[0-9]+\\s*){1}")){
			throw new TppCmdException("#"+param+"#条件字符串格式不正确！");
		}
		//起点
		String start = param.trim().substring(0, 1);
		//终点
		String end = param.trim().substring(1, 2).trim();
		//最大经过站数量
		int size = Integer.parseInt(param.trim().substring(2).trim());
		List<PathPanelPoint> pppList = new ArrayList<PathPanelPoint>();
		if(!isValidCity(new String[]{start,end})){
			return pppList;
		}
		Stack<PathPanelPoint> searchStack = new Stack<PathPanelPoint>();
		searchStack.push(new PathPanelPoint(start));
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection conn = H2Util.getSingleConn();
			ps = conn.prepareStatement(QUERY_END_BY_START);
			while(!searchStack.isEmpty()){
				PathPanelPoint startTemp = searchStack.pop();
				ps.setString(1, startTemp.getCode());
				rs = ps.executeQuery();
				while(rs.next()){
					PathPanelPoint endTemp = new PathPanelPoint(rs.getString(1),startTemp);
					if(end.equals(endTemp.getCode())){
						pppList.add(endTemp);
					}
					//节点未达设定深度则加入搜索队列
					if(endTemp.getDepth()<size){
						searchStack.push(endTemp);
					}
				}
				rs.close();
			}
			return pppList;
		}catch(Exception e){
			throw new TppCmdException("给定起点、终点、最大经过站数量给出有多少种路线失败",e);
		}finally{
			H2Util.close(ps);
			H2Util.close(rs);
		}
	}
	/**
	* 给定起点和终点给出最短路线距离。<br/>
	* 参数格式：AB --> A到B
	* @author jingma@iflytek.com
	* @param param 计算需要的参数
	* @return 最短距离
	 * @throws TppCmdException 
	*/
	public long startEndMinPath(String param) throws TppCmdException{
		if(param==null){
			throw new TppCmdException("参数为空！");
		}
		if(!(param).matches("\\s*([A-Z]{2}){1}\\s*")){
			throw new TppCmdException("#"+param+"#条件字符串格式不正确！");
		}
		//起点
		String start = param.trim().substring(0, 1);
		//终点
		String end = param.trim().substring(1, 2);
		if(!isValidCity(new String[]{start,end})){
			return -1;
		}
		PathPanelPoint result = null;
		Stack<PathPanelPoint> searchStack = new Stack<PathPanelPoint>();
		searchStack.push(new PathPanelPoint(start));
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection conn = H2Util.getSingleConn();
			ps = conn.prepareStatement(QUERY_END_BY_START);
			while(!searchStack.isEmpty()){
				PathPanelPoint startTemp = searchStack.pop();
				ps.setString(1, startTemp.getCode());
				rs = ps.executeQuery();
				while(rs.next()){
					PathPanelPoint endTemp = new PathPanelPoint(rs.getString(1),startTemp,rs.getLong(2));
					if(end.equals(endTemp.getCode())){
						if(result==null||endTemp.getDistance()<result.getDistance()){
							//如果还没有找到一个可能的结果或到当前节点的距离比到已知结果的距离短
							result = endTemp;
							
						}
					} else if(result==null||(endTemp.getDistance()<result.getDistance()&&!endTemp.isAlreadyPass())){
						//如果还没有找到一个可能的结果或(到当前节点的距离比到已知结果的距离短且该节点没有重复走)时，将当前节点加入搜索
						searchStack.push(endTemp);
					}
				}
				rs.close();
			}
			String line = getPath(result);
			LogUtil.debug(param+"的最短线路："+line+",距离："+result.getDistance());
			return result.getDistance();
		}catch(Exception e){
			throw new TppCmdException("给定起点、终点、最大经过站数量给出有多少种路线失败",e);
		}finally{
			H2Util.close(ps);
			H2Util.close(rs);
		}
	}
	/**
	* 给定起点、终点、小于最大路线距离给出有多少种路线。<br/>
	* 参数格式：AB50 --> A到B最大行程小于50
	* @author jingma@iflytek.com
	* @param param 计算需要的参数
	* @return 路线数
	 * @throws TppCmdException 
	*/
	public long startEndMaxDistance(String param) throws TppCmdException{
		if(param==null){
			throw new TppCmdException("参数为空！");
		}
		if(!(param).matches("\\s*([A-Z]{2}[0-9]+){1}\\s*")){
			throw new TppCmdException("#"+param+"#条件字符串格式不正确！");
		}
		//起点
		String start = param.trim().substring(0, 1);
		//终点
		String end = param.trim().substring(1, 2);
		//最大距离
		int maxDistance = Integer.parseInt(param.trim().substring(2).trim());
		if(!isValidCity(new String[]{start,end})){
			return 0;
		}
		List<PathPanelPoint> pathList = new ArrayList<PathPanelPoint>();
		Stack<PathPanelPoint> searchStack = new Stack<PathPanelPoint>();
		searchStack.push(new PathPanelPoint(start));
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection conn = H2Util.getSingleConn();
			ps = conn.prepareStatement(QUERY_END_BY_START);
			while(!searchStack.isEmpty()){
				PathPanelPoint startTemp = searchStack.pop();
				ps.setString(1, startTemp.getCode());
				rs = ps.executeQuery();
				while(rs.next()){
					PathPanelPoint endTemp = new PathPanelPoint(rs.getString(1),startTemp,rs.getLong(2));
					if(end.equals(endTemp.getCode())&&endTemp.getDistance()<maxDistance){
						pathList.add(endTemp);
					}
					if(endTemp.getDistance()<maxDistance){
						searchStack.push(endTemp);
					}
				}
				rs.close();
			}
			printPPPList(param, pathList);
			LogUtil.debug(param+"路线数量："+pathList.size());
			return pathList.size();
		}catch(Exception e){
			throw new TppCmdException("给定起点、终点、最大经过站数量给出有多少种路线失败",e);
		}finally{
			H2Util.close(ps);
			H2Util.close(rs);
		}
	}
	/**
	* 递归实现：根据节点得到路线 <br/>
	* @author jingma@iflytek.com
	* @param endTemp 终点节点
	* @return 路线
	*/
	public static String getPath(PathPanelPoint endTemp) {
		if(endTemp.getParent()!=null){
			return getPath(endTemp.getParent())+"-"+endTemp.getCode();
		}else{
			return endTemp.getCode();
		}
	}
	/**
	* 根据节点列表打印出对应的路线 <br/>
	* @author jingma@iflytek.com
	* @param param 条件
	* @param result 要打印路线的节点列表
	*/
	private void printPPPList(String param,List<PathPanelPoint> result) {
		for(int i=0;i<result.size();i++){
			LogUtil.debug(param+"-->"+(i+1)+":"+getPath(result.get(i))+"-->"+result.get(i).getDistance());
		}
	}
	/**
	* 打印出查询结果集 <br/>
	* @author jingma@iflytek.com
	* @param rs
	 * @throws TppCmdException 
	*/
	public void printResult(ResultSet rs) throws TppCmdException {
		try {
			ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			for(int i=1;i<=colCount;i++){
				System.out.print(meta.getColumnName(i)+"\t");
			}
			while(rs.next()){
				System.out.println();
				for(int i=1;i<=colCount;i++){
					System.out.print(rs.getString(i)+"\t");
				}
			}
			System.out.println();
		} catch (SQLException e) {
			throw new TppCmdException("打印出查询结果集失败",e);
		}finally{
			H2Util.close(rs);
		}
	}
	/**
	* 判断给定城市是否都有效 <br/>
	* @author jingma@iflytek.com
	* @param cityArr 城市列表
	* @return 全部有效则返回true，否则返回false
	 * @throws TppCmdException 
	*/
	private boolean isValidCity(String[] cityArr) throws TppCmdException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection conn = H2Util.getSingleConn();
			ps = conn.prepareStatement(EXISTS_CITY_SQL);
			for(String c:cityArr){
				ps.setString(1, c.trim());
				rs = ps.executeQuery();
				if(!rs.next()){
					LogUtil.debug(c+"城市不在铁路网中！");
					return false;
				}
				rs.close();
			}
			return true;
		}catch(Exception e){
			throw new TppCmdException("判断给定城市是否都有效失败",e);
		}finally{
			H2Util.close(ps);
			H2Util.close(rs);
		}
	}
}
