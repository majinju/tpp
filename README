### 注：
- 对项目一无所知的，请先阅读：doc/铁路路线规划器.docx，了解这大概是个什么问题。
- 我的开发环境：win10、jdk1.7、eclipse

1. 相关操作都需要依赖jdk，建议安装jdk1.7.
2. 以下是文件结构：

```

    │  .classpath
    │  .project
    │  build.xml
    │  
    ├─doc
    │  │  test.tpp   #DEMO脚本
    │  │  tpp_data.txt #DEMO数据文件
    │  │  铁路路线规划器.docx  #分析文档
    │  │  
    │  └─测试结果
    │      │  org.eclipse.ant.internal.launching.remote.InternalAntRunner.html
    │      │  
    │      └─org.eclipse.ant.internal.launching.remote.InternalAntRunner_files
    │              DateUtil.html
    │              H2Util.html
    │              LogUtil.html
    │              Main.html
    │              PathPanelPoint.html
    │              StartEndMaxDistanceTest.html
    │              TppCmdException.html
    │              TppException.html
    │              TrainPathPlan.html
    │              
    ├─lib #需要将h2-1.2.131.jar放到该目录
    ├─src #源码
    │  └─org
    │      └─test
    │          └─tpp
    │              │  Main.java
    │              │  PathPanelPoint.java
    │              │  TrainPathPlan.java
    │              │  
    │              ├─exception
    │              │      TppCmdException.java
    │              │      TppException.java
    │              │      
    │              └─util
    │                      DateUtil.java
    │                      H2Util.java
    │                      LogUtil.java
    │                      
    └─test #测试源码
        └─org
            └─test
                └─tpp
                    └─test
                            DeleteTrainPathDataTest.java
                            ShowTrainPathDataTest.java
                            StartEndMaxDistanceTest.java
                            StartEndMaxStopsTest.java
                            StartEndMinPathTest.java
                            StartEndStopsTest.java
                            TempTest.java
                            TppTest.java
                            TrainPathDistanceTest.java
```

3. tpp文件夹是一个完整的java工程，可以直接导入eclipse，但每个人环境有差异，可能还是需要有一定java能力的人来操作。
4. 导入成功后，可以运行org.test.tpp.Main主类，有详细的帮助说明。
5. 测试结果在doc\测试结果文件夹下。
6. 问题分析文档也在doc文件夹中。
7. 使用帮助（这里基于打包好的jar文件，你可以自行打包或从压缩包中取）：
-  常规运行：java -jar tpp.jar ，若正常运行将看到详细的使用说明，效果如下所示：

```


	E:\temp>java -jar tpp.jar
	帮助信息：
	一般不需要参数，但可以使用"main -ef .\text.tpp"来运行脚本
	\q      退出
	\h      帮助
	\echo 参数      打印自己的字符串
	\idf 数据文件   通过文件导入铁路线路数据
	\ef 命令文件    通过文件录入命令，命令文件中禁止调用\ef命令
	\id 数据        通过命令行导入铁路线路数据,eg:\id AB5,BC4, CD8
	\sd     通过命令行显示铁路线路数据
	\dd 数据        通过命令行删除铁路线路数据,eg:\dd AB,BC, CD。若想清空路线数据，可以重启。
	\tpd 路线       根据给定路线计算距离,eg:\tpd A-B-C
	\sems 参数      给定起点、终点、最大经过站数量给出有多少种路线,eg:\sems AB5
	\ses 参数       给定起点、终点、刚好经过站数量给出有多少种路线,eg:\ses AB5
	\semp 参数      给定起点和终点给出最短路线距离,eg:\semp AB
	\semd 参数      给定起点、终点、小于最大路线距离给出有多少种路线,eg:\semd AB30
	==>
```

- 可以不使用交互方式使用，可以使用：java -jar tpp.jar -ef test.tpp >tt.txt 执行一个脚本文件，并将结果输出到tt.txt文件。
以下就是上面命令输出到tt.txt文件的内容，这里运行的脚本完整的演示了各种命令的使用：

```


	导入数据
	2016-05-07 13:26:02 DEBUG 导入了9条路线。
	2016-05-07 13:26:02 DEBUG 导入了2条路线。
	2016-05-07 13:26:02 DEBUG 导入了1条路线。
	显示数据
	2016-05-07 13:26:02 DEBUG t_train_path表数据
	START	END	DISTANCE	
	A	B	5	
	B	C	4	
	C	D	8	
	D	C	8	
	D	E	6	
	A	D	5	
	C	E	2	
	E	B	3	
	A	E	7	
	M	C	5	
	M	D	3	
	M	E	5	
	2016-05-07 13:26:02 DEBUG t_city表数据
	CODE	
	A	
	B	
	C	
	D	
	E	
	M	
	删除数据
	2016-05-07 13:26:02 DEBUG 删除了：3条数据
	题目中的测试样例
	2016-05-07 13:26:02 DEBUG A-B-C路线距离：9
	2016-05-07 13:26:02 DEBUG A-D路线距离：5
	2016-05-07 13:26:02 DEBUG A-D-C路线距离：13
	2016-05-07 13:26:02 DEBUG A-E-B-C-D路线距离：22
	2016-05-07 13:26:02 DEBUG E-D路线不存在
	2016-05-07 13:26:02 DEBUG A-E-D路线错误
	2016-05-07 13:26:02 DEBUG CC3-->1:C-E-B-C-->0
	2016-05-07 13:26:02 DEBUG CC3-->2:C-D-C-->0
	2016-05-07 13:26:02 DEBUG CC3对应的线路数量：2
	2016-05-07 13:26:02 DEBUG AC4匹配的路线1：A-D-E-B-C
	2016-05-07 13:26:02 DEBUG AC4匹配的路线2：A-D-C-D-C
	2016-05-07 13:26:02 DEBUG AC4匹配的路线3：A-B-C-D-C
	2016-05-07 13:26:02 DEBUG AC4对应的线路数量：3
	2016-05-07 13:26:02 DEBUG AC的最短线路：A-B-C,距离：9
	2016-05-07 13:26:02 DEBUG BB的最短线路：B-C-E-B,距离：9
	2016-05-07 13:26:02 DEBUG CC30-->1:C-E-B-C-->9
	2016-05-07 13:26:02 DEBUG CC30-->2:C-E-B-C-E-B-C-->18
	2016-05-07 13:26:02 DEBUG CC30-->3:C-E-B-C-E-B-C-E-B-C-->27
	2016-05-07 13:26:02 DEBUG CC30-->4:C-E-B-C-D-C-->25
	2016-05-07 13:26:02 DEBUG CC30-->5:C-D-C-->16
	2016-05-07 13:26:02 DEBUG CC30-->6:C-D-E-B-C-->21
	2016-05-07 13:26:02 DEBUG CC30-->7:C-D-C-E-B-C-->25
	2016-05-07 13:26:02 DEBUG CC30路线数量：7
```

8. 如有其它疑问可以发邮件到： jinjuma@yeah.net