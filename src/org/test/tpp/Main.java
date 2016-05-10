/**
* Project Name:tpp
* Date:2016年5月6日上午9:35:08
* Copyright (c) 2016, jinjuma@yeah.net All Rights Reserved.
*/

package org.test.tpp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.test.tpp.exception.TppCmdException;
import org.test.tpp.util.LogUtil;


/**
 * 铁路路线规划主类 <br/>
 * 本类主要是为路线规划器提供一个操作接口，不属于试题主要考核内容，不编写单元测试类<br/>
 * date: 2016年5月6日 上午9:35:08 <br/>
 * @author jinjuma@yeah.net
 * @version 0.0.1
 */
public class Main {
	/**
	* 规划器实例
	*/
	private static TrainPathPlan tpp;

	/**
	* 铁路路线规划起点 <br/>
	* @author jinjuma@yeah.net
	* @param args 一般不需要参数，但可以使用<b>main -ef .\text.tpp</b>来运行脚本
	 * @throws InterruptedException 
	*/
	public static void main(String[] args) throws InterruptedException {
		tpp = new TrainPathPlan();
		tpp.initDatabase();
		if(args.length==2){
			if("-ef".equals(args[0])){
				try {
					execTppCmd("\\ef "+args[1]);
				} catch (TppCmdException e) {
					System.err.println(e.getMessage());
					//正常情况，这里的日志都是记录到文件中。这里输出到控制台体验不是很好。
					LogUtil.error("命令执行错误", e);
				}
			}else{
				System.err.println("目前只支持-ef指令");
			}
			return;
		}else if(args.length>0){
			System.err.println("一般不需要参数，但可以使用\"main -ef .\\text.tpp\"来运行脚本");
		}
		Scanner sc = new Scanner(System.in);
		String line = null;
		printHelp();
		while(true){
			System.out.print("==>");
			line = sc.nextLine();
			try {
				if(!execTppCmd(line)){
					break;
				}
			} catch (TppCmdException e) {
				System.err.println(e.getMessage());
				//正常情况，这里的日志都是记录到文件中。这里输出到控制台体验不是很好。
				LogUtil.error("命令执行错误", e);
			}
		}
		sc.close();
	}

	/**
	* 执行规划器命名 <br/>
	* @author jinjuma@yeah.net
	* @param line 一行规划器命令
	* @return true:继续接收命令，false:结束输入
	* @throws TppCmdException 
	*/
	public static boolean execTppCmd(String line) throws TppCmdException {
		//退出
		if("\\q".equals(line.trim().toLowerCase())){
			return false;
		}else if(line.startsWith("\\idf ")){
			String filePath = line.substring(5);
			File file = new File(filePath);
			Scanner sc;
			try {
				sc = new Scanner(file);
				while(sc.hasNextLine()){
					String tc = sc.nextLine();
					tpp.importTrainPathData(tc);
				}
			} catch (FileNotFoundException e) {
				System.out.println(file.getAbsolutePath()+"文件不存在！");
			}
		}else if(line.startsWith("\\ef ")){
			String filePath = line.substring(4);
			File file = new File(filePath);
			Scanner sc;
			try {
				sc = new Scanner(file);
				int lineNumber = 0;
				while(sc.hasNextLine()){
					String tc = sc.nextLine();
					lineNumber++;
					//空行或注释
					if("".equals(tc)||tc.startsWith("#")){
						continue;
					}
					//命令文件中禁用\ef命令，避免递归调用，导致系统崩溃
					if(tc.startsWith("\\ef")){
						System.err.println("ERROR:命令文件中禁止调用\\ef命令");
						break;
					}
					try {
						execTppCmd(tc);
					} catch (TppCmdException e) {
						LogUtil.error("命令执行错误", e);
						System.out.println(file.getAbsolutePath()+"文件第"+lineNumber
								+"行命令错误");
						//遇到错误命令时，停止命令文件的执行
						break;
					}
				}
				sc.close();
			} catch (FileNotFoundException e) {
				System.out.println(file.getAbsolutePath()+"文件不存在！");
			}
		}else if(line.startsWith("\\id ")){
			tpp.importTrainPathData(line.substring(4));
		}else if(line.startsWith("\\dd ")){
			tpp.deleteTrainPathData(line.substring(4));
		}else if(line.startsWith("\\sd")){
			tpp.showTrainPathData();
		}else if(line.startsWith("\\tpd ")){
			tpp.trainPathDistance(line.substring(5));
		}else if(line.startsWith("\\sems ")){
			tpp.startEndMaxStops(line.substring(6));
		}else if(line.startsWith("\\ses ")){
			tpp.startEndStops(line.substring(5));
		}else if(line.startsWith("\\semp ")){
			tpp.startEndMinPath(line.substring(6));
		}else if(line.startsWith("\\semd ")){
			tpp.startEndMaxDistance(line.substring(6));
		}else if(line.startsWith("\\echo ")){
			System.out.println(line.substring(6));
		}else if(!line.equals("")||line.startsWith("\\h")){
			printHelp();
		}
		return true;
	}

	/**
	* 打印帮助信息 <br/>
	* @author jinjuma@yeah.net
	*/
	public static void printHelp() {
		System.out.println("帮助信息：");
		System.out.println("一般不需要参数，但可以使用\"main -ef .\\text.tpp\"来运行脚本");
		System.out.println("\\q\t退出");
		System.out.println("\\h\t帮助");
		System.out.println("\\echo 参数\t打印自己的字符串");
		System.out.println("\\idf 数据文件\t通过文件导入铁路线路数据");
		System.out.println("\\ef 命令文件\t通过文件录入命令，命令文件中禁止调用\\ef命令");
		System.out.println("\\id 数据\t通过命令行导入铁路线路数据,eg:\\id AB5,BC4, CD8");
		System.out.println("\\sd\t通过命令行显示铁路线路数据");
		System.out.println("\\dd 数据\t通过命令行删除铁路线路数据,eg:\\dd AB,BC, CD。若想清空路线数据，可以重启。");
		System.out.println("\\tpd 路线\t根据给定路线计算距离,eg:\\tpd A-B-C");
		System.out.println("\\sems 参数\t给定起点、终点、最大经过站数量给出有多少种路线,eg:\\sems AB5");
		System.out.println("\\ses 参数\t给定起点、终点、刚好经过站数量给出有多少种路线,eg:\\ses AB5");
		System.out.println("\\semp 参数\t给定起点和终点给出最短路线距离,eg:\\semp AB");
		System.out.println("\\semd 参数\t给定起点、终点、小于最大路线距离给出有多少种路线,eg:\\semd AB30");
	}
}
