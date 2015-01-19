package com.wind.goal.dao.po;

import java.io.Serializable;

/**
 * 用户条件
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-16
 */
public class UserCondition implements Serializable {

	private static final long serialVersionUID = -6397232151124702265L;
	private Integer userId;
	private Integer conditionId;
	private String conditionValue;

	public UserCondition() {}

	public UserCondition(Integer userId, Integer conditionId, String conditionValue) {
		super();
		this.userId = userId;
		this.conditionId = conditionId;
		this.conditionValue = conditionValue;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}
}
