package com.wind.goal.res;

/**
 * @entity UserCondition
 */
public class UserCondition {
	public static final String FIELD_CSATISFYCLAIM = "cSatisfyClaim";

	private UserConditionId id; // id
	private String CSatisfyClaim; // 条件要求当前值

	/** default constructor */
	public UserCondition() {}

	/** must constructor */
	public UserCondition(UserConditionId id, String CSatisfyClaim) {
		this.id = id;
		this.CSatisfyClaim = CSatisfyClaim;
	}

	public UserConditionId getId() {
		return id;
	}

	public void setId(UserConditionId id) {
		this.id = id;
	}

	public String getCSatisfyClaim() {
		return CSatisfyClaim;
	}

	public void setCSatisfyClaim(String CSatisfyClaim) {
		this.CSatisfyClaim = CSatisfyClaim;
	}
}
