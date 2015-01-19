package com.wind.goal.dao.po;

public class RequestEvent {
	private String url; // 请求URL
	private String eventNo; // 触发的事件
	private String paramName; // 参数名称
	private String paramValue; // 参数值
	private String requestFlag; // 是否为请求参数

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRequestFlag() {
		return requestFlag;
	}

	public void setRequestFlag(String requestFlag) {
		this.requestFlag = requestFlag;
	}
}
