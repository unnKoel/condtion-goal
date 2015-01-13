package com.wind.goal.res;

import com.d1xn.ddal.client.base.Res;
import com.d1xn.ddal.client.base.ResId;
import com.wind.goal.vo.ParamterVO;

import java.sql.Timestamp;

/**
 * @entity PlatformParameterDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformParameterDefine {
	public static final String FIELD_CNAME = "cName";
	public static final String FIELD_SNAME = "sName";
	public static final String FIELD_CTYPE = "cType";
	public static final String FIELD_COPERATE = "cOperate";
	public static final String FIELD_CCHANGE = "cChange";
	public static final String FIELD_CRELATIVE = "cRelative";
	public static final String FIELD_DUPDATE = "dUpdate";

	private String CName; // 参数英文名
	private String SName; // 参数中文名
	private String CType; // 参数类型(1:int, 2:string,3:boolean 4:query)
	private String COperate; // 比较类型（>,=,<,>=,<=）
	private String CChange; // 变化类型（1:递增 2:替换）
	private String CRelative; // 关联参数
	private Timestamp DUpdate; // 更新时间

	public ParamterVO getParamterVO() {
		ParamterVO paramterVO = new ParamterVO();
		paramterVO.setCChange(this.getCChange());
		paramterVO.setCName(this.CName);
		paramterVO.setCOperate(this.COperate);
		paramterVO.setCType(this.CType);
		String[] relativeParams = null;
		if (CRelative != null && !CRelative.isEmpty() && CRelative.indexOf(",") != -1) {
			relativeParams = CRelative.split(",");
		}
		paramterVO.setRelativeParams(relativeParams);
		return paramterVO;
	}

	/** default constructor */
	public PlatformParameterDefine() {}

	/** must constructor */
	public PlatformParameterDefine(String CName, String COperate) {
		this.CName = CName;
		this.COperate = COperate;
	}

	/** full constructor */
	public PlatformParameterDefine(String CName, String SName, String CType, String COperate, String CChange, String CRelative,
			Timestamp DUpdate) {
		this.CName = CName;
		this.SName = SName;
		this.CType = CType;
		this.COperate = COperate;
		this.CChange = CChange;
		this.CRelative = CRelative;
		this.DUpdate = DUpdate;
	}

	@ResId
	public String getCName() {
		return CName;
	}

	public void setCName(String CName) {
		this.CName = CName;
	}

	public String getSName() {
		return SName;
	}

	public void setSName(String SName) {
		this.SName = SName;
	}

	public String getCType() {
		return CType;
	}

	public void setCType(String CType) {
		this.CType = CType;
	}

	public String getCOperate() {
		return COperate;
	}

	public void setCOperate(String COperate) {
		this.COperate = COperate;
	}

	public String getCChange() {
		return CChange;
	}

	public void setCChange(String CChange) {
		this.CChange = CChange;
	}

	public String getCRelative() {
		return CRelative;
	}

	public void setCRelative(String CRelative) {
		this.CRelative = CRelative;
	}

	public Timestamp getDUpdate() {
		return DUpdate;
	}

	public void setDUpdate(Timestamp DUpdate) {
		this.DUpdate = DUpdate;
	}

}
