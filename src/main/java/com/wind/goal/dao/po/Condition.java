package com.wind.goal.dao.po;

import java.sql.Timestamp;

/**
 * 条件
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-19
 */
public class Condition {
	private Integer conditionId;
	private String eventNo;
	private String name;
	private String identifyParams;
	private Timestamp updateTime;

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifyParams() {
		return identifyParams;
	}

	public void setIdentifyParams(String identifyParams) {
		this.identifyParams = identifyParams;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
