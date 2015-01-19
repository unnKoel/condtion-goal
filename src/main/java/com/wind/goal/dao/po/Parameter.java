package com.wind.goal.dao.po;

import java.sql.Timestamp;

import com.wind.goal.ParamterVO;

/**
 * 参数
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-19
 */
public class Parameter {
	private String name; // 参数名
	private String chinaName; // 中文名
	private String type; // 参数类型(1:int, 2:string,3:boolean 4:query)
	private String operate; // 比较类型（>,=,<,>=,<=）
	private String change; // 变化类型（1：递增 2：替换）
	private String relativeParams; // 关联参数
	private Timestamp updateTime; // 更新时间

	public ParamterVO getParamterVO() {
		ParamterVO paramterVO = new ParamterVO();
		paramterVO.setCChange(this.getChange());
		paramterVO.setCName(this.name);
		paramterVO.setCOperate(this.operate);
		paramterVO.setCType(this.type);
		String[] relativeParamList = null;
		if (relativeParams != null && !relativeParams.isEmpty() && relativeParams.indexOf(",") != -1) {
			relativeParamList = relativeParams.split(",");
		}
		paramterVO.setRelativeParams(relativeParamList);
		return paramterVO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChinaName() {
		return chinaName;
	}

	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getRelativeParams() {
		return relativeParams;
	}

	public void setRelativeParams(String relativeParams) {
		this.relativeParams = relativeParams;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
