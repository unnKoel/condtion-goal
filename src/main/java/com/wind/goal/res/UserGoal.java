package com.wind.goal.res;

import java.sql.Timestamp;

/**
 * @entity UserGoal
 */
public class UserGoal {
	public static final String FIELD_CSTATUS = "cStatus";
	public static final String FIELD_DGETTIME = "dGetTime";
	public static final String FIELD_DTOUCHTIME = "dTouchTime";

	private UserGoalId id; // id
	private String CStatus; // 成就状态(0:条件不足，1：领取，2：已领取)
	private Timestamp DGetTime; // 目标奖励领取时间
	private Timestamp DTouchTime; // 目标完成时间

	/** default constructor */
	public UserGoal() {}

	/** must constructor */
	public UserGoal(UserGoalId id, String CStatus, Timestamp DGetTime, Timestamp DTouchTime) {
		this.id = id;
		this.CStatus = CStatus;
		this.DGetTime = DGetTime;
		this.DTouchTime = DTouchTime;
	}

	public UserGoalId getId() {
		return id;
	}

	public void setId(UserGoalId id) {
		this.id = id;
	}

	public String getCStatus() {
		return CStatus;
	}

	public void setCStatus(String CStatus) {
		this.CStatus = CStatus;
	}

	public Timestamp getDGetTime() {
		return DGetTime;
	}

	public void setDGetTime(Timestamp DGetTime) {
		this.DGetTime = DGetTime;
	}

	public Timestamp getDTouchTime() {
		return DTouchTime;
	}

	public void setDTouchTime(Timestamp DTouchTime) {
		this.DTouchTime = DTouchTime;
	}

}
