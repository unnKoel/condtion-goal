package com.wind.goal.res;

import com.d1xn.ddal.client.base.ResId;
import com.d1xn.common.base.IPojo;

/**
 * @entity PlatformRequireEventConfigId
 */
@ResId
public class PlatformRequireEventConfigId implements IPojo {
	public static final String FIELD_CURL = "cUrl";
	public static final String FIELD_CEVENTNO = "cEventNo";

	private String CUrl; // 请求URL
	private String CEventNo; // 触发的事件号

	/** default constructor */
	public PlatformRequireEventConfigId() {}

	/** full constructor */
	public PlatformRequireEventConfigId(String CUrl, String CEventNo) {
		this.CUrl = CUrl;
		this.CEventNo = CEventNo;
	}

	public String getCUrl() {
		return CUrl;
	}

	public void setCUrl(String CUrl) {
		this.CUrl = CUrl;
	}

	public String getCEventNo() {
		return CEventNo;
	}

	public void setCEventNo(String CEventNo) {
		this.CEventNo = CEventNo;
	}

}
