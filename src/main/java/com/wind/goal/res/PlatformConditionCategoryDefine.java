package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
import java.sql.Timestamp;

/**
 * @entity PlatformConditionCategoryDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformConditionCategoryDefine{
	public static final String FIELD_ICATEGORYID = "iCategoryId";
	public static final String FIELD_CEVNETNO = "cEvnetNo";
	public static final String FIELD_SCATEGORYNAME = "sCategoryName";
	public static final String FIELD_CIDENTIFYPARAMS = "cIdentifyParams";
	public static final String FIELD_DUPDATE = "dUpdate";

	private Integer ICategoryId; // 条件种类ID
	private String CEvnetNo; // 关联事件号
	private String SCategoryName; // 条件中文名
	private String CIdentifyParams; // 条件标识参数，通过参数标识出条件
	private Timestamp DUpdate; // 新更时间


	/** default constructor */
	public PlatformConditionCategoryDefine(){
	}

	/** must constructor */
	public PlatformConditionCategoryDefine(Integer ICategoryId,String CEvnetNo,String CIdentifyParams){
		this.ICategoryId = ICategoryId;
		this.CEvnetNo = CEvnetNo;
		this.CIdentifyParams = CIdentifyParams;
	}

	/** full constructor */
	public PlatformConditionCategoryDefine(Integer ICategoryId,String CEvnetNo,String SCategoryName,String CIdentifyParams,Timestamp DUpdate){
		this.ICategoryId = ICategoryId;
		this.CEvnetNo = CEvnetNo;
		this.SCategoryName = SCategoryName;
		this.CIdentifyParams = CIdentifyParams;
		this.DUpdate = DUpdate;
	}

	@ResId
	public Integer getICategoryId(){
		return ICategoryId;
	}

	public void setICategoryId(Integer ICategoryId){
		this.ICategoryId=ICategoryId;
	}

	public String getCEvnetNo(){
		return CEvnetNo;
	}

	public void setCEvnetNo(String CEvnetNo){
		this.CEvnetNo=CEvnetNo;
	}

	public String getSCategoryName(){
		return SCategoryName;
	}

	public void setSCategoryName(String SCategoryName){
		this.SCategoryName=SCategoryName;
	}

	public String getCIdentifyParams(){
		return CIdentifyParams;
	}

	public void setCIdentifyParams(String CIdentifyParams){
		this.CIdentifyParams=CIdentifyParams;
	}

	public Timestamp getDUpdate(){
		return DUpdate;
	}

	public void setDUpdate(Timestamp DUpdate){
		this.DUpdate=DUpdate;
	}

}

