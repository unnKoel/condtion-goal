package com.wind.goal.dao.po;

import java.sql.Timestamp;

/**
 * 事件
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-19
 */
public class Event {
	private String eventNo; // 事件号
	private String eventName; // 事件名称
	private String eventExplain; // 事件说明
	private String paramters; // 参数列表
	private Timestamp updateTime; // 更新时间

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventExplain() {
		return eventExplain;
	}

	public void setEventExplain(String eventExplain) {
		this.eventExplain = eventExplain;
	}

	public String getParamters() {
		return paramters;
	}

	public void setParamters(String paramters) {
		this.paramters = paramters;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
