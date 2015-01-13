package com.wind.goal.res;

import com.d1xn.ddal.client.base.ResId;
import com.d1xn.common.base.IPojo;

/**
 * @entity ActivityCenterUserArticleId
 */
@ResId
public class ActivityCenterUserArticleId implements IPojo {
	public static final String FIELD_NUSERID = "nUserId";
	public static final String FIELD_ISQNO = "iSqNo";

	private Long NUserId; // 用户ID
	private Integer ISqNo; // 物品序号

	/** default constructor */
	public ActivityCenterUserArticleId() {}

	/** full constructor */
	public ActivityCenterUserArticleId(Long NUserId, Integer ISqNo) {
		this.NUserId = NUserId;
		this.ISqNo = ISqNo;
	}

	public Long getNUserId() {
		return NUserId;
	}

	public void setNUserId(Long NUserId) {
		this.NUserId = NUserId;
	}

	public Integer getISqNo() {
		return ISqNo;
	}

	public void setISqNo(Integer ISqNo) {
		this.ISqNo = ISqNo;
	}

}
