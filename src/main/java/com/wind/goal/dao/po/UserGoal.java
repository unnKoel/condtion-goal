package com.wind.goal.dao.po;

import java.io.Serializable;
import java.sql.Date;

/**
 * 用户目标
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-16
 */
public class UserGoal implements Serializable {

	private static final long serialVersionUID = 1517589925666887291L;

	private Integer userId;
	private String key;
	private String status;
	private Date touchTime;
	private Date getTime;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTouchTime() {
		return touchTime;
	}

	public void setTouchTime(Date touchTime) {
		this.touchTime = touchTime;
	}

	public Date getGetTime() {
		return getTime;
	}

	public void setGetTime(Date getTime) {
		this.getTime = getTime;
	}
}
