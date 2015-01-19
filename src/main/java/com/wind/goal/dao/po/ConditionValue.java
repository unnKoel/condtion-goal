package com.wind.goal.dao.po;

import java.sql.Timestamp;

/**
 * 条件值
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-19
 */
public class ConditionValue {
	private Integer valueId;
	private Integer conditionId;
	private String requireDesc;
	private String requireValue;
	private Timestamp updateTime;

	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getRequireDesc() {
		return requireDesc;
	}

	public void setRequireDesc(String requireDesc) {
		this.requireDesc = requireDesc;
	}

	public String getRequireValue() {
		return requireValue;
	}

	public void setRequireValue(String requireValue) {
		this.requireValue = requireValue;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
