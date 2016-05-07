/**
* Project Name:tpp
* Date:2016年5月6日下午5:32:07
* Copyright (c) 2016, jingma@iflytek.com All Rights Reserved.
*/

package org.test.tpp;

import java.io.Serializable;

/**
 * 道路节点对象 <br/>
 * date: 2016年5月6日 下午5:32:07 <br/>
 * @author jingma@iflytek.com
 * @version 
 */
public class PathPanelPoint implements Serializable{
	/**
	* 序列化版本ID
	*/
	private static final long serialVersionUID = -5872687697659803954L;
	/**
	 * Creates a new instance of PathPanelPoint.
	 */
	public PathPanelPoint() {
		super();
	}
	
	public PathPanelPoint(String code) {
		super();
		this.code = code;
	}

	public PathPanelPoint(String code, PathPanelPoint parent) {
		super();
		this.code = code;
		this.parent = parent;
		this.depth = parent.getDepth()+1;
	}
	/**
	* Creates a new instance of PathPanelPoint.
	* @param code 本节点编码
	* @param parent 父节点
	* @param upPointDistance 距离上一个节点的距离
	*/
	public PathPanelPoint(String code, PathPanelPoint parent,long upPointDistance) {
		super();
		this.code = code;
		this.parent = parent;
		this.depth = parent.getDepth()+1;
		this.distance = parent.distance+upPointDistance;
	}

	/**
	* 该节点
	*/
	private String code;
	
	/**
	* 父节点
	*/
	private PathPanelPoint parent;

	/**
	* 节点深度
	*/
	private int depth;
	/**
	* 该节点距离起点的距离
	*/
	private long distance;
	/**
	 * @return code 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return parent 
	 */
	public PathPanelPoint getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(PathPanelPoint parent) {
		this.parent = parent;
	}

	/**
	 * @return depth 
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return distance 
	 */
	public long getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(long distance) {
		this.distance = distance;
	}

	/**
	* 正规项目中toString最好用Fastjson等自动生成JSON串
	* @see java.lang.Object#toString()
	*/
	@Override
	public String toString() {
		return "PathPanelPoint [code=" + code + ", parent=" + parent
				+ ", depth=" + depth + ", distance=" + distance + "]";
	}

	/**
	* 判断该路线前面是否已经经过了这个节点 <br/>
	* @author jingma@iflytek.com
	* @return
	*/
	public boolean isAlreadyPass() {
		PathPanelPoint p = parent;
		while(p!=null){
			if(p.getCode().equals(getCode())){
				return true;
			}
			p = p.getParent();
		}
		return false;
	}


}
