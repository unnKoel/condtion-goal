package com.wind.goal.res;

import com.d1xn.ddal.client.base.Res;
import com.d1xn.ddal.client.base.ResId;

/**
 * @entity PlatformRequireEventConfig
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformRequireEventConfig {
	public static final String FIELD_CPARAMNAME = "cParamName";
	public static final String FIELD_CPARAMVALUE = "cParamValue";
	public static final String FIELD_CREQUIREFLAG = "cRequireFlag";

	private PlatformRequireEventConfigId id; // id
	private String CParamName; // 参数名称
	private String CParamValue; // 参数值
	private String CRequireFlag; // 是否为请求参数

	/** default constructor */
	public PlatformRequireEventConfig() {}

	/** must constructor */
	public PlatformRequireEventConfig(PlatformRequireEventConfigId id, String CParamName, String CRequireFlag) {
		this.id = id;
		this.CParamName = CParamName;
		this.CRequireFlag = CRequireFlag;
	}

	/** full constructor */
	public PlatformRequireEventConfig(PlatformRequireEventConfigId id, String CParamName, String CParamValue,
			String CRequireFlag) {
		this.id = id;
		this.CParamName = CParamName;
		this.CParamValue = CParamValue;
		this.CRequireFlag = CRequireFlag;
	}

	@ResId
	public PlatformRequireEventConfigId getId() {
		return id;
	}

	public void setId(PlatformRequireEventConfigId id) {
		this.id = id;
	}

	public String getCParamName() {
		return CParamName;
	}

	public void setCParamName(String CParamName) {
		this.CParamName = CParamName;
	}

	public String getCParamValue() {
		return CParamValue;
	}

	public void setCParamValue(String CParamValue) {
		this.CParamValue = CParamValue;
	}

	public String getCRequireFlag() {
		return CRequireFlag;
	}

	public void setCRequireFlag(String CRequireFlag) {
		this.CRequireFlag = CRequireFlag;
	}

}
