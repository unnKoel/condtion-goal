package com.wind.goal.res;

/**
 * @entity UserConditionId
 */
public class UserConditionId
{
	public static final String FIELD_IUSERID = "iUserId";
	public static final String FIELD_ICATEGORYID = "iCategoryId";

	private Long IUserId; // 用户ID
	private Integer ICategoryId; // 条件类别ID

	/** default constructor */
	public UserConditionId() {}

	/** full constructor */
	public UserConditionId(Long IUserId, Integer ICategoryId) {
		this.IUserId = IUserId;
		this.ICategoryId = ICategoryId;
	}

	public Long getIUserId() {
		return IUserId;
	}

	public void setIUserId(Long IUserId) {
		this.IUserId = IUserId;
	}

	public Integer getICategoryId() {
		return ICategoryId;
	}

	public void setICategoryId(Integer ICategoryId) {
		this.ICategoryId = ICategoryId;
	}

}
