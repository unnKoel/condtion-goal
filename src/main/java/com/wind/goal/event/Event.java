package com.wind.goal.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("serial")
public class Event extends EventObject {

	public Event() {}

	public Event(Object source) {
		super(source);
	}

	public Event(Object source, Map<String, Object> eventParamMap, String eventNO, Set<String> paramKeys) {
		super(source);
		this.eventParamMap = eventParamMap;
		this.eventNO = eventNO;
		this.paramKeys = paramKeys;
	}

	/**
	 * 要求的参数Keys范围
	 */
	private Set<String> paramKeys;
	/**
	 * 事件参数
	 */
	private Map<String, Object> eventParamMap = new HashMap<String, Object>();
	/**
	 * 事件号
	 */
	private String eventNO;

	/**
	 * 用户ID
	 */
	private Long userId;

	public void putParam(String paramName, Object paramValue) {
		eventParamMap.put(paramName, paramValue);
	}

	public Map<String, Object> getEventParamMap() {
		return eventParamMap;
	}

	public void setEventParamMap(Map<String, Object> eventParamMap) {
		this.eventParamMap = eventParamMap;
	}

	public String getEventNO() {
		return eventNO;
	}

	public void setEventNO(String eventNO) {
		this.eventNO = eventNO;
	}

	public Set<String> getParamKeys() {
		return paramKeys;
	}

	public void setParamKeys(Set<String> paramKeys) {
		this.paramKeys = paramKeys;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
