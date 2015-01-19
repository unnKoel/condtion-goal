package com.wind.goal.dao.po;

import java.sql.Timestamp;

/**
 * 目标
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-19
 */
public class Goal {
	private String key; // 目标key
	private Integer conditionId; // 条件Id
	private String name; // 目标名称
	private Integer award; // 目标奖励
	private Timestamp updateTime; // 更新时间

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAward() {
		return award;
	}

	public void setAward(Integer award) {
		this.award = award;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
